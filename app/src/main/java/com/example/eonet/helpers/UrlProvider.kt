package com.example.eonet.helpers

import com.example.eonet.helpers.IUrlProvider

class UrlProvider: IUrlProvider {
    override fun eonetBaseUrl():String {
        return "https://eonet.gsfc.nasa.gov/api/v3/"
    }
}