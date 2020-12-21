package zekai.myapplication.paypaycurrency.ui.main

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import zekai.myapplication.paypaycurrency.Event
import zekai.myapplication.paypaycurrency.R
import zekai.myapplication.paypaycurrency.data.api.BaseResult
import zekai.myapplication.paypaycurrency.data.db.Name
import zekai.myapplication.paypaycurrency.data.db.Rate
import zekai.myapplication.paypaycurrency.data.repository.CurrencyRepository
import zekai.myapplication.paypaycurrency.utils.DateFormatUtils.getDateTime
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: CurrencyRepository): ViewModel() {

    private val mNavigationEvent = MutableLiveData<Event<Int>>()
    val navigationEvent: LiveData<Event<Int>> = mNavigationEvent

    private val mErrorEvent = MutableLiveData<Event<String>>()
    val errorEvent: LiveData<Event<String>> = mErrorEvent

    val names = MutableLiveData<List<Name>>()
    val rates = MutableLiveData<List<Rate>>()

    val selectedCurrency = MutableLiveData<String>("USD")
    val selectedCurrencyFullName = MutableLiveData<String>("United States Dollar")
    val amount = MutableLiveData<Double>(1.0)
    val currentSelectRate = MutableLiveData<Double>(1.0)//default currency is USD, so it is 1.0
    val updateAt = MutableLiveData<String>()
    val updateInfoVisibility = MutableLiveData<Boolean>(false)

    fun loadData(){
        viewModelScope.launch(Dispatchers.Default) {

            withContext(Dispatchers.IO) {
                val nameResult =  repository.loadCurrenciesList()
                withContext(Dispatchers.Main){
                    if(nameResult is BaseResult.Success){
                        names.value = nameResult.data
                    }else{
                        mErrorEvent.value = Event("Sorry, we cannot get data now.")
                    }
                }

                val rateResult =  repository.loadLiveRates()
                withContext(Dispatchers.Main){
                    if(rateResult is BaseResult.Success){
                        rates.value = rateResult.data
                        updateInfoVisibility.value = true
                        updateAt.value = getDateTime(rateResult.data.get(0).timestamp)
                    }else{
                        mErrorEvent.value = Event("Sorry, we cannot get data now.")
                        updateInfoVisibility.value = false
                    }
                }

            }
        }
    }
    fun openDialog(){
        viewModelScope.launch(Dispatchers.Default) {

            withContext(Dispatchers.Main) {
                if(!names.value.isNullOrEmpty()){
                    mNavigationEvent.value = Event(R.id.action_mainFragment_to_currencyListDialog)
                }else{
                    mErrorEvent.value = Event("Sorry, we cannot get currency list.")
                }

            }
        }
    }
}