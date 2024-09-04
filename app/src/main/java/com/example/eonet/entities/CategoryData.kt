package com.example.eonet.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class CategoryData(
    val description: String?,
    val events: List<Event>,
    val link: String?,
    val title: String?
) : Parcelable

@Parcelize
data class CategoryX(
    val id: String?,
    val title: String?
) : Parcelable

@Parcelize
data class Event(
    val categories: List<CategoryX>,
    val closed: String?,
    val description: String?,
    val geometry: List<Geometry>,
    val id: String?,
    val link: String?,
    val sources: List<Source>,
    val title: String?
) : Parcelable

@Parcelize
data class Geometry(
    val coordinates: List<Double>?,
    val date: String?,
    val magnitudeUnit: String?,
    val magnitudeValue: Double?,
    val type: String?
) : Parcelable
@Parcelize
data class Source(
    val id: String?,
    val url: String?
) : Parcelable