package com.poc.pagingwithsearch.presentation.adapter

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.poc.pagingwithsearch.presentation.adapter.viewHolder.MovieLoadStateViewHolder

class MovieLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<MovieLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: MovieLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): MovieLoadStateViewHolder {
        return MovieLoadStateViewHolder.create(parent, retry)
    }
}