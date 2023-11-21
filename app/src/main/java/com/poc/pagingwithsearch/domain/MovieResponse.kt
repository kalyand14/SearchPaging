package com.poc.pagingwithsearch.domain

import android.provider.ContactsContract.Data

data class MovieResponse(val page: Int, val results: List<Movie>, val total_results: Int)

data class Movie(
    val original_title: String,
    val poster_path: String,
    val genre_ids: List<Int>,
    val overview: String,
    val release_date: String,
    var total_results: Int,
    var subAccountList: List<DataItem>
)

sealed class DataItem {
    data class SubAccount(
        val id: Int,
        val description: String,
        var status: Boolean,
        var isDisplay: Boolean = false
    ): DataItem()

    data class ShowCloseAccount(
        val id: Int,
        var status: Boolean
    ) : DataItem()
}

