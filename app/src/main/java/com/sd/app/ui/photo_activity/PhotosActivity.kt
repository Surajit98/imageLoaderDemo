package com.sd.app.ui.photo_activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import com.sd.app.R
import org.koin.androidx.viewmodel.ext.android.viewModel


class PhotosActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private val viewModel: PhotosViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photos)
        setViews()
        addObservers()
    }

    private fun addObservers() {
        viewModel.textChanged().observe(this, Observer { return@Observer })
        viewModel.nextPage().observe(this, Observer { return@Observer })
       /* viewModel.fetchImages().observe(this, {

        })*/
        viewModel.isLoading.observe(this, {

        })
    }

    private fun setViews() {
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val searchItem: MenuItem = menu.findItem(R.id.searchBar)
        val searchView: SearchView = searchItem.actionView as SearchView
        searchView.queryHint = "Search for images"
        searchView.setOnQueryTextListener(this)
        searchView.isIconified = false
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query.isNullOrEmpty()) {
            viewModel.searchText.value = ""
            viewModel.currentPage = 0
            viewModel.pages = 0
        } else {
            viewModel.currentPage = 1
            viewModel.searchText.value = query

        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {

        return false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }


    }
}