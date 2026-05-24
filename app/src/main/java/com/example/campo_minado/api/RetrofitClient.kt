package com.example.campo_minado.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    // SUBSTITUA "http://192.168.18.7/" pelo IP que você encontrou no comando ipconfig do seu PC
    private const val BASE_URL = "http://192.168.18.9/"
    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(ApiService::class.java)
    }
}