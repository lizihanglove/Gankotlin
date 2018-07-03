package website.lizihanglove.newbee.ui

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.webkit.*
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_web_view.*
import org.jetbrains.anko.toast
import site.lizihanglove.loading.Loading
import website.lizihanglove.newbee.R
import android.content.Intent
import android.net.Uri
import android.widget.Toast


class WebViewActivity : AppCompatActivity() {

    val complete: Int = 100
    lateinit var url: String
    lateinit var loading: Loading
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        loading = Loading.Builder(this).setCancelable(true).build()
        val extras: Bundle = intent.getBundleExtra("bundle")
        if (extras.isEmpty) {
            toast("没有加载的数据")
            return
        }
        supportActionBar?.title = "第三方网页"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        url = extras.getString("url")
        webView.loadUrl(url)
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.useWideViewPort = true
        webSettings.allowFileAccess = true
        webSettings.setSupportZoom(true)
        webSettings.loadWithOverviewMode = true
        webSettings.cacheMode = WebSettings.LOAD_NO_CACHE

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
        webView.settings.useWideViewPort = true
        webView.webViewClient = object : WebViewClient() {

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                toast("加载失败")
                super.onReceivedError(view, request, error)
            }


        }
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                if (complete == newProgress) {
                    loading.dismiss()
                } else {
                    loading.show()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        webView.destroy()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.copy_url -> {
                val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clipData = ClipData.newPlainText(null, url)
                clipboard.primaryClip = clipData
                toast("复制成功！")
                true
            }
            R.id.open_url -> {
                val uri = Uri.parse(url)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
                false
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
