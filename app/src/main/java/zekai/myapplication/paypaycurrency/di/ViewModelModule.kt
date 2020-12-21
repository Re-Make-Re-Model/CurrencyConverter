package zekai.myapplication.paypaycurrency.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import zekai.myapplication.paypaycurrency.ui.main.MainViewModel
import zekai.myapplication.paypaycurrency.viewmodel.CurrencyViewModelFactory

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindUserViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: CurrencyViewModelFactory): ViewModelProvider.Factory
}
