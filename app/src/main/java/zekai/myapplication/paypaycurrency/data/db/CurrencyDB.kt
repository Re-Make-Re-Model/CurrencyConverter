package zekai.myapplication.paypaycurrency.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Main database description.
 */
@Database(
        entities = [
            Name::class,
            Rate::class],
        version = 1,
        exportSchema = false
)
abstract class CurrencyDB : RoomDatabase() {

    abstract fun nameDao(): NameDao

    abstract fun rateDao(): RateDao


}