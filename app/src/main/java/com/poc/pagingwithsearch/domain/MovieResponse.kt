package com.poc.pagingwithsearch.domain

data class MovieResponse(val page: Int, val results: List<Movie>, val total_results: Int)


data class Movie(
    val original_title: String,
    val poster_path: String,
    val genre_ids: List<Int>,
    val overview: String,
    val release_date: String,
    var total_results: Int
)
