package com.example.eonet.di

import com.example.eonet.view.HostActivity
import com.example.eonet.di.modules.AppModule
import com.example.eonet.di.modules.NetworkModule
import com.example.eonet.di.modules.ViewModelModule
import com.example.eonet.view.CategoriesFragment
import com.example.eonet.view.CategoryDataFragment
import com.example.eonet.view.EventDetailFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class,NetworkModule::class,ViewModelModule::class])
interface AppComponent {
    fun inject(hostActivity: HostActivity)

    fun inject(categoriesFragment: CategoriesFragment)

    fun inject(categoryDataFragment: CategoryDataFragment)

    fun inject(eventDetailFragment: EventDetailFragment)

}