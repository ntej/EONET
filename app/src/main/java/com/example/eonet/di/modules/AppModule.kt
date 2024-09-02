package com.example.eonet.di.modules

import com.example.eonet.data.IUrlProvider
import com.example.eonet.data.UrlProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @Singleton
    @Provides
    fun provideUrlProvider(): IUrlProvider {
        return UrlProvider()
    }
}