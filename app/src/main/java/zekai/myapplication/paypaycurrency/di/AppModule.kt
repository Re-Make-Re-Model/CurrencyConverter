package zekai.myapplication.paypaycurrency.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import zekai.myapplication.paypaycurrency.data.api.CurrencyService
import zekai.myapplication.paypaycurrency.data.api.RetrofitClient
import zekai.myapplication.paypaycurrency.data.db.CurrencyDB
import zekai.myapplication.paypaycurrency.data.db.NameDao
import zekai.myapplication.paypaycurrency.data.db.RateDao
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {
    @Singleton
    @Provides
    fun provideCurrencyService(): CurrencyService {
        return Retrofit.Builder()
            .baseUrl("http://api.currencylayer.com/")
            .client(RetrofitClient.client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CurrencyService::class.java)
    }

    @Singleton
    @Provides
    fun provideDb(app: Application): CurrencyDB {
        return Room
            .databaseBuilder(app, CurrencyDB::class.java, "currency.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideNameDao(db: CurrencyDB): NameDao {
        return db.nameDao()
    }

    @Singleton
    @Provides
    fun provideRateDao(db: CurrencyDB): RateDao {
        return db.rateDao()
    }
}