package com.example.myfirstapplication.data

object ActorsRepository {
    fun getActors():Map<String,List<String>>{
        return actors()
    }
    fun getSimilarMovies():Map<String,List<String>>{
        return similarMovies()
    }
}