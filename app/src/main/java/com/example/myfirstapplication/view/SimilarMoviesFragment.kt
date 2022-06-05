package com.example.myfirstapplication.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myfirstapplication.R
import com.example.myfirstapplication.data.Movie
import com.example.myfirstapplication.viewmodel.MovieDetailViewModel
//valja
class SimilarMoviesFragment(name:String,id:Long?,favourite:Boolean): Fragment() {
    private var favourite = favourite
    private var movieName:String = name
    private var movieId:Long? = id
    private lateinit var movieRV:RecyclerView
    private var movieList= listOf<Movie>()
    private lateinit var actorsRVSimpleSimilarAdapter:StringAdapter
    private var movieDetailViewModel =  MovieDetailViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(R.layout.similar_movies_fragment, container, false)
        movieRV = view.findViewById(R.id.similarRecyclerView)
        movieRV.layoutManager = LinearLayoutManager(activity)
        actorsRVSimpleSimilarAdapter = StringAdapter(movieList)
        movieRV.adapter = actorsRVSimpleSimilarAdapter
        if(favourite){
            movieId?.let { movieDetailViewModel.getSimilarMoviesByIdDB(requireContext(),it,onSuccess = ::onSuccess, onError = ::onError) }
        }else{
            movieId?.let { movieDetailViewModel.getSimilarMoviesById(it,onSuccess = ::onSuccess,
                onError = ::onError) }
        }
        movieId?.let { movieDetailViewModel.getSimilarMoviesById(it,onSuccess = ::onSuccess, onError = ::onError) }
        return view
    }
    fun onSuccess(movies:List<Movie>){
        movieList=movies
        actorsRVSimpleSimilarAdapter.lista = movies
        actorsRVSimpleSimilarAdapter.notifyDataSetChanged()
    }
    fun onError() {
        val toast = Toast.makeText(context, "Search error", Toast.LENGTH_SHORT)
        toast.show()
    }
}