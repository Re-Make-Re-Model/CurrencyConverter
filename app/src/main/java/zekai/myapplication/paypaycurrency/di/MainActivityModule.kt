package zekai.myapplication.paypaycurrency.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import zekai.myapplication.paypaycurrency.MainActivity

@Suppress("unused")
@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeMainActivity(): MainActivity
}