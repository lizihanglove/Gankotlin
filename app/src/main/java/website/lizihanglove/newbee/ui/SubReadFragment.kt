package website.lizihanglove.newbee.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.sub_read_fragment_layout.*
import org.jetbrains.anko.support.v4.toast
import site.lizihanglove.loading.Loading
import website.lizihanglove.newbee.R
import website.lizihanglove.newbee.adapter.ReadAdapter
import website.lizihanglove.newbee.model.ReadResponse
import website.lizihanglove.newbee.util.CATEGORY
import website.lizihanglove.newbee.util.NetManager
import website.lizihanglove.newbee.util.RxUtil

open class SubReadFragment : Fragment() {
    private var isFirst = true
    private val colors = intArrayOf(R.color.color_monday,
            R.color.color_tuesday,
            R.color.color_wednesday,
            R.color.color_thursday,
            R.color.color_friday,
            R.color.color_saturday,
            R.color.color_sunday)
    private var page = 1
    private val count = 20
    private lateinit var loading: Loading
    private lateinit var category: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.sub_read_fragment_layout, container, false)
    }

    override fun onResume() {
        super.onResume()
        loading = Loading.Builder(activity).setCancelable(true).build()
        category = arguments?.get(CATEGORY) as String
        if (category.isEmpty()) {
            toast("数据异常")
            return
        }
        Logger.i(category)

        readSummary.layoutManager = LinearLayoutManager(activity)
        getData(category)
        refresh.setColorSchemeColors(colors[0], colors[1], colors[2], colors[3], colors[4], colors[5], colors[6])
        refresh.setOnRefreshListener {
            getData(category)
        }
    }

    private fun getData(category: String) {
        if (isFirst) {
            loading.show()
        }

        NetManager.getServer()
                .readSummary(category, count, page)
                .compose(RxUtil.applyIoSchedulers())
                .subscribe(this::checkData) { error -> showError(error) }
    }

    private fun checkData(response: ReadResponse) {
        if (response.error) {
            showError(IllegalStateException("参数错误"))
        } else {
            if (isFirst) {
                loading.dismiss()
                isFirst = false
            }
            Logger.i(response.toString())
            readSummary.adapter = ReadAdapter(response.results, activity!!)

            if (refresh.isRefreshing) {
                refresh.isRefreshing = false
            }
        }
    }

    private fun showError(error: Throwable) {
        if (isFirst) {
            loading.dismiss()
            isFirst = false
        }
        Logger.e(error.localizedMessage)
        if (refresh.isRefreshing) {
            refresh.isRefreshing = false
        }
    }
}
