package com.example.campo_minado

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.campo_minado.databinding.ItemMatchBinding
import com.example.campo_minado.model.MatchResult

class HistoryAdapter(private val matches: List<MatchResult>) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemMatchBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMatchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val match = matches[position]
        holder.binding.tvMatchPlayerName.text = "Jogador: ${match.playerName}"
        holder.binding.tvMatchScore.text = "Pontuação: ${match.score}"
        holder.binding.tvMatchDate.text = match.date ?: ""
    }

    override fun getItemCount(): Int = matches.size
}
