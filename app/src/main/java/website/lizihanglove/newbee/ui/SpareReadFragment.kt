package website.lizihanglove.newbee.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.spare_read_fragment_layout.*
import org.jetbrains.anko.support.v4.toast
import site.lizihanglove.loading.Loading
import website.lizihanglove.newbee.R
import website.lizihanglove.newbee.adapter.CategoryAdapter
import website.lizihanglove.newbee.model.CategoryResponse
import website.lizihanglove.newbee.util.NetManager
import website.lizihanglove.newbee.util.RxUtil

open class SpareReadFragment : Fragment() {
    private lateinit var loading: Loading

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.spare_read_fragment_layout, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loading = Loading.Builder(activity).setCancelable(true).build()
        loading.show()
        mainCategories.layoutManager = LinearLayoutManager(activity)
        NetManager.getServer()
                .categories()
                .compose(RxUtil.applyIoSchedulers())
                .subscribe(this::checkData) { error -> showError(error) }
    }

    private fun checkData(response: CategoryResponse) {
        if (response.error) {
            showError(IllegalStateException("参数错误"))
        } else {
            loading.dismiss()
            mainCategories.adapter = CategoryAdapter(response.results, activity!!)
        }
    }

    private fun showError(error: Throwable) {
        loading.dismiss()
        toast("请求异常")
        Logger.e(error.localizedMessage)
    }
}
