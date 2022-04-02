package com.example.myfirstapplication

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.graphics.drawable.toBitmap
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.PositionAssertions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasPackage
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class IntentInstrumentedTest {

    fun withImage(@DrawableRes id: Int) = object : TypeSafeMatcher<View>(){
        override fun describeTo(description: Description) {
            description.appendText("Drawable does not contain image with id: $id")
        }
        override fun matchesSafely(item: View): Boolean {
            val context:Context = item.context
            val bitmap: Bitmap? = context.getDrawable(id)?.toBitmap()
            return item is ImageView && item.drawable.toBitmap().sameAs(bitmap)
        }
    }
    @Test
    fun testDetailActivityInstantiation(){
        val pokreniDetalje=Intent(ApplicationProvider.getApplicationContext(),MovieDetailActivity::class.java)
        pokreniDetalje.putExtra("movie_title","Pulp Fiction")
        launchActivity<MovieDetailActivity>(pokreniDetalje)
        onView(withId(R.id.movie_title)).check(matches(withText("Pulp Fiction")))
        onView(withId(R.id.movie_genre)).check(matches(withText("crime")))
        onView(withId(R.id.movie_overview)).check(matches(withSubstring("pair of diner bandits")))
        onView(withId(R.id.movie_poster)).check(matches(withImage(R.drawable.crime)))
    }
    @Test
    fun testLinksIntent(){
        Intents.init()
        val pokreniDetalje=Intent(ApplicationProvider.getApplicationContext(),MovieDetailActivity::class.java)
        pokreniDetalje.putExtra("movie_title","Pulp Fiction")
        launchActivity<MovieDetailActivity>(pokreniDetalje)
        onView(withId(R.id.movie_website)).perform(click())
        intended(hasAction(Intent.ACTION_VIEW))
        Intents.release()
    }
    //testiran raspored elemenata na MovieDetailsActivity
    @Test
    fun testZarasporedElemenata(){
        val pokreniDetalje=Intent(ApplicationProvider.getApplicationContext(),MovieDetailActivity::class.java)
        pokreniDetalje.putExtra("movie_title","Pulp Fiction")
        launchActivity<MovieDetailActivity>(pokreniDetalje)
        onView(withId(R.id.movie_poster)).check(isCompletelyLeftOf(withId(R.id.movie_title)))
        onView(withId(R.id.movie_release_date)).check(isCompletelyBelow(withId(R.id.movie_title)))
        onView(withId(R.id.movie_release_date)).check(isCompletelyRightOf(withId(R.id.movie_poster)))
        onView(withId(R.id.movie_genre)).check(isCompletelyBelow(withId(R.id.movie_release_date)))
        onView(withId(R.id.movie_genre)).check(isLeftAlignedWith(withId(R.id.movie_release_date)))
        onView(withId(R.id.movie_website)).check(isCompletelyBelow(withId(R.id.movie_poster)))
        onView(withId(R.id.movie_overview)).check(isCompletelyBelow(withId(R.id.movie_website)))
    }
    //testira da li klik na naslov filma otvara YouTube aplikaciju
    @Test
    fun testYoutube(){
        val pokreniDetalje=Intent(ApplicationProvider.getApplicationContext(),MovieDetailActivity::class.java)
        pokreniDetalje.putExtra("movie_title","Pulp Fiction")
        launchActivity<MovieDetailActivity>(pokreniDetalje)
        onView(withId(R.id.movie_title)).perform(click())
        intended(hasPackage("com.google.android.youtube"))
    }

}