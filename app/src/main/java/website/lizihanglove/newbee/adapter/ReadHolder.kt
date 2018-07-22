package website.lizihanglove.newbee.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import org.jetbrains.anko.find
import website.lizihanglove.newbee.R

/**
 * @author lizihanglove
 * @date 2018/7/21
 * @email one_mighty@163.com
 * @desc
 */
class ReadHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val root = itemView.find<LinearLayout>(R.id.root)
    val logo = itemView.find<ImageView>(R.id.readLogo)
    val title = itemView.find<TextView>(R.id.readTitle)
    val time = itemView.find<TextView>(R.id.readTime)
}