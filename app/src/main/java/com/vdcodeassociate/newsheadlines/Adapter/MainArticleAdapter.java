package com.vdcodeassociate.newsheadlines.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.vdcodeassociate.newsheadlines.R;
import com.vdcodeassociate.newsheadlines.Model.Articles;
import com.vdcodeassociate.newsheadlines.utils.Utils;

import java.util.List;

public class MainArticleAdapter extends RecyclerView.Adapter<MainArticleAdapter.ViewHolder> {
    private List<Articles> articleArrayList;
    private Context context;

    public MainArticleAdapter(List<Articles> articleArrayList, Context context) {
        this.articleArrayList = articleArrayList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_card_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final Articles articleModel = articleArrayList.get(position);

        // Putting Image in ImageView
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.centerCrop();

        Glide.with(context)
                .load(articleModel.getUrlToImage())
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                        viewHolder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, com.bumptech.glide.load.DataSource dataSource, boolean isFirstResource) {
//                        viewHolder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                })
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(viewHolder.imageView);

        // Putting Text in All TextViews
        if (!TextUtils.isEmpty(articleModel.getTitle())) {
            viewHolder.title.setText(articleModel.getTitle());
        }
//        if (!TextUtils.isEmpty(articleModel.getDescription())) {
//            viewHolder.description.setText(articleModel.getDescription());
//        }
//        if (!TextUtils.isEmpty(articleModel.getAuthor())) {
//            viewHolder.author.setText(articleModel.getAuthor());
//        }
        if (!TextUtils.isEmpty(articleModel.getPublishedAt())) {
            viewHolder.publishedAt.setText(articleModel.getPublishedAt());
        }

        viewHolder.date.setText("  -  "+ Utils.DateToTimeFormat(articleModel.getPublishedAt()));
        viewHolder.publishedAt.setText(Utils.DateFormat(articleModel.getPublishedAt()));

    }

    @Override
    public int getItemCount() {
        return articleArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView description;
        private TextView author;
        private TextView date;
        private TextView publishedAt;
        private ImageView imageView;
        private ProgressBar progressBar;

        ViewHolder(View view) {
            super(view);

            title = view.findViewById(R.id.news_title);
//            description = view.findViewById(R.id.news_description);
//            author = view.findViewById(R.id.news_author);
            date = view.findViewById(R.id.news_date);
            publishedAt = view.findViewById(R.id.news_publishedAt);
            imageView = view.findViewById(R.id.news_image);
//            progressBar = view.findViewById(R.id.progress_bar1);

        }
    }

}
