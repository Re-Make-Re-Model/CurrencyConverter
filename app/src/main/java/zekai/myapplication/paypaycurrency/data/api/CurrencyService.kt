package zekai.myapplication.paypaycurrency.data.api

import retrofit2.http.GET
import zekai.myapplication.paypaycurrency.data.api.gsonModel.ListResponse
import zekai.myapplication.paypaycurrency.data.api.gsonModel.LiveResponse

interface CurrencyService {

    @GET("/list?access_key=d8440f76624b981d74447c29763e6d25&format=1")
    suspend fun getList(): ListResponse

    @GET("/live?access_key=d8440f76624b981d74447c29763e6d25&format=1")
    suspend fun getLive(): LiveResponse
}