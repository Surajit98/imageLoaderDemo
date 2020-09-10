package com.sd.app.utils

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import org.koin.core.KoinComponent
import org.koin.core.get

object CustomBindingAdapter : KoinComponent {

    var picasso: Picasso = get()

    @BindingAdapter(value = ["url", "progressBar"])
    fun loadImage(view: ImageView?, url: String?, progressBar: ProgressBar) {
        url?.apply {
            progressBar.visibility = View.VISIBLE
            picasso.load(this).into(view, object : Callback {
                override fun onSuccess() {
                    progressBar.visibility = View.GONE
                }

                override fun onError(e: Exception) {
                    progressBar.visibility = View.GONE
                }
            })
        }

    }
}