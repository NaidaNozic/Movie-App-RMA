package com.example.myfirstapplication.viewmodel

import android.util.Log
import com.example.myfirstapplication.data.ActorsRepository
import com.example.myfirstapplication.data.Movie
import com.example.myfirstapplication.data.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import com.example.myfirstapplication.data.Result
//valja

// preko MovieDetailViewModel-a ćemo filtrirati postojeće liste iz repozitorija kako bi dobili
//odgovarajući film.
class MovieDetailViewModel(private val OpenMovie: ((movies: Movie) -> Unit)?,
                           private val OpenSimilarMovie: ((similar: MutableList<String>) -> Unit)?,
                           private val OpenActors: ((actors: MutableList<String>) -> Unit)?,
                           ) {
    val scope = CoroutineScope(Job() + Dispatchers.Main)

    fun getMovieByTitle(name:String):Movie {
        var movies: ArrayList<Movie> = arrayListOf()
        movies.addAll(MovieRepository.getRecentMovies())
        movies.addAll(MovieRepository.getFavoriteMovies())
        val movie = movies.find { movie -> name.equals(movie.title) }
        //ako film ne postoji vratimo testni
        return movie ?: Movie(0, "Test", "Test", "Test", "Test", "Test",
            "https://www.imdb.com/title/tt0120737/","https://www.imdb.com/title/tt0120737/")
    }

   fun getSimilarByTitle(name: String):List<String>{
       return MovieRepository.getSimilarMovies()?.get(name)?: emptyList()
   }
    fun getSimilarByMovie(name:String):List<String>{
        var similar:Map<String,List<String>> = ActorsRepository.getSimilarMovies()
        return similar?.get(name)?: emptyList()
    }
    fun getMovieDetails(query: Long){
        scope.launch{
            val result = MovieRepository.getMovieItemDetails(query)
            when (result) {
                is Result.Success<Movie> -> OpenMovie?.invoke(result.data)
                else-> Log.v("meh","meh")
            }
        }
    }
    fun getSimilarMoviesById(query: Long){
        scope.launch{
            val result = MovieRepository.getSimilarMoviesAPI(query)
            when (result) {
                is Result.Success<MutableList<String>> -> OpenSimilarMovie?.invoke(result.data)
                else-> Log.v("meh","meh")
            }
        }
    }
    fun getActorsById(query: Long){
        scope.launch{
            val result = ActorsRepository.getActors(query)
            when (result) {
                is Result.Success<MutableList<String>> -> OpenActors?.invoke(result.data)
                else-> Log.v("meh","meh")
            }
        }
    }

    fun getActorsByTitle(name: String):List<String>{
        return ActorsRepository.getActorMovies()?.get(name)?: emptyList()
    }
}