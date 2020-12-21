package zekai.myapplication.paypaycurrency.data.repository

import zekai.myapplication.paypaycurrency.data.api.BaseResult
import zekai.myapplication.paypaycurrency.data.api.CurrencyService
import zekai.myapplication.paypaycurrency.data.api.SafeCall
import zekai.myapplication.paypaycurrency.data.db.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyRepository @Inject constructor(
    private val db: CurrencyDB,
    private val nameDao: NameDao,
    private val rateDao: RateDao,
    private val currencyService: CurrencyService
) : SafeCall() {

    suspend fun loadCurrenciesList(): BaseResult<List<Name>> {
        return if(nameDao.getAllNames().isNotEmpty()){
            BaseResult.Success(nameDao.getAllNames())
        }else{
            loadListFromNetwork()
        }
    }

    private suspend fun loadListFromNetwork(): BaseResult<List<Name>> {
        return safeApiCall(call = { fetchList() })
    }

    private suspend fun fetchList(): BaseResult<List<Name>> {
        val response = currencyService.getList()
        val result = response.currencies.entries.map { item -> Name(item.key, item.value) }
        return executeResponse(result,{
            db.runInTransaction {
                nameDao.insertAllNames(result)
            }
        })
    }

    suspend fun loadLiveRates(): BaseResult<List<Rate>> {
        val localTime = System.currentTimeMillis()
        print(localTime)
        return if(rateDao.getAllRates().isNotEmpty() && (localTime/1000 - rateDao.getAllRates()[0].timestamp <= 30*60)){
            BaseResult.Success(rateDao.getAllRates())
        }else{
            loadLiveRatesFromNetwork()
        }
    }

    private suspend fun loadLiveRatesFromNetwork(): BaseResult<List<Rate>> {
        return safeApiCall(call = { fetchLive() })
    }

    private suspend fun fetchLive(): BaseResult<List<Rate>> {
        val response = currencyService.getLive()
        val result = response.quotes.entries.map { item -> Rate(item.key, item.key.take(3), item.key.takeLast(3), response.timestamp, item.value) }
        return executeResponse(result,{
            db.runInTransaction {
                rateDao.insertAllRates(result)
            }
        })
    }
}