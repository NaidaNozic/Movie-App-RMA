package com.example.myfirstapplication.viewmodel

import com.example.myfirstapplication.data.Movie;
import com.example.myfirstapplication.data.MovieRepository;

class MovieListViewModel {
    fun getFavoriteMovies():List<Movie>{
        return MovieRepository.getFavoriteMovies();
    }
    fun getRecentMovies():List<Movie>{
        return MovieRepository.getRecentMovies();
    }

}