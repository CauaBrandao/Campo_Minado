package com.example.campo_minado.api

import com.example.campo_minado.model.LoginResponse
import com.example.campo_minado.model.MatchResult
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    // Rota de Login (GET)
    @GET("ApiCampoMinado/login.php")
    fun login(
        @Query("usuario") usuario: String,
        @Query("senha") senha: String
    ): Call<List<LoginResponse>>

    // Rota para Salvar a Partida (POST)
    @POST("ApiCampoMinado/salvar_partida.php")
    fun saveMatch(
        @Body result: MatchResult
    ): Call<Void>

    @GET("ApiCampoMinado/listar_partidas.php")
    fun getMatches(): Call<List<MatchResult>>
}