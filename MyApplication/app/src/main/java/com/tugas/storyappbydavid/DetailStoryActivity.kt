package com.tugas.storyappbydavid

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.tugas.storyappbydavid.apiresponse.Story
import com.tugas.storyappbydavid.databinding.ActivityDetailStoryBinding

class DetailStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailStoryBinding

    private val viewModel: DetailStoryViewModel by viewModels()

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.back_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem):Boolean = when(item.itemId) {
        R.id.back -> {
            finish()
            true
        }
        else -> {
            true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val idStory = intent.getStringExtra("id").toString()
        viewModel.getStory(idStory)

        viewModel.isLoading.observe(this, {
            showLoading(it)
        })

        viewModel.detailStory.observe(this, { getAStory ->
            if (getAStory != null) {
                getStory(getAStory)
            }
        })

        playAnimation()
    }

    private fun playAnimation() {
        val nameTitle = ObjectAnimator.ofFloat(binding.textView2, View.ALPHA, 1f).setDuration(500)
        val deskrisiTitle = ObjectAnimator.ofFloat(binding.textView3, View.ALPHA, 1f).setDuration(500)
        val nameText = ObjectAnimator.ofFloat(binding.tvDetailName, View.ALPHA, 1f).setDuration(500)
        val deskripsiText = ObjectAnimator.ofFloat(binding.tvDetailDescription, View.ALPHA, 1f).setDuration(500)


        AnimatorSet().apply {
            playSequentially(nameTitle, nameText, deskrisiTitle, deskripsiText)
            start()
        }
    }

    private fun getStory(Story: Story) {
        Glide.with(this@DetailStoryActivity)
            .load(Story.photoUrl)
            .into(binding.ivDetailPhoto)
        binding.tvDetailName.text = Story.name
        binding.tvDetailDescription.text = Story.description
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}