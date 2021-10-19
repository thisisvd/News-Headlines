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
import com.vdcodeassociate.newsheadlines.kotlin.adapter.ArticleAdapter
import com.vdcodeassociate.newsheadlines.kotlin.constants.Constants.Companion.SEARCH_NEWS_TIME_DELAY
import com.vdcodeassociate.newsheadlines.kotlin.ui.NewsActivity
import com.vdcodeassociate.newsheadlines.kotlin.util.Resource
import com.vdcodeassociate.newsheadlines.kotlin.viewModel.NewsViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment: Fragment(R.layout.fragment_search) {

    lateinit var viewModel: NewsViewModel

    lateinit var articleAdapter: ArticleAdapter

    val TAG = "SearchFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as NewsActivity).viewModel

        setupRecyclerView()

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
        newsSearchEditText.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_NEWS_TIME_DELAY)
                editable?.let {
                    if(editable.toString().isNotEmpty()){
                        viewModel.getSearchNews(editable.toString())
                    }
                }
            }
        }

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
        paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar(){
        paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun setupRecyclerView(){
        articleAdapter = ArticleAdapter()
        newsSearchRecyclerView.apply {
            adapter = articleAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

}