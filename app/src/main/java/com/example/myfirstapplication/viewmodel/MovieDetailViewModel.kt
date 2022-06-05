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

   /* fun getMovieByTitle(name:String):Movie {
        var movies: ArrayList<Movie> = arrayListOf()
        movies.addAll(MovieRepository.getRecentMovies())
        movies.addAll(MovieRepository.getFavoriteMovies())
        val movie = movies.find { movie -> name.equals(movie.title) }
        //ako film ne postoji vratimo testni
        return movie ?: Movie(0, "Test", "Test", "Test", "Test",
            "https://www.imdb.com/title/tt0120737/","https://www.imdb.com/title/tt0120737/")
    }*/
    fun getMovieByTitle(name:String):Movie{
        var movies: ArrayList<Movie> = arrayListOf()
        val movie= movies.find { movie -> name.equals(movie.title) }
        return movie?:Movie(0,"Test","Test","Test","Test","Test","Test")
    }

    fun writeDB(context: Context, movie:Movie, onSuccess: (movies: String) -> Unit, //vjezba9
                onError: () -> Unit){
        scope.launch{
            val result = MovieRepository.writeFavorite(context,movie)
            when (result) {
                is String -> onSuccess?.invoke(result)
                else-> onError?.invoke()
            }
        }
    }
    /*fun deleteDB(context: Context, movie:Movie, onSuccess: (poruka: String) -> Unit, onError: () -> Unit){
        scope.launch {
            val result = MovieRepository.deleteMovie(context,movie)
            when (result) {
                is String -> onSuccess?.invoke(result)
                else-> onError?.invoke()
            }
        }
    }*/
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


    fun getMovieDetails(query: Long, onSuccess: (movies: Movie) -> Unit,
                 onError: () -> Unit){
        scope.launch{
            val result = MovieRepository.getMovie(query)
            when (result) {
                is Movie -> onSuccess?.invoke(result)
                else-> onError?.invoke()
            }
        }
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
    fun getSimilarByTitle(name: String):List<String>{
        return MovieRepository.getSimilarMovies()?.get(name)?: emptyList()
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

}