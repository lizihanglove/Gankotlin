package website.lizihanglove.newbee.model

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.google.gson.JsonObject


/**
 * @author lizihanglove
 * @email one_mighty@163.com
 * @date  2018-06-23
 * @time 18:14
 * @desc
 */
class MultipleItem : MultiItemEntity {
    private var itemType: Int = 0
    var objectContent: JsonObject?


    constructor(itemType: Int, content: JsonObject) {
        this.itemType = itemType
        this.objectContent = content
    }

    override fun getItemType(): Int {
        return itemType
    }

    companion object {
        //文本
        const val TEXT = 1
        //图片
        const val IMAGE = 2
        //文本加图片
        const val IMAGE_TEXT = 3
        //视频
        const val VIDEO = 4
        //空数据
        const val EMPTY = 5
    }
}