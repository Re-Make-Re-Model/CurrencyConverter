package zekai.myapplication.paypaycurrency.ui.main.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import zekai.myapplication.paypaycurrency.MainActivity
import zekai.myapplication.paypaycurrency.databinding.CurrencyListDialogBinding
import zekai.myapplication.paypaycurrency.di.Injectable
import zekai.myapplication.paypaycurrency.ui.main.MainViewModel
import javax.inject.Inject

class CurrencyListDialog: DialogFragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewDataBing: CurrencyListDialogBinding

    val viewModel by viewModels<MainViewModel>({ activity as MainActivity }) { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBing = CurrencyListDialogBinding.inflate(inflater,container,false).apply {
            var names = viewModel.names.value?.map { item -> (item.currencyKey?:"") + " - "+item.currencyName?:"" }?: listOf()
            val adapter = context?.let { ArrayAdapter(it, android.R.layout.simple_list_item_1, names) }
            currenciesListView.adapter = adapter
            searchEditText.addTextChangedListener(afterTextChanged = {
                val text = it.toString()
                val fullNames = viewModel.names.value?.map { item -> (item.currencyKey?:"") + " - "+item.currencyName?:"" }?: listOf()
                if(text.isNotBlank()){
                    names = fullNames.filter{it.contains(text.trim(),true)}
                }
                adapter?.clear()
                adapter?.addAll(names)
                adapter?.notifyDataSetInvalidated()
            })
            currenciesListView.setOnItemClickListener{adapterView, view, position, id ->
                viewModel.selectedCurrency.value = names?.get(position)?.take(3)
                viewModel.selectedCurrencyFullName.value = names?.get(position).substring(6)
                viewModel.currentSelectRate.value = viewModel.rates.value?.filter { it.currencyTo == viewModel.selectedCurrency.value }?.first()?.rate?:1.0
                dismiss()
            }

        }
        viewDataBing.lifecycleOwner = this.viewLifecycleOwner

        return viewDataBing.root
    }
}