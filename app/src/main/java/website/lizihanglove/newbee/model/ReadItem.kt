package website.lizihanglove.newbee.model

/**
 * @author lizihanglove
 * @date 2018/7/21
 * @email one_mighty@163.com
 * @desc
 */
data class ReadItem(val _id: String,
                    val content: String,
                    val cover: String,
                    val crawled: String,
                    val created_at: String,
                    val deleted: String,
                    val published_at: String,
                    val raw: String,
                    val site: SiteItem,
                    val title: String,
                    val uid: String,
                    val url: String
)