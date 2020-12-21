package zekai.myapplication.paypaycurrency.data.api.gsonModel

import com.google.gson.annotations.SerializedName

data class ErrorResponse (

    @SerializedName("code") val code : String,
    @SerializedName("info") var message : String

)