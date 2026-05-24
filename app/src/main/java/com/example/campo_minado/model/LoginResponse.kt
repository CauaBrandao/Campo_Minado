package com.example.campo_minado.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("usuarioId") val usuarioId: Int,
    @SerializedName("usuarioNome") val usuarioNome: String,
    @SerializedName("usuarioEmail") val usuarioEmail: String,
    @SerializedName("usuarioCpf") val usuarioCpf: String
)