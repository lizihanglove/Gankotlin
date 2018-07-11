package website.lizihanglove.newbee.adapter

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import org.jetbrains.anko.find
import org.jetbrains.anko.layoutInflater
import org.jetbrains.anko.toast
import site.lizihanglove.preview.Preview
import website.lizihanglove.newbee.R
import website.lizihanglove.newbee.java.GlideApp
import website.lizihanglove.newbee.model.MultipleItem
import website.lizihanglove.newbee.ui.WebViewActivity


/**
 * @author lizihanglove
 * @email one_mighty@163.com
 * @date  2018-06-23
 * @time 18:09
 * @desc 数据适配
 */
class DayDataAdapter(data: List<MultipleItem>) : BaseMultiItemQuickAdapter<MultipleItem, BaseViewHolder>(data) {

    init {
        addItemType(MultipleItem.TEXT, R.layout.item_text_view)
        addItemType(MultipleItem.IMAGE, R.layout.item_image_view)
        addItemType(MultipleItem.IMAGE_TEXT, R.layout.item_img_text_view)
        addItemType(MultipleItem.VIDEO, R.layout.item_video_view)
        addItemType(MultipleItem.EMPTY, R.layout.item_empty_view)
    }

    override fun convert(helper: BaseViewHolder, item: MultipleItem) {
        when (item.itemType) {
            MultipleItem.TEXT -> {
                val name = item.objectContent?.get("name")?.asString
                helper.setText(R.id.dayDataSubtitle, name)
            }
            MultipleItem.IMAGE -> {
                val image = helper.getView<ImageView>(R.id.dayDataImage)
                val bonusUrl = item.objectContent?.get("url")?.asString
                GlideApp.with(mContext)
                        .load(bonusUrl)
                        .thumbnail(0.5f)
                        .placeholder(R.drawable.empty)
                        .into(image)
                image.setOnClickListener {
                    run {
                        val color = mContext.resources.getColor(R.color.color_light_gray)
                        val view = mContext.layoutInflater.inflate(R.layout.preview_layout, null)
                        val imagePreview = view.find<ImageView>(R.id.image_preview)
                        GlideApp.with(mContext)
                                .load(bonusUrl)
                                .placeholder(R.drawable.empty)
                                .into(imagePreview)
                        Preview.Builder(mContext)
                                .setBackground(color)
                                .setContentView(view)
                                .build()
                                .show()
                    }
                }

            }
            MultipleItem.IMAGE_TEXT -> {
                val desc = item.objectContent?.get("desc")
                helper.setText(R.id.dayDataName, desc?.asString)
                val thumbnail = helper.getView<ImageView>(R.id.dayDataThumbnail)
                val images = item.objectContent?.getAsJsonArray("images")
                val size = images?.size()
                if (size != null && size > 0) {
                    val imagesUrl = images.get(0)?.asString
                    GlideApp.with(mContext)
                            .load(imagesUrl)
                            .placeholder(R.drawable.empty)
                            .into(thumbnail)
                    thumbnail.setOnClickListener {
                        run {
                            val color = mContext.resources.getColor(R.color.color_light_gray)
                            val view = mContext.layoutInflater.inflate(R.layout.preview_layout, null)
                            val imagePreview = view.find<ImageView>(R.id.image_preview)
                            GlideApp.with(mContext)
                                    .load(imagesUrl)
                                    .placeholder(R.drawable.empty)
                                    .into(imagePreview)
                            Preview.Builder(mContext)
                                    .setBackground(color)
                                    .setContentView(view)
                                    .build()
                                    .show()
                        }
                    }
                } else {
                    val localUrl = mContext.getDrawable(R.drawable.empty)
                    GlideApp.with(mContext)
                            .load(localUrl)
                            .placeholder(R.drawable.empty)
                            .into(thumbnail)
                    thumbnail.setOnClickListener {
                        run {
                            mContext.toast("无图片预览")
                        }
                    }
                }
                val container = helper.getView<TextView>(R.id.dayDataName)
                val projectUrl = item.objectContent?.get("url")
                container.setOnClickListener {
                    run {
                        val intent = Intent(mContext, WebViewActivity::class.java)
                        val extra = Bundle()
                        extra.putString("url", projectUrl?.asString)
                        intent.putExtra("bundle", extra)
                        mContext.startActivity(intent)
                    }
                }
            }
            MultipleItem.VIDEO -> {
                val desc = item.objectContent?.get("desc")
                val videoUrl = item.objectContent?.get("url")
                helper.setText(R.id.dayDataTitle, desc?.asString)
                val view = helper.getView<TextView>(R.id.dayDataTitle)
                view.setOnClickListener {
                    run {
                        val intent = Intent(mContext, WebViewActivity::class.java)
                        val extra = Bundle()
                        extra.putString("url", videoUrl?.asString)
                        intent.putExtra("bundle", extra)
                        mContext.startActivity(intent)
                    }
                }
            }
            else -> {
                helper.setText(R.id.dayDataEmpty, "没有内容")

            }
        }
    }
}