package com.example.eonet.dagger.modules

import com.example.eonet.helpers.IUrlProvider
import com.example.eonet.helpers.UrlProvider
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