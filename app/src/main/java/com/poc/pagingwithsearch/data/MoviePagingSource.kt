package com.poc.pagingwithsearch.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.poc.pagingwithsearch.domain.DataItem
import com.poc.pagingwithsearch.domain.Movie
import kotlin.random.Random

class MoviePagingSource(
    private val apiService: RetrofitService,
    private val filter: String
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {

        return try {
            val position = params.key ?: 1

            if (position * params.loadSize > 500) {
                throw Exception(
                    "You reached max of no. of records for search!, " +
                            "Please use filter to narrow-down your search. "
                )
            }

            // val genre: List<Genre> = apiService.getGenre(   api_key = "21760cef2bb2ba72a3cf69f066977d22")
            val response = apiService.getTopRatedMovies(
                api_key = "21760cef2bb2ba72a3cf69f066977d22",
                vote_count = "5",
                language = "en-US",
                with_genres = filter,
                sort_by = "release_date.desc",
                page = position
            )
            val result = response.body()!!.results.map {
                it.total_results = response.body()!!.total_results
                // it.genre_ids.map { genre.body()!! }
                it.subAccountList = generateSubAccountList()
                it
            }

            LoadResult.Page(
                data = result,
                prevKey = if (position == 1) null else position - 1,
                nextKey = position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    private fun generateSubAccountList(): List<DataItem> {
        val allList = mutableListOf<DataItem>()
        val count = Random.nextInt(1, 10)
        repeat(count) {
            allList.add(generateSubAccount())
        }

        val activeList = allList.filter { dataItem ->
            (dataItem as DataItem.SubAccount).status
        }

        val toggleDataItem = DataItem.ShowCloseAccount(allList.size, false)

        val closedList = allList.filter { dataItem ->
            (dataItem as DataItem.SubAccount).status.not()
        }

        val subAccountList = mutableListOf<DataItem>()
        subAccountList.addAll(activeList)
        subAccountList.add(toggleDataItem)
        subAccountList.addAll(closedList)

        return subAccountList
    }

    private fun generateSubAccount(): DataItem.SubAccount {
        val randomId = Random.nextInt(1, 1000)
        val randomName = "Description${Random.nextInt(1, 100)}"
        return DataItem.SubAccount(randomId, randomName, Random.nextBoolean()).apply {
            isDisplay = this.status == true
        }
    }
}
