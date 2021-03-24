package com.vdcodeassociate.newsheadlines;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.vdcodeassociate.newsheadlines.model.Articles;
import com.vdcodeassociate.newsheadlines.utils.Utils;

import java.util.List;

import okhttp3.internal.Util;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<Articles> articles;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public Adapter(List<Articles> articles, Context context) {
        this.articles = articles;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_card_view,parent,false);
        return new ViewHolder(view,onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ViewHolder holder1 =  holder;
        Articles article = articles.get(position);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(Utils.getRandomDrawbleColor());
        requestOptions.error(Utils.getRandomDrawbleColor());
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.centerCrop();

        Glide.with(context)
                .load(article.getUrlToImage())
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.imageView);

        holder.title.setText(article.getTitle());
        holder.description.setText(article.getDescription());
        holder.source.setText(article.getSource().get(position).getName());
        holder.date.setText("\u2022"+Utils.DateToTimeFormat(article.getPublishedAt()));
        holder.publishedAt.setText(Utils.DateFormat(article.getPublishedAt()));
        holder.author.setText(article.getAuthor());

    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView author,publishedAt,title,source,description,date;
        public ImageView imageView;
        public ProgressBar progressBar;
        OnItemClickListener itemClickListener;

        public ViewHolder(@NonNull View itemView,OnItemClickListener itemClickListener) {
            super(itemView);

            itemView.setOnClickListener(this::onClick);
            author = itemView.findViewById(R.id.author);
            publishedAt = itemView.findViewById(R.id.publishedAt);
            title = itemView.findViewById(R.id.title);
            source = itemView.findViewById(R.id.source);
            description = itemView.findViewById(R.id.description);
            date = itemView.findViewById(R.id.date);
            imageView = itemView.findViewById(R.id.image1);
            progressBar = itemView.findViewById(R.id.progress_bar1);

            this.itemClickListener = itemClickListener;

        }

        @Override
        public void onClick(View v) {

        }
    }

}
