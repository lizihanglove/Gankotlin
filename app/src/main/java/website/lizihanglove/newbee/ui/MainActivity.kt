package website.lizihanglove.newbee.ui

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
import kotlinx.android.synthetic.main.main_menu_layout.*
import kotlinx.android.synthetic.main.toolbar_layout.*
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
    private val historyFragment: Fragment = HistoryFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        search.setOnClickListener {
            run {
                toast("搜索并没有好")
            }
        }
        toolBar.setTitleTextColor(Color.WHITE)
        switchUI(0, toolBar)
        setSupportActionBar(toolBar)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val mDrawerToggle: ActionBarDrawerToggle
        mDrawerToggle = object : ActionBarDrawerToggle(this, drawer, toolBar, R.string.open, R.string.close) {}
        mDrawerToggle.syncState()
        drawer!!.addDrawerListener(mDrawerToggle)
        latest.setOnClickListener { switchUI(0, toolBar) }
        history.setOnClickListener { switchUI(1, toolBar) }
        spareRead.setOnClickListener { switchUI(2, toolBar) }
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
                transaction.replace(R.id.content, mainFragment)
                transaction.show(mainFragment)
                toolbar.title = "最新内容"
            }
            1 -> {
                transaction.replace(R.id.content, historyFragment)
                transaction.show(historyFragment)
                toolbar.title = "历史内容"
            }
            2 -> {
                transaction.replace(R.id.content, spareReadFragment)
                transaction.show(spareReadFragment)
                toolbar.title = "闲读"
            }
        }
        transaction.commit()
    }

    private fun hideUI(fragmentManager: FragmentManager) {
        fragmentManager.beginTransaction()
                .hide(mainFragment)
                .hide(spareReadFragment)
                .hide(historyFragment)
                .commit()
    }
}
