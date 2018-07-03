package website.lizihanglove.newbee.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import website.lizihanglove.newbee.R

/**
 * @purpose 主页面，做内容切换
 * @author  one_mighty
 * @date 2017/10/18 16:30
 * @other none
 */
class MainActivity : AppCompatActivity() {

    private val mainFragment: Fragment = MainFragment()
    private val spareReadFragment: Fragment = SpareReadFragment()
    private val toolbar: Toolbar
        get() {
            val toolBar: Toolbar = findViewById(R.id.tool_bar)
            return toolBar
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val latest: LinearLayout = findViewById(R.id.ll_latest)
        val spareRead: LinearLayout = findViewById(R.id.ll_spare_read)
        val subscribe: LinearLayout = findViewById(R.id.ll_subscribe)
        val outSource: LinearLayout = findViewById(R.id.ll_out_source)
        val submitMaterial: LinearLayout = findViewById(R.id.ll_submit_material)
        val rssSubscribe: LinearLayout = findViewById(R.id.ll_rss_subscribe)
        val toolBar: Toolbar = findViewById(R.id.tool_bar)
        val search: LinearLayout = findViewById(R.id.ll_search)

        search.setOnClickListener {
            run {
                toast("搜索并没有好")
            }
        }
        toolBar.setTitleTextColor(Color.WHITE)
        switchUI(0, toolbar)
        setSupportActionBar(toolBar)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val mDrawerToggle: ActionBarDrawerToggle
        mDrawerToggle = object : ActionBarDrawerToggle(this, drawer, toolBar, R.string.open, R.string.close) {}
        mDrawerToggle.syncState()
        drawer!!.addDrawerListener(mDrawerToggle)
        latest.setOnClickListener { switchUI(0, toolbar) }
        spareRead.setOnClickListener { switchUI(1, toolbar) }
        subscribe.setOnClickListener { toast("做不了，订阅没有接口") }
        outSource.setOnClickListener { toast("只是为了好看而已，外包没有做") }
        submitMaterial.setOnClickListener { toast("别多想，提交并没有做") }
        rssSubscribe.setOnClickListener { toast("提交干活都没有做，订阅就更没有做了") }

    }

    private fun switchUI(int: Int, toolbar: Toolbar) {
        drawer!!.closeDrawer(GravityCompat.START)
        val fm: FragmentManager = supportFragmentManager
        val transaction: FragmentTransaction = fm.beginTransaction()
        hideUI(fm)
        when (int) {
            0 -> {
                transaction.replace(R.id.ll_content, mainFragment)
                transaction.show(mainFragment)
                toolbar.title = "最新内容"
            }
            1 -> {
                transaction.replace(R.id.ll_content, spareReadFragment)
                transaction.show(spareReadFragment)
                toolbar.title = "闲读"
            }
        }
        transaction.commit()
    }

    private fun hideUI(fragmentManager: FragmentManager) {
        fragmentManager.beginTransaction()
                .hide(spareReadFragment)
                .commit()
    }
}
