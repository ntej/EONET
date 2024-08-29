package com.example.eonet.dagger.modules

import com.example.eonet.helpers.IUrlProvider
import com.example.eonet.helpers.UrlProvider
import com.example.eonet.repos.EOCategoriesRepo
import com.example.eonet.viewmodel.HomeViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ViewModelModule {
    @Singleton
    @Provides
    fun provideHomeActivityViewModelFactory(eoCategoriesRepo: EOCategoriesRepo): HomeViewModelFactory {
        return HomeViewModelFactory(eoCategoriesRepo)
    }
}