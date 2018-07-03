package website.lizihanglove.newbee.util

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * @ author lizihanglove
 * 邮箱：one_mighty@163.com
 * 时间：on 2017/11/3 16:37
 */
class NetManager {
    companion object {
        private val builder: OkHttpClient.Builder = OkHttpClient.Builder()
        private val client = builder.connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build()
        private val gsonConverterFactory = GsonConverterFactory.create()
        private val rxJavaCallAdapterFactory = RxJava2CallAdapterFactory.create()
        private val mRetrofit = Retrofit.Builder()
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .baseUrl(baseURL)
                .client(client)
                .build()
        private val api = mRetrofit.create(Api::class.java)

        fun getServer(): Api {
            return api
        }
    }
}


