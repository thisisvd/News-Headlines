package com.vdcodeassociate.newsheadlines.kotlin.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vdcodeassociate.newsheadlines.kotlin.model.ResponseModel
import com.vdcodeassociate.newsheadlines.kotlin.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
    val repository: NewsRepository
): ViewModel() {

    val breakingNews: MutableLiveData<Resource<ResponseModel>> = MutableLiveData()
    val breakingNewsPage = 1

    init {
        getBreakingNews("us")
    }

    // get data method
    fun getBreakingNews(countryCode: String) = viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        val response = repository.getLatestNews(countryCode, breakingNewsPage)
        breakingNews.postValue(handleLatestNewsResponse(response))
    }

    private fun handleLatestNewsResponse(response: Response<ResponseModel>) : Resource<ResponseModel>{
        if(response.isSuccessful){
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

}