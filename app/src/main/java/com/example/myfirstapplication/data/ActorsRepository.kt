package com.example.myfirstapplication.data

import com.example.myfirstapplication.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

object ActorsRepository {

    private const val tmdb_api_key : String = BuildConfig.TMDB_API_KEY

    fun getActorMovies():Map<String,List<String>>{
        return actors()
    }

    suspend fun getCast( id: Long
    ) : GetCastResponse?{
        return withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.getCast(id)
            val responseBody = response.body()
            return@withContext responseBody
        }
    }

}