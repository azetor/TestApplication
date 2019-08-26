package com.mobilemonkeysoftware.testapplication.tools

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

object ImageLoader {

    fun load(
        context: Context?,
        url: String,
        target: ImageView
    ) {
        context
            ?.let {
                Glide
                    .with(it)
                    .load(url)
                    .into(target)
            }
    }
}