package com.vdcodeassociate.newsheadlines.kotlin.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class NewsViewModelProviderFactory(
    val repository: NewsRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(repository) as T
    }

}