package com.vdcodeassociate.newsheadlines.kotlin.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vdcodeassociate.newsheadlines.R
import com.vdcodeassociate.newsheadlines.databinding.FragmentSavedBinding
import com.vdcodeassociate.newsheadlines.databinding.FragmentSearchBinding
import com.vdcodeassociate.newsheadlines.kotlin.adapter.ArticleAdapter
import com.vdcodeassociate.newsheadlines.kotlin.constants.Constants.Companion.SEARCH_NEWS_TIME_DELAY
import com.vdcodeassociate.newsheadlines.kotlin.ui.NewsActivity
import com.vdcodeassociate.newsheadlines.kotlin.util.Resource
import com.vdcodeassociate.newsheadlines.kotlin.viewModel.NewsViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment: Fragment(R.layout.fragment_search) {

    // viewModel
    lateinit var viewModel: NewsViewModel

    // Adapter
    lateinit var articleAdapter: ArticleAdapter

    // TAG
    val TAG = "SearchFragment"

    // viewBinding
    lateinit var binding: FragmentSearchBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)

        // viewModel Implementation
        viewModel = (activity as NewsActivity).viewModel

        // Calling Recycler View
        setupRecyclerView()

        // Set Up adapter
        articleAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article",it)
            }
            findNavController().navigate(
                R.id.action_searchFragment_to_webViewFragment,
                bundle
            )
        }

        // delay in request
        var job: Job? = null
        binding.apply {
            newsSearchEditText.addTextChangedListener { editable ->
                job?.cancel()
                job = MainScope().launch {
                    delay(SEARCH_NEWS_TIME_DELAY)
                    editable?.let {
                        if (editable.toString().isNotEmpty()) {
                            viewModel.getSearchNews(editable.toString())
                        }
                    }
                }
            }
        }

        // Set up response from view model with recycler adapter
        viewModel.searchNews.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        articleAdapter.differ.submitList(newsResponse.articles)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e(TAG,"AN error occurred : $message")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })

    }

    private fun hideProgressBar(){
        binding.paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar(){
        binding.paginationProgressBar.visibility = View.INVISIBLE
    }

    // Set Up Recycler View
    private fun setupRecyclerView(){
        articleAdapter = ArticleAdapter()
        binding.newsSearchRecyclerView.apply {
            adapter = articleAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

}