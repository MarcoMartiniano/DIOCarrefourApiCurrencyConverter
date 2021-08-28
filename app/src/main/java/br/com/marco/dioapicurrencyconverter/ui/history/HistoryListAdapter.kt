package br.com.marco.dioapicurrencyconverter.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.marco.dioapicurrencyconverter.core.extensions.formatCurrency
import br.com.marco.dioapicurrencyconverter.data.model.Coin
import br.com.marco.dioapicurrencyconverter.data.model.ExchangeResponseValue
import br.com.marco.dioapicurrencyconverter.databinding.ItemHistoryBinding

class HistoryListAdapter : ListAdapter<ExchangeResponseValue, HistoryListAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHistoryBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemHistoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ExchangeResponseValue) {
            binding.tvName.text = item.name
            binding.tvCotacao.text = item.cotacao.toString()
            binding.tvDataCotacao.text = "Data da cotação:  " + item.data
            val coin = Coin.getByName(item.codein)
            binding.tvValue.text = item.bid.formatCurrency(coin.locale)
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<ExchangeResponseValue>() {
    override fun areItemsTheSame(oldItem: ExchangeResponseValue, newItem: ExchangeResponseValue) = oldItem == newItem
    override fun areContentsTheSame(oldItem: ExchangeResponseValue, newItem: ExchangeResponseValue) = oldItem.id == newItem.id
}