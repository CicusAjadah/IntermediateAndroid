package com.tugas.storyappbydavid

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.tugas.storyappbydavid.adapter.ListStoryAdapter
import com.tugas.storyappbydavid.adapter.LoadingStateAdapter
import com.tugas.storyappbydavid.context.SuperApplication
import com.tugas.storyappbydavid.context.ViewModelFactory
import com.tugas.storyappbydavid.databinding.ActivityListStoryBinding

class ListStoryActivity : AppCompatActivity() {

    private val sharedPrefFile = "kotlinsharedpreference"
    val contextAplikasi = SuperApplication.appContext

    private lateinit var binding: ActivityListStoryBinding

    private val viewModel: ListStoryViewModel by viewModels {
        ViewModelFactory(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.navbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem):Boolean = when(item.itemId) {

        R.id.action_logout -> {
            val sharedPreferences: SharedPreferences? = contextAplikasi?.getSharedPreferences(sharedPrefFile,Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor? =  sharedPreferences?.edit()
            editor?.clear()
            editor?.apply()
            finish()
            val moveIntent = Intent(this@ListStoryActivity, MainActivity::class.java)
            startActivity(moveIntent)
            true
        }
        R.id.action_add_story -> {
            val moveIntent = Intent(this@ListStoryActivity, UploadStoryActivity::class.java)
            startActivity(moveIntent)
            true
        }
        R.id.action_see_maps -> {
            val moveIntent = Intent(this@ListStoryActivity, StoryMapsActivity::class.java)
            startActivity(moveIntent)
            true
        }
        else -> {
            true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityListStoryBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.listStory.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.listStory.addItemDecoration(itemDecoration)


        getListStory()
    }

    override fun onResume() {
        super.onResume()
        getListStory()
    }

    private fun getListStory() {
        val adapter = ListStoryAdapter()
        viewModel.stories.observe(this, { pagingData ->
            adapter.submitData(lifecycle, pagingData)
        })
        binding.listStory.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )
        adapter.refresh()
    }
}