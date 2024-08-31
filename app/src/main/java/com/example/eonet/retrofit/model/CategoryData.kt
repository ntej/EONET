package com.example.eonet.retrofit.model

import java.util.Date

data class CategoryData(
    val description: String,
    val events: List<Event>,
    val link: String,
    val title: String
)

data class CategoryX(
    val id: String,
    val title: String
)

data class Event(
    val categories: List<CategoryX>,
    val closed: Any?,
    val description: String?,
    val geometry: List<Geometry>,
    val id: String,
    val link: String,
    val sources: List<Source>,
    val title: String
)

data class Geometry(
    val coordinates: List<Double>,
    val date: String,
    val magnitudeUnit: String,
    val magnitudeValue: Double,
    val type: String
)

data class Source(
    val id: String,
    val url: String
)