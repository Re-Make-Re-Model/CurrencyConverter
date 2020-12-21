package zekai.myapplication.paypaycurrency.data.api.gsonModel

import com.google.gson.annotations.SerializedName

data class LiveResponse (

    @SerializedName("success") val success : Boolean,
    @SerializedName("terms") val terms : String,
    @SerializedName("privacy") val privacy : String,
    @SerializedName("timestamp") val timestamp : Long,
    @SerializedName("source") val source : String,
    @SerializedName("quotes") val quotes : Map<String, Double>,
    @SerializedName("error") var errorResponse: ErrorResponse
)