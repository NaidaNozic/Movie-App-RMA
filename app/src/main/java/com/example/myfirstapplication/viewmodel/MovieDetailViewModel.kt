package com.example.myfirstapplication.viewmodel


import com.example.myfirstapplication.data.Movie
import com.example.myfirstapplication.data.MovieRepository

// preko MovieDetailViewModel-a ćemo filtrirati postojeće liste iz repozitorija kako bi dobili
//odgovarajući film.
class MovieDetailViewModel {

    fun getMovieByTitle(name:String):Movie {
        var movies: ArrayList<Movie> = arrayListOf()
        movies.addAll(MovieRepository.getRecentMovies())
        movies.addAll(MovieRepository.getFavoriteMovies())
        val movie = movies.find { movie -> name.equals(movie.title) }
        //ako film ne postoji vratimo testni
        return movie ?: Movie(0, "Test", "Test", "Test", "Test", "Test")
    }
}