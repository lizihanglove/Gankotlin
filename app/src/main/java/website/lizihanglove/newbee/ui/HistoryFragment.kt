package website.lizihanglove.newbee.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.history_fragment_layout.*
import org.jetbrains.anko.support.v4.toast
import site.lizihanglove.loading.Loading
import website.lizihanglove.newbee.R
import website.lizihanglove.newbee.adapter.DateAdapter
import website.lizihanglove.newbee.model.LatestResponse
import website.lizihanglove.newbee.util.NetManager
import website.lizihanglove.newbee.util.RxUtil


open class HistoryFragment : Fragment() {
    private lateinit var loading:Loading
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.history_fragment_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loading = Loading.Builder(activity).setCancelable(true).build()
        loading.show()
        //请求更新过数据的日期
        NetManager.getServer()
                .latest()
                .compose(RxUtil.applyIoSchedulers())
                .subscribe(
                        { response -> processLatest(response) },
                        { throwable -> showError(throwable) })
    }

    /**
     * 处理日期
     */
    private fun processLatest(response: LatestResponse) {
        if (response.error) {
            showError()
        } else {
            if (response.results.isNotEmpty()) {
                //展示所有日期
                showDates(response.results)
            } else {
                showError()
            }
        }
    }

    /**
     * 展示日期数据
     */
    private fun showDates(results: List<String>) {
        loading.dismiss()
        dateContainer.layoutManager = LinearLayoutManager(activity)
        dateContainer.adapter = DateAdapter(results,activity!!)
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

