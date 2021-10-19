package com.vdcodeassociate.newsheadlines.kotlin.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.vdcodeassociate.newsheadlines.R
import com.vdcodeassociate.newsheadlines.kotlin.adapter.ArticleAdapter
import com.vdcodeassociate.newsheadlines.kotlin.ui.NewsActivity
import com.vdcodeassociate.newsheadlines.kotlin.viewModel.NewsViewModel
import kotlinx.android.synthetic.main.fragment_saved.*
import kotlinx.android.synthetic.main.fragment_search.*

class SavedFragment: Fragment(R.layout.fragment_saved) {

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
                R.id.action_savedFragment_to_webViewFragment,
                bundle
            )
        }

        // Swiping functionality for delete
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = articleAdapter.differ.currentList[position]
                viewModel.deleteArticles(article)
                Snackbar.make(view,"Successfully deleted article!", Snackbar.LENGTH_SHORT).apply {
                    setAction("Undo"){
                        viewModel.saveArticles(article)
                        articleAdapter.notifyDataSetChanged()
                    }
                }.show()
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(newsSavedRecyclerView)
        }

        viewModel.getSavedArticles().observe(viewLifecycleOwner, Observer { articles ->
            articleAdapter.differ.submitList(articles)
        })

    }

    private fun setupRecyclerView(){
        articleAdapter = ArticleAdapter()
        newsSavedRecyclerView.apply {
            adapter = articleAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

}