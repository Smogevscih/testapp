package com.smic.testapp

import android.app.Application
import com.smic.testapp.network.RequestGit
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class TestApp : Application() {
    private val BASE_URL = HttpUrl.Builder().apply {
        scheme("https")
        host("api.github.com")
    }.build()


    override fun onCreate() {
        super.onCreate()
        initRetrofit(BASE_URL)
    }

    companion object {
        lateinit var requestApi: RequestGit
    }


    private fun initRetrofit(base_url: HttpUrl) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //add RxAdapter
            .client(okHttpClient)
            .baseUrl(base_url)
            .build()

        requestApi = retrofit.create(RequestGit::class.java)
    }


    fun initRetrofitForTest(base_url: HttpUrl) {
        initRetrofit(base_url)
    }
}