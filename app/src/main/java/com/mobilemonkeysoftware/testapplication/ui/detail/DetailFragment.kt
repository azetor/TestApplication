package com.mobilemonkeysoftware.testapplication.ui.detail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.clicks
import com.mobilemonkeysoftware.testapplication.R
import com.mobilemonkeysoftware.testapplication.core.Photo
import com.mobilemonkeysoftware.testapplication.tools.ImageLoader
import com.mobilemonkeysoftware.testapplication.ui.EXTRA_PHOTO
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_detail.*
import android.content.Intent
import android.net.Uri

class DetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater
        .inflate(R.layout.fragment_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments
            ?.getSerializable(EXTRA_PHOTO)
            ?.let {
                with(it as Photo) {
                    ImageLoader
                        .load(context, this.downloadUrl, photo)
                    text.text = this.toString()
                    share
                        .clicks()
                        .subscribeBy {
                            startActivity(Intent.createChooser(
                                Intent(android.content.Intent.ACTION_SEND).apply {
                                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                    type = "image/*"
                                    putExtra(Intent.EXTRA_STREAM, Uri.parse(downloadUrl))
                                },
                                "Share Photo via:"))
                        }
                }
            }
    }
}