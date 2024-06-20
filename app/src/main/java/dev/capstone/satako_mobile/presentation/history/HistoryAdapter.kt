package dev.capstone.satako_mobile.presentation.history

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.capstone.satako_mobile.R
import dev.capstone.satako_mobile.data.response.HistoryItem
import dev.capstone.satako_mobile.databinding.ItemHistoryBinding
import dev.capstone.satako_mobile.presentation.home.diagnose.DiagnoseFragmentDirections
import dev.capstone.satako_mobile.utils.DateFormatter.formatIsoDate


class HistoryAdapter() : ListAdapter<HistoryItem, HistoryAdapter.HistoryViewHolder>(DIFF_CALLBACK) {

    class HistoryViewHolder(private val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(history: HistoryItem) {
            with(binding) {
                historyDiseaseName.text = history.disease
                historyDate.text = history.createdAt?.let { formatIsoDate(it) }
                Glide.with(itemView.context)
                    .load(history.imageUrl)
                    .placeholder(R.drawable.sample_scan)
                    .error(R.drawable.sample_scan)
                    .into(historyImageView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val history = getItem(position)
        holder.bind(history)
        holder.itemView.setOnClickListener {
            val toResultFragment =
                HistoryFragmentDirections.actionHistoryFragmentToResultFragment(
                    null,
                    null,
                    history
                )
            holder.itemView.findNavController().navigate(toResultFragment)
        }
    }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HistoryItem>() {
            override fun areItemsTheSame(oldItem: HistoryItem, newItem: HistoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: HistoryItem,
                newItem: HistoryItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}