package zekai.myapplication.paypaycurrency.data.api.gsonModel

import com.google.gson.annotations.SerializedName

data class ListResponse (

    @SerializedName("success") val success : Boolean,
    @SerializedName("terms") val terms : String,
    @SerializedName("privacy") val privacy : String,
    @SerializedName("currencies") val currencies : Map<String, String>,
    @SerializedName("error") var errorResponse: ErrorResponse
)