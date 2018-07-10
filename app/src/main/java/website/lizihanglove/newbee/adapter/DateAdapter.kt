package website.lizihanglove.newbee.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import org.jetbrains.anko.layoutInflater
import org.jetbrains.anko.textColor
import website.lizihanglove.newbee.R
import website.lizihanglove.newbee.ui.HistoryActivity
import website.lizihanglove.newbee.util.DATE

/**
 * @author lizihanglove
 * @date 2018/7/10
 * @email one_mighty@163.com
 * @desc
 */
open class DateAdapter(var dates: List<String>, var context: Context) : RecyclerView.Adapter<DateHolder>() {
    private val even = context.resources.getDrawable(R.drawable.even_background)
    private val odd = context.resources.getDrawable(R.drawable.odd_background)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateHolder {
        val historyView = R.layout.item_history_view
        val view = LayoutInflater.from(parent.context).inflate(historyView, parent, false)
        return DateHolder(view)
    }

    override fun getItemCount(): Int {
        return dates.size
    }

    override fun onBindViewHolder(holder: DateHolder, position: Int) {
        val date = dates[position]
        var day = date.split("-")[2]
        if (day.startsWith("0")) {
            day = day[1].toString()
        }
        val toInt = day.toInt()
        Log.i("toInt", toInt.toString())

        when(toInt%2){
            0->{ holder.logo.background = even }
            1->{ holder.logo.background = odd }
        }
        holder.logo.text = day
        holder.detail.text = date
        val intent = Intent(context, HistoryActivity::class.java)
        intent.putExtra(DATE,date)
        holder.item.setOnClickListener {
            run {
                context.startActivity(intent)
            }
        }
    }
}
