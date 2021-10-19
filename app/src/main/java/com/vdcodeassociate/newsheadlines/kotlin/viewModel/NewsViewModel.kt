package com.vdcodeassociate.newsheadlines.kotlin.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vdcodeassociate.newsheadlines.kotlin.model.Article
import com.vdcodeassociate.newsheadlines.kotlin.model.ResponseModel
import com.vdcodeassociate.newsheadlines.kotlin.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
    val repository: NewsRepository
): ViewModel() {

    val breakingNews: MutableLiveData<Resource<ResponseModel>> = MutableLiveData()
    val breakingNewsPage = 1

    val searchNews: MutableLiveData<Resource<ResponseModel>> = MutableLiveData()
    val searchNewsPage = 1

    init {
        getLatestNews("us")
    }

    // get getLatestNews data method
    fun getLatestNews(countryCode: String) = viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        val response = repository.getLatestNews(countryCode, breakingNewsPage)
        breakingNews.postValue(handleLatestNewsResponse(response))
    }

    // get data method
    fun getSearchNews(query: String) = viewModelScope.launch {
        searchNews.postValue(Resource.Loading())
        val response = repository.getSearchNews(query, searchNewsPage)
        searchNews.postValue(handleSearchNewsResponse(response))
    }

    // handle Response for LatestNews()
    private fun handleLatestNewsResponse(response: Response<ResponseModel>) : Resource<ResponseModel>{
        if(response.isSuccessful){
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    // handle Response for searchNews()
    private fun handleSearchNewsResponse(response: Response<ResponseModel>) : Resource<ResponseModel>{
        if(response.isSuccessful){
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    // Room Database setup
    // insert
    fun saveArticles(article: Article) = viewModelScope.launch {
        repository.insert(article)
    }

    // read articles
    fun getSavedArticles() = repository.getSavedArticles()

    // delete
    fun deleteArticles(article: Article) = viewModelScope.launch {
        repository.delete(article)
    }

}




















