package website.lizihanglove.newbee.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_splash.*
import website.lizihanglove.newbee.R
import java.util.*


open class SplashActivity : AppCompatActivity() {
    private val colors = intArrayOf(R.color.color_monday,
            R.color.color_tuesday,
            R.color.color_wednesday,
            R.color.color_thursday,
            R.color.color_friday,
            R.color.color_saturday,
            R.color.color_sunday)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen()
        setContentView(R.layout.activity_splash)
        val day =Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        val color = resources.getColor(colors[day-1])
        container.setBackgroundColor(color)
        textView.text = getString(R.string.nickname)
        val timer: CountDownTimer = SplashTimer(this, 3000, 1)
        timer.start()
    }

    private fun setFullScreen() {
        val decorView = window.decorView
        val option = View.SYSTEM_UI_FLAG_FULLSCREEN
        decorView.systemUiVisibility = option
    }

    class SplashTimer(activity: Activity, millisInFuture: Long, interval: Long) : CountDownTimer(millisInFuture, interval) {
        private var mActivity: Activity = activity
        override fun onFinish() {
            val mainIntent = Intent(mActivity, MainActivity::class.java)
            mActivity.startActivity(mainIntent)
            mActivity.finish()
        }

        override fun onTick(progress: Long) {
        }
    }
}
