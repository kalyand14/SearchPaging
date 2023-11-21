package com.poc.pagingwithsearch.presentation

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.poc.pagingwithsearch.data.MainRepository

class ViewModelFactory constructor(
    owner: SavedStateRegistryOwner,
    private val repository: MainRepository
) : AbstractSavedStateViewModelFactory(owner, null) {

    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(this.repository, handle) as T
        } else if (modelClass.isAssignableFrom(GenreViewModel::class.java)) {
            GenreViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}
