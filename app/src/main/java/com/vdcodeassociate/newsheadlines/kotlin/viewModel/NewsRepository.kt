package com.vdcodeassociate.newsheadlines.kotlin.viewModel

import com.vdcodeassociate.newsheadlines.kotlin.restAPI.APIClient
import com.vdcodeassociate.newsheadlines.kotlin.roomdb.ArticleDatabase

class NewsRepository(
    val db: ArticleDatabase
) {

    // calling the api function -
    suspend fun getLatestNews(code: String, page: Int) =
        APIClient.api.getBreakingNews(code,page)

}