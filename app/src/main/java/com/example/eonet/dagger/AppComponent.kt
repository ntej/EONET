package com.example.eonet.dagger

import androidx.lifecycle.ViewModelProvider
import com.example.eonet.views.HomeActivity
import com.example.eonet.dagger.modules.AppModule
import com.example.eonet.dagger.modules.NetworkModule
import com.example.eonet.dagger.modules.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class,NetworkModule::class,ViewModelModule::class])
interface AppComponent {
    fun inject(homeActivity: HomeActivity)
}