package website.lizihanglove.newbee.model

data class LatestResponse(val error: Boolean = false,
                          val results: List<String>)