package com.example.eonet.model

data class Categories(
    val categories: List<Category>,
    val description: String,
    val link: String,
    val title: String
)

data class Category(
    val description: String,
    val id: String,
    val layers: String,
    val link: String,
    val title: String
)