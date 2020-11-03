package com.hbl.recordactivity.repository

import com.idealabs.photoeditor.repository.network.FlowCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

object RemoteRepository {
    const val baseUrl = "https://app.bupt.edu.cn/"

    private val retrofit: Retrofit by lazy {
        val httpLogInterceptor = HttpLoggingInterceptor { message ->
            if (message.length < 100000) {
                HttpLoggingInterceptor.Logger.DEFAULT.log(message)
            } else {
                HttpLoggingInterceptor.Logger.DEFAULT.log("too much message to log")
            }
        }
        httpLogInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(30L, TimeUnit.SECONDS)
            .readTimeout(30L, TimeUnit.SECONDS)
            .writeTimeout(30L, TimeUnit.SECONDS)
            .addInterceptor(httpLogInterceptor)
            .build()

        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(FlowCallAdapterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    }

    fun <T> createService(clazz: Class<T>): T {
        return retrofit.create(clazz)
    }
}


