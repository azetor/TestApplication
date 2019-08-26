package com.mobilemonkeysoftware.testapplication

import android.app.Application
import android.os.Looper
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.functions.Functions
import io.reactivex.plugins.RxJavaPlugins

class MainApplication : Application() {

    init {
        RxJavaPlugins.setErrorHandler(Functions.emptyConsumer())
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { AndroidSchedulers.from(Looper.getMainLooper(), true) }
    }
}