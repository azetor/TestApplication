package com.mobilemonkeysoftware.testapplication.core

import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import com.mobilemonkeysoftware.testapplication.tools.RxSchedulers
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import timber.log.Timber
import java.io.Serializable

interface Api {

    @GET("v2/list")
    fun list(): Observable<List<Photo>>

    @GET("200?image={id}")
    fun photo(@Path("id") id: Int): Observable<Photo>
}

data class Photo(
    val id: Int,
    val author: String,
    val width: Int,
    val height: Int,
    val url: String,
    @SerializedName("download_url") val downloadUrl: String
) : Serializable

object PicsumPhotosApi : Api {

    private val api: Api by lazy { provideApi() }

    private fun provideApi() = Retrofit
        .Builder()
        .client(provideClient())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(provideGson()))
        .baseUrl("https://picsum.photos/")
        .build()
        .create(Api::class.java)

    private fun provideClient() = OkHttpClient
        .Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    private fun provideGson() = GsonBuilder()
        .setPrettyPrinting()
        .create()

    override fun list(): Observable<List<Photo>> = api
        .list()
        .subscribeOn(RxSchedulers.cached())

    fun flattenedList() : Observable<Photo> = list()
        .flatMap { Observable.fromIterable(it) }
        .doOnNext { Timber.d("PHOTO: $it") }

    override fun photo(id: Int): Observable<Photo> = api
        .photo(id)
        .subscribeOn(RxSchedulers.cached())
}