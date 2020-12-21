package zekai.myapplication.paypaycurrency.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import zekai.myapplication.paypaycurrency.EventObserver
import zekai.myapplication.paypaycurrency.MainActivity
import zekai.myapplication.paypaycurrency.databinding.MainFragmentBinding
import zekai.myapplication.paypaycurrency.di.Injectable
import javax.inject.Inject


class MainFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewDataBing: MainFragmentBinding

    val mainViewModel by viewModels<MainViewModel>({ activity as MainActivity }) { viewModelFactory }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View  {
        // Inflate the layout for this fragment
        viewDataBing = MainFragmentBinding.inflate(inflater, container, false).apply {
            viewmodel = mainViewModel
            amountEdit.setText("1")
            amountEdit.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    amountEdit.clearFocus()
                }
                false
            })
            amountEdit.addTextChangedListener ( afterTextChanged = {
                if(!it.isNullOrEmpty()){
                    val amount = it.toString().toDouble()
                    mainViewModel.amount.value = amount
                }
            })

            allCurrenciesRecyclerView.layoutManager = GridLayoutManager(context,3)
            val gridAdapter = GridAdapter()
            allCurrenciesRecyclerView.adapter = gridAdapter
            subscribeAdapterUI(gridAdapter)
        }
        viewDataBing.lifecycleOwner = this.viewLifecycleOwner
        return viewDataBing.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mainViewModel.loadData()
        mainViewModel.navigationEvent.observe(viewLifecycleOwner, EventObserver {
            findNavController().navigate(it)
        })
        mainViewModel.errorEvent.observe(viewLifecycleOwner, EventObserver{
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })
    }

    private fun subscribeAdapterUI(gridAdapter:GridAdapter){
        mainViewModel.rates.observe(viewLifecycleOwner){result ->
            gridAdapter.initContents(result)
        }
        mainViewModel.amount.observe(viewLifecycleOwner){
            gridAdapter.setAmount(it)
        }
        mainViewModel.currentSelectRate.observe(viewLifecycleOwner){
            gridAdapter.setCurrentSelectRate(it)
        }
    }
}