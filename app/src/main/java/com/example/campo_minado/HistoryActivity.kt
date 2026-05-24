package com.example.campo_minado

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.campo_minado.api.RetrofitClient
import com.example.campo_minado.databinding.ActivityHistoryBinding
import com.example.campo_minado.model.MatchResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
    private var playerName: String = "Anônimo"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        playerName = intent.getStringExtra("PLAYER_NAME") ?: "Anônimo"
        binding.tvPlayerName.text = "Jogador Atual: $playerName"

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        fetchHistory()
    }

    private fun fetchHistory() {
        RetrofitClient.instance.getMatches().enqueue(object : Callback<List<MatchResult>> {
            override fun onResponse(call: Call<List<MatchResult>>, response: Response<List<MatchResult>>) {
                if (response.isSuccessful) {
                    val matches = response.body() ?: emptyList()
                    // Passa a lista vinda do PHP diretamente para o seu Adapter
                    val adapter = HistoryAdapter(matches)
                    binding.recyclerView.adapter = adapter
                } else {
                    Toast.makeText(this@HistoryActivity, "Erro ao carregar histórico", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<MatchResult>>, t: Throwable) {
                Toast.makeText(this@HistoryActivity, "Erro de conexão: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}