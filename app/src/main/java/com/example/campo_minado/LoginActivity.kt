package com.example.campo_minado

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.campo_minado.api.RetrofitClient
import com.example.campo_minado.databinding.ActivityLoginBinding
import com.example.campo_minado.model.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val email = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                executarLogin(email, password)
            } else {
                Toast.makeText(this, "Por favor, preencha o usuário e a senha", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun executarLogin(email: String, password: String) {
        RetrofitClient.instance.login(email, password).enqueue(object : Callback<List<LoginResponse>> {

            override fun onResponse(
                call: Call<List<LoginResponse>>,
                response: Response<List<LoginResponse>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val listaUsuarios = response.body()!!

                    if (listaUsuarios.isNotEmpty()) {
                        // Pega o primeiro usuário da lista vinda do PHP
                        val usuarioLogado = listaUsuarios[0]

                        // Abre a MainActivity passando o nome real do banco
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        intent.putExtra("PLAYER_NAME", usuarioLogado.usuarioNome)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity, "E-mail ou senha incorretos!", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(this@LoginActivity, "Erro na resposta do servidor", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<LoginResponse>>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Falha de conexão: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}