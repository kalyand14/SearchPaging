package com.poc.pagingwithsearch.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.poc.pagingwithsearch.presentation.model.UiModel

object MovieDiffUtil : DiffUtil.ItemCallback<UiModel>() {
    override fun areItemsTheSame(oldItem: UiModel, newItem: UiModel): Boolean {
        // Id is unique.
        return (oldItem is UiModel.MovieItem && newItem is UiModel.MovieItem &&
                oldItem.movie.original_title == newItem.movie.original_title) ||
                (oldItem is UiModel.SeparatorItem && newItem is UiModel.SeparatorItem &&
                        oldItem.date == newItem.date)
    }

    override fun areContentsTheSame(oldItem: UiModel, newItem: UiModel): Boolean {
        return oldItem == newItem
    }
}