package zekai.myapplication.paypaycurrency.utils

import java.text.SimpleDateFormat
import java.util.*

object DateFormatUtils{

    fun getDateTime(long: Long): String? {
        try {
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val netDate = Date(long * 1000)
            return sdf.format(netDate)
        } catch (e: Exception) {
            return e.toString()
        }
    }
}