package com.example.campo_minado

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.campo_minado.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val playerName = intent.getStringExtra("PLAYER_NAME") ?: "Anônimo"
        binding.tvWelcome.text = "Bem-vindo, $playerName!"

        binding.btnStartGame.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("PLAYER_NAME", playerName)
            startActivity(intent)
        }

        binding.btnViewHistory.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            intent.putExtra("PLAYER_NAME", playerName)
            startActivity(intent)
        }

        binding.btnLogout.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}