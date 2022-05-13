package com.example.myfirstapplication.data

data class Movie (
    val id: Long,
    val title: String,
    val overview: String,
    val releaseDate: String,
    val homepage: String?,
    val genre: String?,
    var posterPath: String,
    var backdropPath: String
)