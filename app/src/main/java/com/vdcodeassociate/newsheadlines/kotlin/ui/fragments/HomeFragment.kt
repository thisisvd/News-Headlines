package com.vdcodeassociate.newsheadlines.kotlin.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vdcodeassociate.newsheadlines.R
import com.vdcodeassociate.newsheadlines.databinding.FragmentHomeBinding
import com.vdcodeassociate.newsheadlines.kotlin.adapter.ArticleAdapter
import com.vdcodeassociate.newsheadlines.kotlin.ui.NewsActivity
import com.vdcodeassociate.newsheadlines.kotlin.util.Resource
import com.vdcodeassociate.newsheadlines.kotlin.viewModel.NewsViewModel

class HomeFragment: Fragment(R.layout.fragment_home) {

    // viewModel
    lateinit var viewModel: NewsViewModel

    // Adapter
    lateinit var articleAdapter: ArticleAdapter

    // TAG
    val TAG = "HomeFragment"

    // viewBinding
    lateinit var binding: FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

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
                R.id.action_homeFragment_to_webViewFragment,
                bundle
            )
        }

        // Set up response from view model with recycler adapter
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
        binding.paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar(){
        binding.paginationProgressBar.visibility = View.INVISIBLE
    }

    // Set Up Recycler View
    private fun setupRecyclerView(){
        articleAdapter = ArticleAdapter()
        binding.newsRecyclerView.apply {
            adapter = articleAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

}