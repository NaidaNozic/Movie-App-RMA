package com.example.myfirstapplication.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myfirstapplication.R
import com.example.myfirstapplication.viewmodel.MovieDetailViewModel
//valja
class SimilarMoviesFragment(name:String,id:Long?): Fragment() {
    private var movieName:String = name
    private var movieId:Long? = id
    private lateinit var movieRV:RecyclerView
    private var movieList= listOf<String>()
    private lateinit var actorsRVSimpleAdapter:StringAdapter
    private var movieDetailViewModel =  MovieDetailViewModel(null,this@SimilarMoviesFragment::OpenSimilarMovie,null)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(R.layout.similar_movies_fragment, container, false)
        movieRV = view.findViewById(R.id.similarRecyclerView)
        movieList = movieName?.let { movieDetailViewModel.getSimilarByTitle(it) }!!
        movieRV.layoutManager = LinearLayoutManager(activity)
        actorsRVSimpleAdapter = StringAdapter(movieList)
        movieRV.adapter = actorsRVSimpleAdapter
        movieId?.let { movieDetailViewModel.getSimilarMoviesById(it) }
        return view
    }
    fun OpenSimilarMovie(similar:MutableList<String>){
        movieList=similar
        actorsRVSimpleAdapter.lista = similar;
        actorsRVSimpleAdapter.notifyDataSetChanged();
    }
}