package com.vdcodeassociate.newsheadlines.kotlin.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebViewClient
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.vdcodeassociate.newsheadlines.R
import com.vdcodeassociate.newsheadlines.kotlin.adapter.ArticleAdapter
import com.vdcodeassociate.newsheadlines.kotlin.constants.Constants.Companion.SEARCH_NEWS_TIME_DELAY
import com.vdcodeassociate.newsheadlines.kotlin.ui.NewsActivity
import com.vdcodeassociate.newsheadlines.kotlin.util.Resource
import com.vdcodeassociate.newsheadlines.kotlin.viewModel.NewsViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_web_view.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WebViewFragment: Fragment(R.layout.fragment_web_view) {

    lateinit var viewModel: NewsViewModel

    val args: WebViewFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as NewsActivity).viewModel

        val article = args.article

        webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url)
        }

        fab.setOnClickListener {
            viewModel.saveArticles(article)
            Snackbar.make(view,"Article saved successfully!",Snackbar.LENGTH_SHORT).show()
        }

    }

}