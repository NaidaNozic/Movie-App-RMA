package com.example.myfirstapplication

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.myfirstapplication.data.Movie
import com.example.myfirstapplication.view.*
import com.example.myfirstapplication.viewmodel.MovieDetailViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationBarView

class MovieDetailActivity : AppCompatActivity() {
    private var movieDetailViewModel =  MovieDetailViewModel()
    private lateinit var bottomNavigation: BottomNavigationView
    private  var movie=Movie(0,"Test","Test","Test","Test","Test","Test")
    private lateinit var title : TextView
    private lateinit var overview : TextView
    private lateinit var releaseDate : TextView
    private lateinit var genre : TextView
    private lateinit var website : TextView
    private lateinit var poster : ImageView
    private lateinit var backdrop : ImageView
    private lateinit var shareButton : FloatingActionButton
    private lateinit var addFavorite : Button
    private lateinit var deleteFavorite : Button
    private val posterPath = "https://image.tmdb.org/t/p/w780"
    private val backdropPath = "https://image.tmdb.org/t/p/w500"

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId){
            R.id.navigation_actors -> {
                var actorsFragment:ActorsFragment
                if(addFavorite.visibility==View.GONE){
                    actorsFragment = ActorsFragment(movie.title,movie.id,true)
                } else{
                    actorsFragment = ActorsFragment(movie.title,movie.id,false)
                }
                openFragment(actorsFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_similar_movies -> {
                var similarFragment:SimilarMoviesFragment
                if(addFavorite.visibility==View.GONE) {
                    similarFragment = SimilarMoviesFragment(movie.title, movie.id, true)
                }else{
                    similarFragment  = SimilarMoviesFragment(movie.title,movie.id, false)
                }
                openFragment(similarFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        bottomNavigation = findViewById(R.id.navigation)
        title = findViewById(R.id.movie_title)
        overview = findViewById(R.id.movie_overview)
        releaseDate = findViewById(R.id.movie_release_date)
        poster = findViewById(R.id.movie_poster)
        website = findViewById(R.id.movie_website)
        shareButton = findViewById(R.id.shareButton)
        backdrop = findViewById(R.id.movie_backdrop)
        addFavorite = findViewById(R.id.favorites)
        deleteFavorite = findViewById(R.id.delete)
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        website.setOnClickListener{
            showWebsite()
        }
        title.setOnClickListener{
            youtubeSearch()
        }
        shareButton.setOnClickListener{
            shareOverview()
        }

        addFavorite.setOnClickListener{
            writeDB()
        }
        deleteFavorite.setOnClickListener{
            deleteDB()
        }
        val extras = intent.extras

        if (extras != null) {
            if (extras.containsKey("movie_title")) {
                movie = movieDetailViewModel.getMovieByTitle(extras.getString("movie_title", ""))
                populateDetails()
            }
            else if (extras.containsKey("movie_id") && !extras.containsKey("exists") ){
                movieDetailViewModel.getMovie(extras.getLong("movie_id"),onSuccess = ::onSuccess,
                    onError = ::onError )
            }
            else if (extras.containsKey("movie_id") && extras.containsKey("exists") ){
                movieDetailViewModel.getMovieFromDB(applicationContext,extras.getLong("movie_id"),onSuccess = ::onSuccess,
                    onError = ::onError )
                addFavorite.visibility= View.GONE
                deleteFavorite.visibility = View.VISIBLE
            }
        } else {
            finish()
        }
    }

    fun onSuccess(movie:Movie){
        this.movie =movie;
        populateDetails()
    }
    fun onError() {
        val toast = Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT)
        toast.show()
    }

    fun writeDB(){
        movieDetailViewModel.writeDB(applicationContext,this.movie,onSuccess = ::onSuccess1,
            onError = ::onError)
    }

    fun deleteDB(){
        movieDetailViewModel.deleteDB(applicationContext,this.movie,onSuccess = ::onSuccess1, onError = ::onError)
    }
    fun onSuccess1(message:String){
        val toast = Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT)
        toast.show()
        addFavorite.visibility= View.GONE
        deleteFavorite.visibility = View.VISIBLE

    }
    private fun populateDetails() {
        title.text=movie.title
        releaseDate.text=movie.releaseDate
        website.text=movie.homepage
        overview.text=movie.overview
        val context: Context = poster.getContext()
        Glide.with(context)
            .load(posterPath + movie.posterPath)
            .placeholder(R.drawable.picture1)
            .error(R.drawable.picture1)
            .fallback(R.drawable.picture1)
            .into(poster);
        var backdropContext: Context = backdrop.getContext()
        Glide.with(backdropContext)
            .load(backdropPath + movie.backdropPath)
            .centerCrop()
            .placeholder(R.drawable.backdrop)
            .error(R.drawable.backdrop)
            .fallback(R.drawable.backdrop)
            .into(backdrop);
    }

    private fun showWebsite(){
        val sendIntent = Intent().apply {
            action = Intent.ACTION_VIEW
            setData(Uri.parse(movie.homepage))
        }
        if (sendIntent.resolveActivity(packageManager) != null) {
            startActivity(sendIntent)
        }
    }

    private fun youtubeSearch(){
        val intent = Intent(Intent.ACTION_SEARCH).apply {
            setPackage("com.google.android.youtube")
            putExtra("query", movie.title + " trailer")
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    private fun shareOverview(){
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, movie.overview)
            type = "text/plain"
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }
    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.actorsSimilarMoviesFrameLayout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
