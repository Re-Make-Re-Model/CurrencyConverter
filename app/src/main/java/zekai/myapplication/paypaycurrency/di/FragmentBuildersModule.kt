package zekai.myapplication.paypaycurrency.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import zekai.myapplication.paypaycurrency.ui.main.MainFragment
import zekai.myapplication.paypaycurrency.ui.main.dialog.CurrencyListDialog

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeMainFragment(): MainFragment

    @ContributesAndroidInjector
    abstract fun contributeDialogFragment(): CurrencyListDialog
}
