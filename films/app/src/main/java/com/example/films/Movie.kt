package com.example.films

import java.io.Serializable

data class Movie(
    val title: String,
    val description: String,
    val rating: Double,
    val imageUrl: String
) : Serializable