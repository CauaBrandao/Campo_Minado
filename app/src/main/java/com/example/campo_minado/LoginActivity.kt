package com.example.campo_minado

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.campo_minado.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("PLAYER_NAME", username)
                startActivity(intent)
                finish() // Fechar a tela de login para não voltar a ela pressionando 'Voltar' no menu
            } else {
                Toast.makeText(this, "Por favor, preencha o usuário e a senha", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
