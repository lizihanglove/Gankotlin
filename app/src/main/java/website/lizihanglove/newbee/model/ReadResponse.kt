package website.lizihanglove.newbee.model

/**
 * @author lizihanglove
 * @date 2018/7/21
 * @email one_mighty@163.com
 * @desc
 */

data class ReadResponse(val error: Boolean = false,
                            val results: ArrayList<ReadItem>)