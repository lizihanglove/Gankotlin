package website.lizihanglove.newbee.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.JsonObject
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.main_fragment_layout.*
import org.jetbrains.anko.support.v4.toast
import site.lizihanglove.loading.Loading
import website.lizihanglove.newbee.R
import website.lizihanglove.newbee.adapter.DayDataAdapter
import website.lizihanglove.newbee.model.MultipleItem
import website.lizihanglove.newbee.util.NetManager
import website.lizihanglove.newbee.util.RxUtil


open class MainFragment : Fragment() {
    private lateinit var loading:Loading
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.main_fragment_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loading = Loading.Builder(activity).setCancelable(true).build()
        loading.show()
        //获取最新数据
        NetManager.getServer()
                .today()
                .compose(RxUtil.applyIoSchedulers())
                .subscribe( this::checkData
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
        dataContainer.layoutManager = LinearLayoutManager(activity)
        dataContainer.adapter = adapter
    }

    /**
     * 展示错误信息
     */
    private fun showError(throwable: Throwable) {
        loading.dismiss()
        toast("出错啦，T_T")
        Logger.e(throwable, "Error")
    }

    /**
     * 展示错误信息
     */
    private fun showError() {
        loading.dismiss()
        toast("出错啦，T_T")
        Logger.e("Error")
    }
}

