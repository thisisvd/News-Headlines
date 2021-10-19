package com.vdcodeassociate.newsheadlines.kotlin.restAPI

import com.vdcodeassociate.newsheadlines.kotlin.constants.Constants.Companion.API_KEY
import com.vdcodeassociate.newsheadlines.kotlin.model.ResponseModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIInterface {

    // top headlines
    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        countryCode: String = "us",
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<ResponseModel>

    // search news
    @GET("v2/everything")
    suspend fun getSearchNews(
        @Query("q")
        searchQuery: String,
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<ResponseModel>

}