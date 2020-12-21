package zekai.myapplication.paypaycurrency.data.api

sealed class BaseResult<out T : Any> {

    data class Success<out T : Any>(val data: T): BaseResult<T>()


    data class Fail(val message: String) :BaseResult<Nothing>()

    override fun toString(): String {
        return when(this){
            is Success<*> -> "Success[data=$data]"
            is Fail -> "Fail[message=$message]"
        }
    }
}