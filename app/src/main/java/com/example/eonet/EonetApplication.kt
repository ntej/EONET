package com.example.eonet

import android.app.Application
import com.example.eonet.di.AppComponent
import com.example.eonet.di.DaggerAppComponent

class EonetApplication : Application() {
    // Instance of the AppComponent that will be used by all the Activities in the project
    lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
    }
}