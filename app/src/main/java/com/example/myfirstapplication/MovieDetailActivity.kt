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
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var title : TextView
    private lateinit var overview : TextView
    private lateinit var releaseDate : TextView
    private lateinit var genre : TextView
    private  var movie=Movie(0,"Test","Test","Test","Test","Test","Test")
    private lateinit var website : TextView
    private lateinit var poster : ImageView
    private lateinit var backdrop : ImageView
    private lateinit var shareButton: FloatingActionButton
    private var movieDetailViewModel =  MovieDetailViewModel()
    private val posterPath = "https://image.tmdb.org/t/p/w780"
    private val backdropPath = "https://image.tmdb.org/t/p/w500"
    private lateinit var addFavorite : Button

    //Listener za click
    private val  mOnItemSelectedListener =  NavigationBarView.OnItemSelectedListener{ item ->
        when (item.itemId) {
            R.id.navigation_actors -> {
                val actorsFragment = ActorsFragment(movie.title,movie.id)
                openFragment(actorsFragment)
                return@OnItemSelectedListener true
            }
            R.id.navigation_similar_movies -> {
                val similarFragment = SimilarMoviesFragment(movie.title,movie.id)
                openFragment(similarFragment)
                return@OnItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        //navigation
        bottomNavigation = findViewById(R.id.navigation)

        title = findViewById(R.id.movie_title)
        overview = findViewById(R.id.movie_overview)
        releaseDate = findViewById(R.id.movie_release_date)
        genre = findViewById(R.id.movie_genre)
        poster = findViewById(R.id.movie_poster)
        website = findViewById(R.id.movie_website)
        shareButton = findViewById(R.id.shareButton)
        backdrop = findViewById(R.id.movie_backdrop)
        addFavorite = findViewById(R.id.favorites)

        website.setOnClickListener{ //Potrebno je napraviti da se pokrene web preglednik kada se klikne na link web stranice filma
            // i da se učita navedena stranica.
            showWebsite()
        }
        shareButton.setOnClickListener{
            shareOverview()
        }
        title.setOnClickListener{
            searchInYoutube()
        }
        addFavorite.setOnClickListener{
            writeDB()
        }
        val extras = intent.extras

        if (extras != null) {
            if (extras.containsKey("movie_title")) {
                movie = movieDetailViewModel.getMovieByTitle(extras.getString("movie_title", ""))
                populateDetails()
            }
            else if (extras.containsKey("movie_id")){
                movieDetailViewModel.getMovieDetails(extras.getLong("movie_id"),onSuccess = ::onSuccess,
                    onError = ::onError)
            }
        } else {
            finish()
        }
        bottomNavigation.setOnItemSelectedListener(mOnItemSelectedListener)
        //Defaultni fragment
        bottomNavigation.selectedItemId= R.id.navigation_actors
    }
    private fun shareOverview(){
        val intent = Intent().apply {
            action =Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT,movie.overview)
            type="text/plain"
        }
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            // Definisati naredbe ako ne postoji aplikacija za navedenu akciju
        }
    }
    fun writeDB(){ //vjezba9
        movieDetailViewModel.writeDB(applicationContext,this.movie,onSuccess = ::onSuccess1, onError = ::onError)
    }
    fun onSuccess1(message:String){
        val toast = Toast.makeText(applicationContext, "Spaseno", Toast.LENGTH_SHORT)
        toast.show()
        addFavorite.visibility= View.GONE
    }
    fun onError() {
        val toast = Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT)
        toast.show()
    }
    fun onSuccess(movie:Movie){
        this.movie =movie;
        populateDetails()
    }
    private fun searchInYoutube(){
        //results?search_query=divergent+trailer
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("https://www.youtube.com/results?search_query="+title.text.toString()+"+trailer")
        intent.setPackage("com.google.android.youtube")
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            // Definisati naredbe ako ne postoji aplikacija za navedenu akciju
        }
    }
    private fun showWebsite(){
        val webIntent: Intent = Uri.parse(movie.homepage).let { webpage ->
            Intent(Intent.ACTION_VIEW, webpage)
        }
        try {
              startActivity(webIntent)
          } catch (e: ActivityNotFoundException) {
              // Definisati naredbe ako ne postoji aplikacija za navedenu akciju
          }
    /*    if (webIntent.resolveActivity(packageManager) != null) {
            startActivity(webIntent)
        }*/
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.actorsSimilarMoviesFrameLayout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
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
    fun OpenMovie(movie:Movie){
        this.movie =movie;
        populateDetails()
    }

}
