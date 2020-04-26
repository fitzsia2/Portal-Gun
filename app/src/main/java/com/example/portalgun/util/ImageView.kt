package com.example.portalgun.util

import android.widget.ImageView
import com.example.portalgun.ui.main.ImageLoadedCallback
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

fun ImageView.load(path: String?, onLoaded: ImageLoadedCallback = { _, _, _ -> }) {
    Picasso.get().load(path).into(this, object : Callback {

        override fun onSuccess() = onLoaded(true, this@load, null)

        override fun onError(e: Exception?) = onLoaded(false, this@load, e)
    })
}
