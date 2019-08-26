package com.mobilemonkeysoftware.testapplication.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.mobilemonkeysoftware.testapplication.R
import com.mobilemonkeysoftware.testapplication.core.PicsumPhotosApi
import com.mobilemonkeysoftware.testapplication.tools.RxSchedulers
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        PicsumPhotosApi
            .flattenedList()
            .observeOn(RxSchedulers.mainThread())
            .subscribeBy { Timber.d("PHOTO: $it") }
    }
}

