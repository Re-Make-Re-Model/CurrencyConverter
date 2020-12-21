package zekai.myapplication.paypaycurrency.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NameDao {
    @Query("SELECT * FROM name")
    fun getAllNames(): List<Name>

    @Query("SELECT * FROM name WHERE currency_key = :currencyKey")
    fun loadNameByKey(currencyKey: String): Name

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun insertAllNames(names: List<Name>)

}