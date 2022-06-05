package com.example.myfirstapplication.view

import androidx.room.*
import com.example.myfirstapplication.data.Movie
import com.example.myfirstapplication.data.MovieWithCast
import com.example.myfirstapplication.data.MovieWithSimilarMovies

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie")
    suspend fun getAll(): List<Movie>
    @Insert
    suspend fun insertAll(vararg movies: Movie)

    @Query("SELECT * FROM movie WHERE id=:id AND favourite=1 LIMIT 1")
    suspend fun findById(id: Long): Movie

    @Transaction
    @Query("SELECT * FROM movie WHERE id=:id LIMIT 1")
    suspend fun getMovieAndCastById(id:Long): MovieWithCast

    @Transaction
    @Query("SELECT * FROM movie WHERE id=:id LIMIT 1")
    suspend fun getSimilarMoviesById(id:Long): MovieWithSimilarMovies
}