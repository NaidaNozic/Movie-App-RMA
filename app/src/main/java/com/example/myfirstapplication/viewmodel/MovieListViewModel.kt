package com.example.myfirstapplication.viewmodel

import android.content.Context
import com.example.myfirstapplication.data.GetMoviesResponse
import com.example.myfirstapplication.data.Movie
import com.example.myfirstapplication.data.MovieRepository
import com.example.myfirstapplication.data.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

//searchDone i onError metode su implementirane unutar SearchFragment-a i služe za izmjenu UI-a.
class MovieListViewModel(private val searchDone: ((movies: List<Movie>) -> Unit)?,
                         private val onError: (()->Unit)?) {

    val scope = CoroutineScope(Job() + Dispatchers.Main)//CoroutineScope vodi računa o svim pokrenutim Coroutine -ama

    fun getFavorites(context: Context, onSuccess: (movies: List<Movie>) -> Unit, //vjezba9
                     onError: () -> Unit){
        scope.launch{
            val result = MovieRepository.getFavoriteMovies(context)
            when (result) {
                is List<Movie> -> onSuccess?.invoke(result)
                else-> onError?.invoke()
            }
        }
    }


    fun getFavoriteMovies():List<Movie>{
        return MovieRepository.getFavoriteMovies();
    }
    fun getRecentMovies():List<Movie>{
        return MovieRepository.getRecentMovies();
    }
    fun getUpcoming( onSuccess: (movies: List<Movie>) -> Unit,
                     onError: () -> Unit){
        // Create a new coroutine on the UI thread
        scope.launch{
            // Make the network call and suspend execution until it finishes
            val result = MovieRepository.getUpcomingMovies()
            // Display result of the network request to the user
            when (result) {
                is GetMoviesResponse -> onSuccess?.invoke(result.movies)
                else-> onError?.invoke()
            }
        }
    }

    fun search(query: String){
        // Kreira se Coroutine na UI
        scope.launch{
        // Vrši se poziv servisa i suspendira se rutina dok se `withContext` ne završi
        val result = MovieRepository.searchRequest(query)
        // Prikaže se rezultat korisniku na glavnoj niti
            when (result) {
                is Result.Success<List<Movie>> -> searchDone?.invoke(result.data)
                else-> onError?.invoke()
            }
        }
    }
}