package com.poc.pagingwithsearch.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.poc.pagingwithsearch.data.MainRepository
import com.poc.pagingwithsearch.presentation.model.UiModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel constructor(
    private val mainRepository: MainRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    /* val items: Flow<PagingData<UiModel>> = mainRepository.getAllMovies().map { pagingData ->
         pagingData.filter { movie ->
             movie.original_title.contains("city", true) || movie.overview.contains("city ", true)
         }
     }
         .map { pageingdata -> pageingdata.map { UiModel.MovieItem(it) } }
         .map {
             it.insertSeparators { before, after ->
                 if (after == null) {
                     // we're at the end of the list
                     return@insertSeparators null
                 }

                 if (before == null) {
                     // we're at the beginning of the list
                     return@insertSeparators UiModel.SeparatorItem("${after.movie.release_date}")
                 }
                 // check between 2 items
                 if (checkDayDiff(after.movie.release_date, before.movie.release_date) < 0) {
                     //Insert Date
                     UiModel.SeparatorItem(after.movie.release_date)
                 } else {
                     // no separator
                     null
                 }
             }
         }

         .cachedIn(viewModelScope)*/


    /**
     * Stream of immutable states representative of the UI.
     */
    var totalResults: MutableStateFlow<Int> = MutableStateFlow(0)

    var state: StateFlow<UiState>

    var pagingDataFlow: Flow<PagingData<UiModel>>

    var accept: (UiAction) -> Unit

    init {
        val initialFilter: String = ""
        val initialQuery: String = savedStateHandle[LAST_SEARCH_QUERY] ?: DEFAULT_QUERY
        val lastQueryScrolled: String = savedStateHandle[LAST_QUERY_SCROLLED] ?: DEFAULT_QUERY
        val actionStateFlow = MutableSharedFlow<UiAction>()

        val searches = actionStateFlow
            .filterIsInstance<UiAction.Search>()
            .distinctUntilChanged()
            .onStart { emit(UiAction.Search(query = initialQuery)) }

        val queriesScrolled = actionStateFlow
            .filterIsInstance<UiAction.Scroll>()
            .distinctUntilChanged()
            // This is shared to keep the flow "hot" while caching the last query scrolled,
            // otherwise each flatMapLatest invocation would lose the last query scrolled,
            .shareIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
                replay = 1
            )
            .onStart { emit(UiAction.Scroll(currentQuery = lastQueryScrolled)) }

        val filter = actionStateFlow
            .filterIsInstance<UiAction.Filter>()
            .distinctUntilChanged()
            .onStart { emit(UiAction.Filter(filter = initialFilter)) }

        pagingDataFlow = combine(
            searches,
            filter,
            ::Pair
        ).flatMapLatest { (search, filter) ->
            searchMovie(
                queryString = search.query,
                filter = filter.filter
            )
        }.cachedIn(viewModelScope)

        /*  pagingDataFlow = searches
              .flatMapLatest { searchMovie(queryString = it.query) }
              .cachedIn(viewModelScope)*/


        state = combine(
            searches,
            queriesScrolled,
            ::Pair
        ).map { (search, scroll) ->
            UiState(
                query = search.query,
                lastQueryScrolled = scroll.currentQuery,
                // If the search query matches the scroll query, the user has scrolled
                hasNotScrolledForCurrentSearch = search.query != scroll.currentQuery
            )
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
                initialValue = UiState()
            )


        accept = { action ->
            viewModelScope.launch { actionStateFlow.emit(action) }
        }
    }

    override fun onCleared() {
        savedStateHandle[LAST_SEARCH_QUERY] = state.value.query
        savedStateHandle[LAST_QUERY_SCROLLED] = state.value.lastQueryScrolled
        super.onCleared()
    }

    private fun searchMovie(queryString: String, filter: String): Flow<PagingData<UiModel>> =
        mainRepository.getAllMovies(filter).map { pagingData ->
            pagingData.filter { movie ->
                if (queryString.isNotEmpty()) {
                    movie.original_title.contains(queryString, true) || movie.overview.contains(
                        queryString,
                        true
                    )
                } else {
                    true
                }
            }
        }
            .map { pageingdata ->
                pageingdata.map {

                    totalResults.emit(it.total_results)

                    UiModel.MovieItem(it)
                }
            }
            .map {
                it.insertSeparators { before, after ->
                    if (after == null) {
                        // we're at the end of the list
                        return@insertSeparators null
                    }

                    if (before == null) {
                        // we're at the beginning of the list
                        return@insertSeparators UiModel.SeparatorItem("${after.movie.release_date}")
                    }
                    // check between 2 items
                    if (checkDayDiff(after.movie.release_date, before.movie.release_date) < 0) {
                        //Insert Date
                        UiModel.SeparatorItem(after.movie.release_date)
                    } else {
                        // no separator
                        null
                    }
                }
            }

}

private const val LAST_QUERY_SCROLLED: String = "last_query_scrolled"
private const val LAST_SEARCH_QUERY: String = "last_search_query"
private const val DEFAULT_QUERY = ""

data class UiState(
    val query: String = DEFAULT_QUERY,
    val lastQueryScrolled: String = DEFAULT_QUERY,
    val hasNotScrolledForCurrentSearch: Boolean = false,
)

sealed class UiAction {
    data class Search(val query: String) : UiAction()
    data class Filter(val filter: String) : UiAction()
    data class Scroll(val currentQuery: String) : UiAction()
}

fun checkDayDiff(date1String: String, date2String: String): Int {
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    val date1: Date = sdf.parse(date1String)
    val date2: Date = sdf.parse(date2String)
    return date1.compareTo(date2)
}