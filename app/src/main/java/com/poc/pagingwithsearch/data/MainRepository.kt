package com.poc.pagingwithsearch.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.poc.pagingwithsearch.domain.Genre
import com.poc.pagingwithsearch.domain.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MainRepository constructor(private val retrofitService: RetrofitService) {

    fun getAllMovies(filter: String): Flow<PagingData<Movie>> {

        return Pager(
            config = PagingConfig(
                pageSize = Companion.ITEMS_PER_PAGE,
                prefetchDistance = 5,
                enablePlaceholders = false,
                initialLoadSize = 2
            ),
            pagingSourceFactory = {
                MoviePagingSource(retrofitService, filter)
            }, initialKey = 1
        ).flow
    }

    companion object {
        private const val ITEMS_PER_PAGE = 10
    }

    suspend fun getAllGenre(): Flow<List<Genre>> {
        return flow {
            val result =
                retrofitService.getGenre(api_key = "21760cef2bb2ba72a3cf69f066977d22")
            result.body()?.genres?.let { emit(it) }
        }
    }

}