package website.lizihanglove.newbee.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import org.jetbrains.anko.toast
import website.lizihanglove.newbee.R
import website.lizihanglove.newbee.util.DATE


open class HistoryActivity : AppCompatActivity() {
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
        supportActionBar?.title = date
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
