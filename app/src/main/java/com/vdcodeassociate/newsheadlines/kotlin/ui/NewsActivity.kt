package com.vdcodeassociate.newsheadlines.kotlin.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.vdcodeassociate.newsheadlines.R
import com.vdcodeassociate.newsheadlines.databinding.ActivityNewsBinding
import com.vdcodeassociate.newsheadlines.kotlin.roomdb.ArticleDatabase
import com.vdcodeassociate.newsheadlines.kotlin.viewModel.NewsRepository
import com.vdcodeassociate.newsheadlines.kotlin.viewModel.NewsViewModel
import com.vdcodeassociate.newsheadlines.kotlin.viewModel.NewsViewModelProviderFactory

class NewsActivity : AppCompatActivity() {

    // init viewModel
    lateinit var viewModel: NewsViewModel

    // viewBinding
    lateinit var binding: ActivityNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ViewModel Implementation
        val repository = NewsRepository(ArticleDatabase(this))
        val viewModelProviderFactory = NewsViewModelProviderFactory(repository)
        viewModel = ViewModelProvider(this,viewModelProviderFactory)[NewsViewModel::class.java]

        // bottom nav Implementation
        binding.bottomNavigationView.setupWithNavController(findViewById<FrameLayout>(R.id.newsNavHostFragment).findNavController())

    }
}