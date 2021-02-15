package com.kiran.student.api

import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {
    private const val BASE_URL= "http://10.0.2.2:3000/api/v1/"
    var token: String?=null
    private val okHttp=OkHttpClient.Builder()
    private val retrofitBuilder= Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttp.build())

    //create retrofit func
    private val retrofit= retrofitBuilder.build()

    //generic func
    fun <T> buildService(serviceType:Class<T>):T{
        return retrofit.create(serviceType)
    }
}