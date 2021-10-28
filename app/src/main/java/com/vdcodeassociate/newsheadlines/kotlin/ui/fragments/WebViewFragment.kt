package com.vdcodeassociate.newsheadlines.kotlin.ui.fragments

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.vdcodeassociate.newsheadlines.R
import com.vdcodeassociate.newsheadlines.databinding.FragmentWebViewBinding
import com.vdcodeassociate.newsheadlines.kotlin.ui.NewsActivity
import com.vdcodeassociate.newsheadlines.kotlin.viewModel.NewsViewModel

class WebViewFragment: Fragment(R.layout.fragment_web_view) {

    // viewModel
    lateinit var viewModel: NewsViewModel

    // getting arguments as passed
    val args: WebViewFragmentArgs by navArgs()

    // viewBinding
    lateinit var binding: FragmentWebViewBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWebViewBinding.bind(view)

        // viewModel Implementation
        viewModel = (activity as NewsActivity).viewModel

        // init args values that have been passed
        val article = args.article

        binding.apply {

            webView.apply {
                webViewClient = WebViewClient()
                loadUrl(article.url)
            }

            fab.setOnClickListener {
                viewModel.saveArticles(article)
                Snackbar.make(view, "Article saved successfully!", Snackbar.LENGTH_SHORT).show()
            }

        }

    }

}