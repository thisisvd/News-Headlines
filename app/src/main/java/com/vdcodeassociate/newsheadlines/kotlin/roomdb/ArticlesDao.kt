package com.vdcodeassociate.newsheadlines.kotlin.roomdb

import androidx.lifecycle.LiveData
import androidx.room.*
import com.vdcodeassociate.newsheadlines.kotlin.model.Article

@Dao
interface ArticlesDao {

    // Insert an articles
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticleInRoom(article: Article): Long

    // Get all saved articles
    @Query("SELECT * FROM articles")
    fun getArticlesFromRoom(): LiveData<List<Article>>

    // Delete an articles
    @Delete()
    suspend fun deleteArticleInRoom(article: Article)

}