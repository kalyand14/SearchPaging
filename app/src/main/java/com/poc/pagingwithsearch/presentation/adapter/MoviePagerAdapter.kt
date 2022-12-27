package com.poc.pagingwithsearch.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.poc.pagingwithsearch.R
import com.poc.pagingwithsearch.databinding.AdapterMovieBinding
import com.poc.pagingwithsearch.presentation.adapter.viewHolder.MovieViewHolder
import com.poc.pagingwithsearch.presentation.adapter.viewHolder.SeparatorViewHolder
import com.poc.pagingwithsearch.presentation.model.UiModel

class MoviePagerAdapter :
    PagingDataAdapter<UiModel, RecyclerView.ViewHolder>(MovieDiffUtil) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val uiModel = getItem(position)
        uiModel.let {
            when (uiModel) {
                is UiModel.MovieItem -> (holder as MovieViewHolder).bind(uiModel.movie)
                is UiModel.SeparatorItem -> (holder as SeparatorViewHolder).bind(uiModel.date)
                else -> {}
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == R.layout.adapter_movie) {
            val inflater = LayoutInflater.from(parent.context)
            val binding = AdapterMovieBinding.inflate(inflater, parent, false)
            return MovieViewHolder(binding)
        } else {
            SeparatorViewHolder.create(parent)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is UiModel.MovieItem -> R.layout.adapter_movie
            is UiModel.SeparatorItem -> R.layout.separator_view_item
            null -> throw UnsupportedOperationException("Unknown view")
        }
    }

}