package com.vdcodeassociate.newsheadlines.kotlin.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vdcodeassociate.newsheadlines.R
import com.vdcodeassociate.newsheadlines.kotlin.adapter.ArticleAdapter
import com.vdcodeassociate.newsheadlines.kotlin.ui.NewsActivity
import com.vdcodeassociate.newsheadlines.kotlin.util.Resource
import com.vdcodeassociate.newsheadlines.kotlin.viewModel.NewsViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment: Fragment(R.layout.fragment_home) {

    lateinit var viewModel: NewsViewModel

    lateinit var articleAdapter: ArticleAdapter

    val TAG = "HomeFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as NewsActivity).viewModel

        setupRecyclerView()

        articleAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article",it)
            }
            findNavController().navigate(
                R.id.action_homeFragment_to_webViewFragment,
                bundle
            )
        }

        viewModel.breakingNews.observe(viewLifecycleOwner, Observer { response ->
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
        newsRecyclerView.apply {
            adapter = articleAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

}