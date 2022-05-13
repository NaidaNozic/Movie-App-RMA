package com.example.myfirstapplication.viewmodel

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

    fun getFavoriteMovies():List<Movie>{
        return MovieRepository.getFavoriteMovies();
    }
    fun getRecentMovies():List<Movie>{
        return MovieRepository.getRecentMovies();
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