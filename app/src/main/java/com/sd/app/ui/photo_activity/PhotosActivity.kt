package com.sd.app.ui.photo_activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.sd.app.R
import com.sd.app.data.model.Photo
import com.sd.app.databinding.ActivityPhotosBinding
import com.sd.app.utils.hideKeyboard
import org.koin.androidx.viewmodel.ext.android.viewModel


class PhotosActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private val viewModel: PhotosViewModel by viewModel()
    private lateinit var binding: ActivityPhotosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_photos)
        binding.apply {
            lifecycleOwner = this@PhotosActivity
            viewModel = this@PhotosActivity.viewModel
        }
        setViews()
        addObservers()
    }

    private fun addObservers() {

        viewModel.fetchImages1().observe(this, Observer {
            return@Observer
        })
        viewModel.photosData.observe(this, Observer {
            setList(it)


        })
        viewModel.showMessage.observe(this, Observer {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
        })
    }

    private fun setList(list: ArrayList<Photo>?) {
        if (list == null) {
            binding.adapter?.apply {
                clear()
            }
        } else {
            if (binding.adapter == null) {
                binding.adapter = PhotoAdapter(list)
            } else {
                if (viewModel.fetchNextPage.value == true) {
                    binding.adapter?.apply { add(list) }
                } else {
                    binding.adapter?.apply {
                        clear()
                        add(list)
                    }
                }

            }
        }
    }

    private fun setViews() {
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.list.addOnScrollListener(object :
            LoadMoreListener(binding.list.layoutManager as GridLayoutManager) {
            override fun loadMoreItems() {
                viewModel.loadNext()
            }

            override val isLastPage: Boolean
                get() = viewModel.isLastPage()
            override val isLoading: Boolean
                get() = viewModel.isLoading.value!!
        })
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
        if (!query.isNullOrEmpty()) {
            viewModel.searchData(query)
            hideKeyboard()
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