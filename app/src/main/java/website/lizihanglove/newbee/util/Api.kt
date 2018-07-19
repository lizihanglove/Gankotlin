package website.lizihanglove.newbee.util

import com.google.gson.JsonObject
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import website.lizihanglove.newbee.model.CategoryResponse
import website.lizihanglove.newbee.model.LatestResponse
import website.lizihanglove.newbee.model.SubCategoryResponse


/**
 * @ author lizihanglove
 * 邮箱：one_mighty@163.com
 * 时间：on 2017/11/3 17:15
 */
interface Api {

    /**
     * 最新内容
     */
    @GET("today")
    fun today(): Observable<JsonObject>

    /**
     * 历史数据
     */
    @GET("day/history")
    fun latest(): Observable<LatestResponse>

    /**
     * 某日内容
     */
    @GET("day/{year}/{month}/{day}")
    fun history(
            @Path("year") year: String,
            @Path("month") month: String,
            @Path("day") day: String
    ): Observable<JsonObject>

    /**
     * 主分类
     */
    @GET("xiandu/categories")
    fun categories(): Observable<CategoryResponse>

    /**
     * 子分类
     */
    @GET("xiandu/category/{englishName}")
    fun subCategories(@Path("englishName") englishName: String): Observable<SubCategoryResponse>
}