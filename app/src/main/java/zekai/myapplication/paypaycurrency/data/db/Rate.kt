package zekai.myapplication.paypaycurrency.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["quote"])
data class Rate(
        @ColumnInfo(name = "quote")
        val quote: String,
        @ColumnInfo(name = "currency_from") val currencyFrom: String?,
        @ColumnInfo(name = "currency_to") val currencyTo: String?,
        @ColumnInfo(name = "timestamp") val timestamp: Long,
        @ColumnInfo(name = "rate") val rate: Double,
)