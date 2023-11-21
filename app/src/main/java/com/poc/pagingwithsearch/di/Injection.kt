package com.poc.pagingwithsearch.di

import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import com.poc.pagingwithsearch.data.MainRepository
import com.poc.pagingwithsearch.data.RetrofitService
import com.poc.pagingwithsearch.presentation.ViewModelFactory

object Injection {

    private fun provideMainRepository(): MainRepository {
        return MainRepository(RetrofitService.getInstance())
    }

    fun provideViewModelFactory(
        owner: SavedStateRegistryOwner
    ): ViewModelProvider.Factory {
        return ViewModelFactory(owner, provideMainRepository())
    }
}
