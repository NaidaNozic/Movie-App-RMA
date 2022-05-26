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
                        movies.add(Movie(id.toLong(), title, overview, releaseDate, null, posterPath, " "))
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
    fun getSimilarMovies(): Map<String, List<String>> {
        return similarMovies()
    }
    suspend fun getSimilarMovies( id: Long
    ) : GetSimilarResponse?{
        return withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.getSimilar(id)
            val responseBody = response.body()
            return@withContext responseBody
        }
    }
    suspend fun getMovie(id: Long
    ) : Movie?{
        return withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.getMovie(id)
            val responseBody = response.body()
            return@withContext responseBody
        }
    }
    suspend fun getMovieItemDetails(
        id: Long
    ):Result<Movie>{
        return withContext(Dispatchers.IO) {
            val url1 = "https://api.themoviedb.org/3/movie/$id?api_key=$tmdb_api_key"
            try {
                val url = URL(url1)
                var movie=Movie(0, "Test", "Test", "Test", "Test", "Test", "Test")
                (url.openConnection() as? HttpURLConnection)?.run {
                    val result = this.inputStream.bufferedReader().use { it.readText() }
                    val jsonObject = JSONObject(result)

                    movie.posterPath = jsonObject.getString("poster_path")
                    movie.title = jsonObject.getString("title")
                    movie.id = jsonObject.getLong("id")
                    movie.homepage = jsonObject.getString("homepage")
                    movie.overview = jsonObject.getString("overview")
                    movie.backdropPath = jsonObject.getString("backdrop_path")
                    movie.releaseDate = jsonObject.getString("release_date")
                    //movie.genre = jsonObject.getJSONArray("genres").getJSONObject(0).getString("name")
                }
                return@withContext Result.Success(movie);
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
    suspend fun getUpcomingMovies() : GetMoviesResponse?{
        return withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.getUpcomingMovies()
            val responseBody = response.body()
            return@withContext responseBody
        }
    }

            suspend fun getSimilarMoviesAPI(
        id: Long
    ): Result<MutableList<String>> {
        return withContext(Dispatchers.IO) {
            val url1 = "https://api.themoviedb.org/3/movie/$id/similar?api_key=$tmdb_api_key"
            try {
                val url = URL(url1)
                var similar:MutableList<String> = mutableListOf()
                (url.openConnection() as? HttpURLConnection)?.run {
                    val result = this.inputStream.bufferedReader().use { it.readText() }
                    val jo = JSONObject(result)
                    val items: JSONArray = jo.getJSONArray("results")
                    for (i in 0 until items.length()) {
                        val slicni = items.getJSONObject(i)
                        val title = slicni.getString("title")
                        similar.add(title)
                        if (i == 4) break
                    }
                }
                return@withContext Result.Success(similar);
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
