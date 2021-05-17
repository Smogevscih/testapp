package com.smic.testapp

import android.app.Application
import com.smic.testapp.network.RequestGit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class TestApp : Application() {
    private val BASE_URL = "https://api.github.com/"


    override fun onCreate() {
        super.onCreate()
        initRetrofit()
    }

    companion object {
        lateinit var requestApi: RequestGit
    }


    private fun initRetrofit() {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //add RxAdapter
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .build()

        requestApi = retrofit.create(RequestGit::class.java)
    }
}