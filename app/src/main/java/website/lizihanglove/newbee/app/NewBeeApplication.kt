package website.lizihanglove.newbee.app

import android.app.Application
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger


/**
 * @author lizihanglove
 * @email one_mighty@163.com
 * @date  2018-06-15
 * @time 23:59
 * @desc
 */
open class NewBeeApplication:Application(){

    override fun onCreate() {
        super.onCreate()
        Logger.addLogAdapter(AndroidLogAdapter())
    }
}