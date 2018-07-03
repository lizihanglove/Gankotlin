package <default> (D:\Learning\NewBee\app\src\test\java)


import com.google.gson.annotations.SerializedName

data class LatestRsponse(@SerializedName("error")
                         val error: Boolean = false,
                         @SerializedName("results")
                         val results: List<String>?)


