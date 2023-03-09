package com.gandalgom.sample.databinding

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider

import com.gandalgom.sample.databinding.data.*
import com.gandalgom.sample.databinding.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    // Obtain ViewModel from ViewModelProviders
    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[SimpleViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        // TODO: Explicitly setting initial values is a bad pattern. We'll fix that.
        binding.viewModel = viewModel

        updateLikes()
    }

    /**
     * This method is triggered by the `android:onclick` attribute in the layout. It puts business
     * logic in the activity, which is not ideal. We should do something about that.
     */
    fun onLike(view: View) {
        viewModel.onLike()
        updateLikes()
    }

    /**
     * This method has many problems:
     * - It's calling findViewById multiple times
     * - It has untestable logic
     * - It's updating two views when called even if they're not changing
     */
    private fun updateLikes() {
        findViewById<TextView>(R.id.likes).text = viewModel.likes.toString()
        findViewById<ProgressBar>(R.id.progressBar).progress =
            (viewModel.likes * 100 / 5).coerceAtMost(100)
        val image = findViewById<ImageView>(R.id.imageView)

        val color = getAssociatedColor(viewModel.popularity, this)

        ImageViewCompat.setImageTintList(image, ColorStateList.valueOf(color))

        image.setImageDrawable(getDrawablePopularity(viewModel.popularity, this))
    }

    private fun getAssociatedColor(popularity: Popularity, context: Context): Int {
        return when (popularity) {
            Popularity.NORMAL ->
                context.theme.obtainStyledAttributes(intArrayOf(android.R.attr.colorForeground))
                    .getColor(0, 0x000000)
            Popularity.POPULAR ->
                ContextCompat.getColor(context, R.color.popular)
            Popularity.STAR ->
                ContextCompat.getColor(context, R.color.star)
        }
    }

    private fun getDrawablePopularity(popularity: Popularity, context: Context): Drawable? {
        return when (popularity) {
            Popularity.NORMAL ->
                ContextCompat.getDrawable(context, R.drawable.ic_person_black_96dp)
            Popularity.POPULAR ->
                ContextCompat.getDrawable(context, R.drawable.ic_whatshot_black_96dp)
            Popularity.STAR ->
                ContextCompat.getDrawable(context, R.drawable.ic_whatshot_black_96dp)
        }
    }
}