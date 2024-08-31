package com.example.eonet.dagger

import com.example.eonet.views.HostActivity
import com.example.eonet.dagger.modules.AppModule
import com.example.eonet.dagger.modules.NetworkModule
import com.example.eonet.dagger.modules.ViewModelModule
import com.example.eonet.views.fragments.CategoriesFragment
import com.example.eonet.views.fragments.CategoryDataFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class,NetworkModule::class,ViewModelModule::class])
interface AppComponent {
    fun inject(hostActivity: HostActivity)

    fun inject(categoriesFragment: CategoriesFragment)

    fun inject(categoryDataFragment: CategoryDataFragment)

}