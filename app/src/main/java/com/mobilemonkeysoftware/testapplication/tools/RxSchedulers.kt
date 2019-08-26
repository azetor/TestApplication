package com.mobilemonkeysoftware.testapplication.tools

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

object RxSchedulers {

    private val cachedExecutorService: ExecutorService by lazy {
        Executors.newCachedThreadPool()
    }
    private val cachedScheduler: Scheduler by lazy {
        Schedulers.from(cachedExecutorService)
    }

    fun cached(): Scheduler = cachedScheduler

    fun mainThread(): Scheduler = AndroidSchedulers.mainThread()

    fun io(): Scheduler = Schedulers.io()
}