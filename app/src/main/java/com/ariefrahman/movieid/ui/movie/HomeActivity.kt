package com.ariefrahman.movieid.ui.movie

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.ariefrahman.core.data.Resource
import com.ariefrahman.movieid.R
import com.ariefrahman.movieid.databinding.ActivityHomeBinding
import org.koin.android.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var mApiAdapter: MovieAdapter
    private val viewModel: MovieViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        mApiAdapter = MovieAdapter()

        viewModel.getMovieApi().observe(this, { _movies ->
            try{
                when(_movies){
                    is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        mApiAdapter.setMovieList(_movies.data)

                        with(binding.rvMovie){
                            layoutManager = LinearLayoutManager(context)
                            adapter = mApiAdapter
                        }
                    }
                    else -> return@observe
                }
            } catch (e: Exception){
                e.printStackTrace()
            }

        })


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_favorite, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.favorite_menu -> {
                val uri = Uri.parse("movieid://favorite")
                startActivity(Intent(Intent.ACTION_VIEW, uri))
            }
        }
        return super.onOptionsItemSelected(item)
    }

}