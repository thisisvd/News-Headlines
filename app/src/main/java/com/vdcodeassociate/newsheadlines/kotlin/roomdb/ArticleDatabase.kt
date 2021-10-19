package com.vdcodeassociate.newsheadlines.kotlin.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vdcodeassociate.newsheadlines.kotlin.model.Article

@Database(
    entities = [Article::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class ArticleDatabase : RoomDatabase(){

    // get Articles from Dao
    abstract fun getArticlesDao(): ArticlesDao

    // All database instance
    companion object{

        @Volatile
        private var instance: ArticleDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance?: kotlin.synchronized(LOCK){
            instance ?: createDatabase(context).also{ instance = it}
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ArticleDatabase::class.java,
                "articles_db.db"
            ).build()

    }

}