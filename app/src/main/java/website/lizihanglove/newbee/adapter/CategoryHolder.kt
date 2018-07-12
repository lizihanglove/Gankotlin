package website.lizihanglove.newbee.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import org.jetbrains.anko.find
import website.lizihanglove.newbee.R

class CategoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val categoryName = itemView.find<TextView>(R.id.categoryName)
}
