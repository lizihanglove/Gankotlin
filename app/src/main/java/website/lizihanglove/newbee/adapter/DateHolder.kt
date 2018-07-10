package website.lizihanglove.newbee.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import org.jetbrains.anko.find
import website.lizihanglove.newbee.R

/**
 * @author lizihanglove
 * @date 2018/7/10
 * @email one_mighty@163.com
 * @desc
 */
open class DateHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val item = itemView.find<LinearLayout>(R.id.historyItem)
    val logo = itemView.find<TextView>(R.id.dateLogo)
    val detail = itemView.find<TextView>(R.id.dateDetail)
}
