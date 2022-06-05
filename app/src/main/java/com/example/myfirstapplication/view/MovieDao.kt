package com.example.myfirstapplication.view

import androidx.room.*
import com.example.myfirstapplication.data.Movie

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie")
    suspend fun getAll(): List<Movie>
    @Insert
    suspend fun insertAll(vararg movies: Movie)
}