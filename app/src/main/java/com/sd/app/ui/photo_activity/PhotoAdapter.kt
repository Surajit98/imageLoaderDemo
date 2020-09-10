package com.sd.app.ui.photo_activity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sd.app.R
import com.sd.app.data.model.Photo
import com.sd.app.databinding.ListItemPhotosBinding

class PhotoAdapter(private val photos: ArrayList<Photo>) :
    RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ListItemPhotosBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.list_item_photos,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount() = photos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.url = photos[position].url_m
    }

    fun add(list: java.util.ArrayList<Photo>) {
        photos.addAll(list)
        notifyDataSetChanged()
    }

    fun clear(){
        photos.clear()
        notifyDataSetChanged()
    }

    class ViewHolder(
        val binding: ListItemPhotosBinding
    ) : RecyclerView.ViewHolder(binding.root) {
    }
}