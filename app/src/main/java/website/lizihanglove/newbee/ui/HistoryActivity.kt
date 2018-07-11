package website.lizihanglove.newbee.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import com.google.gson.JsonObject
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.main_fragment_layout.*
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast
import site.lizihanglove.loading.Loading
import website.lizihanglove.newbee.R
import website.lizihanglove.newbee.adapter.DayDataAdapter
import website.lizihanglove.newbee.model.MultipleItem
import website.lizihanglove.newbee.util.DATE
import website.lizihanglove.newbee.util.NetManager
import website.lizihanglove.newbee.util.RxUtil


open class HistoryActivity : AppCompatActivity() {
    private lateinit var loading:Loading
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        val date = intent.getStringExtra(DATE)
        when (date.isNotEmpty()) {
            true -> getData(date)
            false -> showError()
        }
    }

    /**
     * 提示异常
     */
    private fun showError() {
        supportActionBar?.title = "信息异常"
    }

    /**
     * 请求数据
     */
    private fun getData(date: String) {
        val dates = date.split("-")
        supportActionBar?.title = date
        loading = Loading.Builder(this).setCancelable(true).build()
        loading.show()
        NetManager.getServer()
                .history(dates[0], dates[1], dates[2])
                .compose(RxUtil.applyIoSchedulers())
                .subscribe(
                        this::checkData
                ) { throwable -> showError(throwable) }

    }

    /**
     * 检查最新数据
     */
    private fun checkData(response: JsonObject) {
        Logger.i(response.toString())
        val isError = response.get("error").asBoolean
        if (isError) {
            showError()
        } else {
            showData(response)
        }
    }

    /**
     * 展示最新数据
     */
    private fun showData(response: JsonObject) {
        loading.dismiss()
        val data = ArrayList<MultipleItem>()
        val categories = response.getAsJsonArray("category")
        val results = response.getAsJsonObject("results")
        categories.forEach { category ->
            val name = category.asString
            val obj = JsonObject()
            obj.addProperty("name", name)
            data.add(MultipleItem(MultipleItem.TEXT, obj))
            val items = results.getAsJsonArray(name)
            items.forEach { item ->
                val itemObject = item.asJsonObject
                when (itemObject.get("type").asString) {
                    "福利"->{
                        data.add(MultipleItem(MultipleItem.IMAGE,itemObject))
                    }
                    "休息视频"->{
                        data.add(MultipleItem(MultipleItem.VIDEO,itemObject))
                    }
                    else -> {
                        data.add(MultipleItem(MultipleItem.IMAGE_TEXT,itemObject))
                    }
                }
            }
        }
        val adapter = DayDataAdapter(data)
        history.layoutManager = LinearLayoutManager(this)
        history.adapter = adapter
    }

    /**
     * 展示错误信息
     */
    private fun showError(throwable: Throwable) {
        loading.dismiss()
        toast("出错啦，T_T")
        Logger.e(throwable, "Error")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> {
                false
            }
        }
    }

}
