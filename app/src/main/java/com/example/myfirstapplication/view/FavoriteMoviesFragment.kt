package com.example.myfirstapplication.view

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myfirstapplication.MovieDetailActivity
import com.example.myfirstapplication.R
import com.example.myfirstapplication.data.Movie
import com.example.myfirstapplication.viewmodel.MovieListViewModel
import androidx.lifecycle.Observer
import android.util.Pair as UtilPair

class FavoriteMoviesFragment : Fragment() {
    private lateinit var favoriteMovies: RecyclerView
    private lateinit var favoriteMoviesAdapter: MovieListAdapter
    private lateinit var movieListViewModel :  MovieListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view =  inflater.inflate(R.layout.favorites_fragment, container, false)
        favoriteMovies = view.findViewById(R.id.favoriteMovies)
        favoriteMovies.layoutManager = GridLayoutManager(activity, 2)
        favoriteMoviesAdapter = MovieListAdapter(arrayListOf()) { movie,view1,view2 -> showMovieDetails(movie,view1,view2) }
        favoriteMovies.adapter=favoriteMoviesAdapter
        context?.let { movieListViewModel= MovieListViewModel(it) }
        val moviesObserver = Observer<List<Movie>> { movies -> favoriteMoviesAdapter.updateMovies(movies)
        }
        movieListViewModel.favoriteMovies.observe(viewLifecycleOwner, moviesObserver)
        return view;
    }

    fun onSuccess(movies:List<Movie>){
        favoriteMoviesAdapter.updateMovies(movies)
    }
    fun onError() {
        val toast = Toast.makeText(context, "Error", Toast.LENGTH_SHORT)
        toast.show()
    }
    companion object {
        fun newInstance(): FavoriteMoviesFragment = FavoriteMoviesFragment()
    }
    private fun showMovieDetails(movie: Movie, view1: View,view2:View) {
        val intent = Intent(activity, MovieDetailActivity::class.java).apply {
            putExtra("movie_id", movie.id)
            putExtra("exists", true)
        }
        val options = ActivityOptions
            .makeSceneTransitionAnimation(activity,  UtilPair.create(view1, "poster"),
                UtilPair.create(view2, "title"))
        startActivity(intent, options.toBundle())
    }

}