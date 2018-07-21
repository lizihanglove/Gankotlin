package website.lizihanglove.newbee.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_spare_read.*
import site.lizihanglove.loading.Loading
import website.lizihanglove.newbee.R
import website.lizihanglove.newbee.adapter.ReadFragmentAdapter
import website.lizihanglove.newbee.model.SubCategoryResponse
import website.lizihanglove.newbee.util.*


open class SpareReadActivity : AppCompatActivity() {
    private lateinit var loading: Loading
    private  var fragments: ArrayList<SubReadFragment> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spare_read)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        val categoryName = intent.getStringExtra(CATEGORY_NAME)
        val categoryEnglishName = intent.getStringExtra(CATEGORY_ENGLISH_NAME)
        when (categoryEnglishName.isNotEmpty() && categoryName.isNotEmpty()) {
            true -> getData(categoryEnglishName, categoryName)
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
    private fun getData(categoryEnglishName: String, categoryName: String) {
        supportActionBar?.title = categoryName
        loading = Loading.Builder(this).setCancelable(true).build()
        loading.show()
        NetManager.getServer()
                .subCategories(categoryEnglishName)
                .compose(RxUtil.applyIoSchedulers())
                .subscribe(
                        this::checkData
                ) { throwable -> showError(throwable) }
    }

    /**
     * 检查最新数据
     */
    private fun checkData(response: SubCategoryResponse) {
        Logger.i(response.toString())
        if (response.error) {
            showError()
        } else {
            showData(response)
        }
    }

    /**
     * 展示最新数据
     */
    private fun showData(response: SubCategoryResponse) {
        loading.dismiss()
        response.results.forEach { subCategory ->
            val newTab = tab.newTab()
            newTab.text = subCategory.title
            tab.addTab(newTab)
            val subReadFragment = SubReadFragment()
            val bundle = Bundle()
            bundle.putString(CATEGORY,subCategory.id)
            subReadFragment.arguments = bundle
            fragments.add(subReadFragment)
        }
        tab.setupWithViewPager(pager)
        val titles = response.results.map { subCategory -> subCategory.title }
        val fm = supportFragmentManager
        Logger.i(titles.toString())
        pager.adapter = ReadFragmentAdapter(titles, fragments, fm)
        pager.currentItem = 0
    }

    /**
     * 展示错误信息
     */
    private fun showError(throwable: Throwable) {
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
