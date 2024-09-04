package com.example.eonet.di.modules


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.eonet.domain.utils.ViewModelKey
import com.example.eonet.view.viewmodel.CategoriesDataViewModel
import com.example.eonet.view.viewmodel.CategoriesViewModel
import com.example.eonet.view.viewmodel.EventDetailViewModel
import com.example.eonet.view.viewmodel.HostViewModel
import com.example.eonet.view.viewmodel.DaggerViewModelFactory
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

    @Binds
    @IntoMap
    @ViewModelKey(EventDetailViewModel::class)
    abstract fun bindCategoryDetailViewModel(eventDetailViewModel: EventDetailViewModel): ViewModel
}