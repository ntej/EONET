package com.example.eonet.view.viewmodel

import androidx.lifecycle.ViewModel
import com.example.eonet.domain.utils.DateFormatter
import javax.inject.Inject


class EventDetailViewModel @Inject constructor(private val dateFormatter: DateFormatter) : ViewModel() {

    fun formatDate(date:String?):String {
        return dateFormatter.formatDate(date)
    }
}