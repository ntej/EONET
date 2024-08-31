package com.example.eonet.dagger.modules


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.eonet.utils.ViewModelKey
import com.example.eonet.viewmodel.CategoriesDataViewModel
import com.example.eonet.viewmodel.CategoriesViewModel
import com.example.eonet.viewmodel.HostViewModel
import com.example.eonet.viewmodel.DaggerViewModelFactory
import dagger.Binds
import dagger.Module
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
    @ViewModelKey(HostViewModel::class)
    abstract fun bindHomeViewModel(hostViewModel: HostViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CategoriesViewModel::class)
    abstract fun bindCategoriesViewModel(categoriesViewModel: CategoriesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CategoriesDataViewModel::class)
    abstract fun bindCategoriesDataViewModel(categoriesDataViewModel: CategoriesDataViewModel): ViewModel
}