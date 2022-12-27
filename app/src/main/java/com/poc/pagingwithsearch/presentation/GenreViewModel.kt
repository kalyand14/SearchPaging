package com.poc.pagingwithsearch.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.poc.pagingwithsearch.data.MainRepository
import com.poc.pagingwithsearch.domain.Genre
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class GenreViewModel constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    var state: MutableStateFlow<List<Genre>> = MutableStateFlow(emptyList())

    fun getGenre() {
        viewModelScope.launch {
            mainRepository.getAllGenre()
                .collect { state.value = it }
        }
    }
}