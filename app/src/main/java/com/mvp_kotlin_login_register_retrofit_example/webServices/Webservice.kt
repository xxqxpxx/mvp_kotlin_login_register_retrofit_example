package com.mvp_kotlin_login_register_retrofit_example.webServices

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class Webservice {

    private val api: Api

    init {

        val okHttpClient = OkHttpClient.Builder().build()
        val gson = GsonBuilder().setLenient().create()
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
        httpClient.connectTimeout(60, TimeUnit.SECONDS)
        httpClient.readTimeout(60, TimeUnit.SECONDS)
        httpClient.addInterceptor(logging)

        val retrofit = Retrofit.Builder().client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("")
            .build()

        api = retrofit.create(Api::class.java)
    }

    companion object {
        private var instance: Webservice? = null

        fun getInstance(): Webservice {
            if (instance == null) {
                instance = Webservice()
            }
            return instance as Webservice
        }
    }


    fun getApi(): Api {
        return api
    }



}
