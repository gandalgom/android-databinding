package com.gandalgom.sample.databinding

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
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

        binding.lifecycleOwner = this

        binding.viewModel = viewModel
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