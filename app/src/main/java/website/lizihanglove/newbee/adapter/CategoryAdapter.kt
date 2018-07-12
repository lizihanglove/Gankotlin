package website.lizihanglove.newbee.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import website.lizihanglove.newbee.R
import website.lizihanglove.newbee.model.Category
import website.lizihanglove.newbee.ui.SpareReadActivity
import website.lizihanglove.newbee.util.CATEGORY_ENGLISH_NAME
import website.lizihanglove.newbee.util.CATEGORY_NAME

open class CategoryAdapter(var results: List<Category>, var context: Context) : RecyclerView.Adapter<CategoryHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_category_view, parent, false)
        return CategoryHolder(view)
    }

    override fun getItemCount(): Int {
        return results.size
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        holder.categoryName.text = results[position].name
        holder.categoryName.setOnClickListener {
            val intent = Intent(context, SpareReadActivity::class.java)
            intent.putExtra(CATEGORY_ENGLISH_NAME,results[position].en_name)
            intent.putExtra(CATEGORY_NAME,results[position].name)
            context.startActivity(intent)
        }
    }

}
