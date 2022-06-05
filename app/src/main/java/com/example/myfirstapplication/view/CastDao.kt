package com.example.myfirstapplication.view

import androidx.room.*
import com.example.myfirstapplication.data.Cast

@Dao
interface CastDao {
    @Insert
    suspend fun insertAll(vararg cast: Cast)

    @Transaction
    @Delete
    suspend fun deleteCast(cast: List<Cast>)
}