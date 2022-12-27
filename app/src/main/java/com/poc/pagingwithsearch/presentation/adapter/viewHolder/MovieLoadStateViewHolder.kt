package com.poc.pagingwithsearch.presentation.adapter.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.poc.pagingwithsearch.R
import com.poc.pagingwithsearch.databinding.LoadstateViewItemBinding

class MovieLoadStateViewHolder(
    private val binding: LoadstateViewItemBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.retryButton.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.errorMsg.text = loadState.error.localizedMessage
        }
        binding.progressBar.isVisible = loadState is LoadState.Loading
        //binding.retryButton.isVisible = loadState is LoadState.Error
        binding.errorMsg.isVisible = loadState is LoadState.Error


    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): MovieLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.loadstate_view_item, parent, false)
            val binding = LoadstateViewItemBinding.bind(view)
            return MovieLoadStateViewHolder(binding, retry)
        }
    }

}