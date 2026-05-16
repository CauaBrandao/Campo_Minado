package com.example.campo_minado.model

import com.google.gson.annotations.SerializedName

data class MatchResult(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("player_name")
    val playerName: String,
    @SerializedName("score")
    val score: Int,
    @SerializedName("date")
    val date: String? = null
)
