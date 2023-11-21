package com.poc.pagingwithsearch.presentation.model

import com.poc.pagingwithsearch.domain.Movie

sealed class UiModel {
    data class MovieItem(val movie: Movie) : UiModel()
    data class SeparatorItem(val date: String) : UiModel()
}
