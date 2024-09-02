package com.example.eonet.data

import com.example.eonet.data.IUrlProvider

class UrlProvider: IUrlProvider {
    override fun eonetBaseUrl():String {
        return "https://eonet.gsfc.nasa.gov/api/v3/"
    }
}