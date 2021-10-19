package com.vdcodeassociate.newsheadlines.kotlin.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.vdcodeassociate.newsheadlines.R
import com.vdcodeassociate.newsheadlines.kotlin.roomdb.ArticleDatabase
import com.vdcodeassociate.newsheadlines.kotlin.viewModel.NewsRepository
import com.vdcodeassociate.newsheadlines.kotlin.viewModel.NewsViewModel
import com.vdcodeassociate.newsheadlines.kotlin.viewModel.NewsViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_news.*

class NewsActivity : AppCompatActivity() {

    // init viewModel
    lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        val repository = NewsRepository(ArticleDatabase(this))
        val viewModelProviderFactory = NewsViewModelProviderFactory(repository)
        viewModel = ViewModelProvider(this,viewModelProviderFactory)[NewsViewModel::class.java]

        bottomNavigationView.setupWithNavController(newsNavHostFragment.findNavController())

    }
}