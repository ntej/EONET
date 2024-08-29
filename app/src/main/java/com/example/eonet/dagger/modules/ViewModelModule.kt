package com.example.eonet.dagger.modules


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.eonet.utils.ViewModelKey
import com.example.eonet.viewmodel.HomeViewModel
import com.example.eonet.viewmodel.DaggerViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.internal.Provider
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindsViewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory

        /****
     * you can bind your HomeViewModel to the ViewModel type using @IntoMap and @ViewModelKey annotations.
     *
     * The @IntoMap annotation allows Dagger to collect all ViewModel bindings into a Map.
     * The @ViewModelKey is a custom annotation that helps Dagger map the ViewModel class to its provider.
     */
    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel
}