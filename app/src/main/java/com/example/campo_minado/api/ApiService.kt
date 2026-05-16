package com.example.campo_minado.api

import com.example.campo_minado.model.MatchResult
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("backend/api.php")
    fun getHistory(): Call<List<MatchResult>>

    @POST("backend/api.php")
    fun saveMatch(@Body result: MatchResult): Call<Void>
}
