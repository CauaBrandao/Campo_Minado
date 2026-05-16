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

        binding.btnStartGame.setOnClickListener {
            val playerName = binding.etPlayerName.text.toString().trim()
            if (playerName.isNotEmpty()) {
                val intent = Intent(this, GameActivity::class.java)
                intent.putExtra("PLAYER_NAME", playerName)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Por favor, digite seu nome", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnViewHistory.setOnClickListener {
            val playerName = binding.etPlayerName.text.toString().trim()
            val intent = Intent(this, HistoryActivity::class.java)
            if (playerName.isNotEmpty()) {
                intent.putExtra("PLAYER_NAME", playerName)
            } else {
                intent.putExtra("PLAYER_NAME", "Anônimo")
            }
            startActivity(intent)
        }
    }
}