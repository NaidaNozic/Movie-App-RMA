package com.example.myfirstapplication.viewmodel

import android.content.Context
import android.util.Log
import com.example.myfirstapplication.data.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MovieDetailViewModel() {
    val scope = CoroutineScope(Job() + Dispatchers.Main)

    fun getMovieByTitle(name:String):Movie{
        var movies: ArrayList<Movie> = arrayListOf()
        val movie= movies.find { movie -> name.equals(movie.title) }
        return movie?:Movie(0,"Test","Test","Test","Test","Test","Test")
    }

    fun getActorsByTitle(name: String):List<String>{
        return ActorsRepository.getActorMovies()?.get(name)?: emptyList()
    }

    fun getSimilarByTitle(name: String):List<String>{
        return MovieRepository.getSimilarMovies()?.get(name)?: emptyList()
    }


    fun getSimilarMoviesById(query: Long, onSuccess: (movies: List<Movie>) -> Unit,
                             onError: () -> Unit){
        scope.launch{
            val result = MovieRepository.getSimilarMovies(query)
            when (result) {
                is GetSimilarResponse -> onSuccess?.invoke(result.movies)
                else-> onError?.invoke()
            }
        }
    }
    fun getSimilarMoviesByIdDB(context: Context,id: Long, onSuccess: (movies: List<Movie>) -> Unit,
                               onError: () -> Unit){
        scope.launch{
            val result = MovieRepository.getSimilarMoviesDB(context,id)
            when (result) {
                is List<*> -> onSuccess?.invoke(result)
                else-> onError?.invoke()
            }
        }
    }
    fun getActorsById(query: Long, onSuccess: (actors: List<Cast>) -> Unit,
                      onError: () -> Unit){
        scope.launch{
            val result = ActorsRepository.getCast(query)
            when (result) {
                is GetCastResponse -> onSuccess?.invoke(result.cast)
                else-> onError?.invoke()
            }
        }
    }
    fun getActorsByIdDB(context: Context, id: Long, onSuccess: (actors: List<Cast>) -> Unit,
                        onError: () -> Unit){
        scope.launch{
            val result = MovieRepository.getCastDB(context,id)
            when (result) {
                is List<*> -> onSuccess?.invoke(result)
                else-> onError?.invoke()
            }
        }
    }
    fun writeDB(context: Context, movie:Movie, onSuccess: (movies: String) -> Unit,
                onError: () -> Unit){
        scope.launch{
            val result = MovieRepository.writeFavorite(context,movie)
            when (result) {
                is String -> onSuccess?.invoke(result)
                else-> onError?.invoke()
            }
        }
    }
    fun deleteDB(context: Context, movie:Movie, onSuccess: (poruka: String) -> Unit, onError: () -> Unit){
        scope.launch {
            val result = MovieRepository.deleteMovie(context,movie)
            when (result) {
                is String -> onSuccess?.invoke(result)
                else-> onError?.invoke()
            }
        }
    }

    fun getMovieFromDB(context: Context, id:Long, onSuccess: (movies: Movie) -> Unit,
                       onError: () -> Unit){

        // Create a new coroutine on the UI thread
        scope.launch{

            // Make the network call and suspend execution until it finishes
            val result = MovieRepository.getMovieDB(context,id)

            // Display result of the network request to the user
            when (result) {
                is Movie -> onSuccess?.invoke(result)
                else-> onError?.invoke()
            }
        }
    }

    fun getMovie(query: Long, onSuccess: (movies: Movie) -> Unit,
                 onError: () -> Unit){
        scope.launch{
            val result = MovieRepository.getMovie(query)
            when (result) {
                is Movie -> onSuccess?.invoke(result)
                else-> onError?.invoke()
            }
        }
    }

}