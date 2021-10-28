package com.vdcodeassociate.newsheadlines.kotlin.adapter

import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.vdcodeassociate.newsheadlines.R
import com.vdcodeassociate.newsheadlines.databinding.NewsCardViewBinding
import com.vdcodeassociate.newsheadlines.kotlin.model.Article
import com.vdcodeassociate.newsheadlines.kotlin.util.Utils

class ArticleAdapter: RecyclerView.Adapter<ArticleAdapter.ArticleViewModel>() {

    // ViewHolder inner class
    inner class ArticleViewModel(val binding: NewsCardViewBinding): RecyclerView.ViewHolder(binding.root)

    // Diff Util call back
    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }

    // set differ
    val differ = AsyncListDiffer(this, differCallback)

    // Recycler Components
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewModel {
        return ArticleViewModel(
            NewsCardViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: ArticleViewModel, position: Int) {

        val article = differ.currentList[position]

        // Putting Image in ImageView
        val requestOptions = RequestOptions()
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL)
        requestOptions.centerCrop()

        holder.binding.apply {
            Glide.with(root)
                .load(article.urlToImage)
                .apply(requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(newsImage)
            newsTitle.text = article.title
            newsPublishedAt.text = Utils.DateFormat(article.publishedAt)
            newsTime.text = Utils.DateFormat(article.publishedAt)

            root.setOnClickListener {
                onItemClickListener?.let { it(article) }
            }

        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Article) -> Unit)? = null

    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }

}