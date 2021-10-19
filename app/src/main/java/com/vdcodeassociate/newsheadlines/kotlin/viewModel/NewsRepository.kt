package com.vdcodeassociate.newsheadlines.kotlin.viewModel

import com.vdcodeassociate.newsheadlines.kotlin.model.Article
import com.vdcodeassociate.newsheadlines.kotlin.restAPI.APIClient
import com.vdcodeassociate.newsheadlines.kotlin.roomdb.ArticleDatabase

class NewsRepository(
    val db: ArticleDatabase
) {

    // calling getLatestNews from the api function -
    suspend fun getLatestNews(code: String, page: Int) =
        APIClient.api.getBreakingNews(code,page)

    // calling getSearchNews from the api function -
    suspend fun getSearchNews(query: String,page: Int) =
        APIClient.api.getSearchNews(query,page)

    // Room Database setups
    // insert
    suspend fun insert(article: Article) = db.getArticlesDao().insertArticleInRoom(article)

    // get all saved articles
    fun getSavedArticles() = db.getArticlesDao().getArticlesFromRoom()

    // delete
    suspend fun delete(article: Article) = db.getArticlesDao().deleteArticleInRoom(article)

}