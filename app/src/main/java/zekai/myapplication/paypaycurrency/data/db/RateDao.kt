package zekai.myapplication.paypaycurrency.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RateDao {
    @Query("SELECT * FROM rate")
    fun getAllRates(): List<Rate>

    @Query("SELECT * FROM rate WHERE currency_from = :currencyFrom AND currency_to = :currencyTo")
    fun loadNameByKey(currencyFrom: String, currencyTo: String): Rate

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun insertAllRates(rates: List<Rate>)

}