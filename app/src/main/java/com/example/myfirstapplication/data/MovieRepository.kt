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

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

object MovieRepository {

    private const val tmdb_api_key: String = BuildConfig.TMDB_API_KEY

    fun getFavoriteMovies() : List<Movie> {
        return favoriteMovies();
    }
    fun getRecentMovies() : List<Movie> {
        return recentMovies();
    }
    suspend fun searchRequest(
        query: String
    ): Result<List<Movie>>{
        return withContext(Dispatchers.IO) {
            try {
                val movies = arrayListOf<Movie>()
                val url1 = "https://api.themoviedb.org/3/search/movie?api_key=$tmdb_api_key&query=$query"
                val url = URL(url1) //formira se ispravni url
                (url.openConnection() as? HttpURLConnection)?.run { //otvara se konekcija i vrsi se poziv web servisa
                    val result = this.inputStream.bufferedReader().use { it.readText() }
                    //Rezultat poziva web servisa je u obliku InputStream-a . Ovaj objekat ćemo
                    //pretvoriti u String .
                    val jo = JSONObject(result)
                    //Navedeni string sadrži rezultat poziva web servisa. Ovaj rezultat je u JSON
                    //formatu. Da bi radili sa podacima u JSON formatu koristimo JSONObject klasu.
                    //Kreirat ćemo novi JSONObject kojeg ćemo incializirati sa stringom rezultata.
                    //Ovaj objekat će da sadrži čitav JSON rezultata.
                    val results = jo.getJSONArray("results")
                    //Iz navedenog objekta možemo izdvojiti djelove rezultata koji nas interesuju. Da
                    //bi dobili niz objekata koji nas interesuju, izdvojit ćemo JSONArray sa
                    //nazivnom results iz JSON objekta rezultata.
                    for (i in 0 until results.length()) {
                        //Dalje treba proći kroz listu svih rezultata, preuzeti podatke koji nas
                        //interesuju (naziv filma, id, posterPath, overview, releaseDate).
                        val movie = results.getJSONObject(i)
                        val title = movie.getString("title")
                        val id = movie.getInt("id")
                        val posterPath = movie.getString("poster_path")
                        val overview = movie.getString("overview")
                        val releaseDate = movie.getString("release_date")
                        movies.add(Movie(id.toLong(), title, overview, releaseDate,
                            null, null, posterPath, " "))
                        if (i == 5) break
                    }
                }
                return@withContext Result.Success(movies) //Vraćamo podatke
            }
            catch (e: MalformedURLException) {
                return@withContext Result.Error(Exception("Cannot open HttpURLConnection"))
            } catch (e: IOException) {
                return@withContext Result.Error(Exception("Cannot read stream"))
            } catch (e: JSONException) {
                return@withContext Result.Error(Exception("Cannot parse JSON"))
            }

        }
    }

}
