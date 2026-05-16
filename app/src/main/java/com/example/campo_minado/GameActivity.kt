package com.example.campo_minado

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.campo_minado.api.RetrofitClient
import com.example.campo_minado.databinding.ActivityGameBinding
import com.example.campo_minado.model.MatchResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding
    private var playerName: String = "Anônimo"

    private val rows = 8
    private val cols = 8
    private val totalMines = 10

    private lateinit var buttons: Array<Array<Button>>
    private lateinit var mineField: Array<IntArray> // -1 for mine, 0-8 for adjacent mines
    private lateinit var revealed: Array<BooleanArray>

    private var isGameOver = false
    private var score = 0
    private var safeCellsRevealed = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        playerName = intent.getStringExtra("PLAYER_NAME") ?: "Anônimo"
        binding.tvPlayerName.text = "Jogador: $playerName"

        initGame()

        binding.btnRestart.setOnClickListener {
            initGame()
        }
    }

    private fun initGame() {
        isGameOver = false
        score = 0
        safeCellsRevealed = 0
        updateScore()
        binding.btnRestart.visibility = View.GONE

        binding.gridLayout.removeAllViews()
        binding.gridLayout.rowCount = rows
        binding.gridLayout.columnCount = cols

        buttons = Array(rows) { Array(cols) { Button(this) } }
        mineField = Array(rows) { IntArray(cols) }
        revealed = Array(rows) { BooleanArray(cols) }

        placeMines()
        calculateNumbers()

        for (r in 0 until rows) {
            for (c in 0 until cols) {
                val button = Button(this)
                val params = GridLayout.LayoutParams()
                params.width = 120
                params.height = 120
                params.rowSpec = GridLayout.spec(r)
                params.columnSpec = GridLayout.spec(c)
                button.layoutParams = params

                button.setOnClickListener { onCellClicked(r, c) }

                buttons[r][c] = button
                binding.gridLayout.addView(button)
            }
        }
    }

    private fun placeMines() {
        var minesPlaced = 0
        while (minesPlaced < totalMines) {
            val r = (0 until rows).random()
            val c = (0 until cols).random()
            if (mineField[r][c] != -1) {
                mineField[r][c] = -1
                minesPlaced++
            }
        }
    }

    private fun calculateNumbers() {
        val dr = intArrayOf(-1, -1, -1, 0, 0, 1, 1, 1)
        val dc = intArrayOf(-1, 0, 1, -1, 1, -1, 0, 1)

        for (r in 0 until rows) {
            for (c in 0 until cols) {
                if (mineField[r][c] == -1) continue
                var count = 0
                for (i in 0 until 8) {
                    val nr = r + dr[i]
                    val nc = c + dc[i]
                    if (nr in 0 until rows && nc in 0 until cols && mineField[nr][nc] == -1) {
                        count++
                    }
                }
                mineField[r][c] = count
            }
        }
    }

    private fun onCellClicked(r: Int, c: Int) {
        if (isGameOver || revealed[r][c]) return

        if (mineField[r][c] == -1) {
            gameOver(false)
            return
        }

        revealCell(r, c)

        val totalSafeCells = rows * cols - totalMines
        if (safeCellsRevealed == totalSafeCells) {
            gameOver(true)
        }
    }

    private fun revealCell(r: Int, c: Int) {
        if (r !in 0 until rows || c !in 0 until cols || revealed[r][c]) return

        revealed[r][c] = true
        val button = buttons[r][c]
        button.isEnabled = false
        button.setBackgroundColor(Color.LTGRAY)

        val cellValue = mineField[r][c]
        if (cellValue > 0) {
            button.text = cellValue.toString()
        }

        safeCellsRevealed++
        score++
        updateScore()

        if (cellValue == 0) {
            val dr = intArrayOf(-1, -1, -1, 0, 0, 1, 1, 1)
            val dc = intArrayOf(-1, 0, 1, -1, 1, -1, 0, 1)
            for (i in 0 until 8) {
                revealCell(r + dr[i], c + dc[i])
            }
        }
    }

    private fun updateScore() {
        binding.tvScore.text = "Pontuação: $score"
    }

    private fun gameOver(won: Boolean) {
        isGameOver = true
        binding.btnRestart.visibility = View.VISIBLE

        for (r in 0 until rows) {
            for (c in 0 until cols) {
                if (mineField[r][c] == -1) {
                    buttons[r][c].text = "*"
                    buttons[r][c].setBackgroundColor(Color.RED)
                }
                buttons[r][c].isEnabled = false
            }
        }

        val message = if (won) "Você venceu!" else "Fim de jogo!"
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

        saveMatchResult()
    }

    private fun saveMatchResult() {
        val result = MatchResult(playerName = playerName, score = score)
        RetrofitClient.instance.saveMatch(result).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@GameActivity, "Partida salva!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@GameActivity, "Erro ao salvar: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@GameActivity, "Erro de conexão: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
