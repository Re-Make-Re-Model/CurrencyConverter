package zekai.myapplication.paypaycurrency.data.api

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope

open class SafeCall {

    suspend fun <T : Any> safeApiCall(call: suspend () -> BaseResult<T>): BaseResult<T> {
        return try {
            call()
        }catch (e: Exception) {
            // An exception was thrown when calling the API so we're converting this to an IOException
            BaseResult.Fail(e.message?:"")
        }
    }

    suspend fun <T : Any> executeResponse(response: T, successBlock: (suspend CoroutineScope.() -> Unit)? = null): BaseResult<T> {
        return coroutineScope {
            successBlock?.let { it() }
            BaseResult.Success(response)
        }
    }
}