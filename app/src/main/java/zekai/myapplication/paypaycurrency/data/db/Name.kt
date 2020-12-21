package zekai.myapplication.paypaycurrency.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["currency_key"])
data class Name(
        @ColumnInfo(name = "currency_key")
        val currencyKey: String,
        @ColumnInfo(name = "currency_name")
        val currencyName: String?
)