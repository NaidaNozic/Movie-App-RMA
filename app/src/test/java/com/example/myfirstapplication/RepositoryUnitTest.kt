package com.example.myfirstapplication


import com.example.myfirstapplication.data.Movie
import org.hamcrest.CoreMatchers.`is` as Is
import com.example.myfirstapplication.data.MovieRepository
import org.hamcrest.Matchers.*
import org.junit.Test
import junit.framework.Assert.assertEquals
import org.junit.Assert.assertThat
class RepositoryUnitTest {
    @Test
    fun testGetFavoriteMovies(){
        val movies = MovieRepository.getFavoriteMovies()
        assertEquals(movies.size,6)
        assertThat(movies, hasItem<Movie>(hasProperty("title", Is("Pulp Fiction"))))
        assertThat(movies, not(hasItem<Movie>(hasProperty("title", Is("Black Widow")))))
    }

}