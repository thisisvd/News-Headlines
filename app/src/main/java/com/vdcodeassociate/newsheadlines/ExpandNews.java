package com.vdcodeassociate.newsheadlines;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.transition.MaterialContainerTransform;
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback;
import com.vdcodeassociate.newsheadlines.Model.Articles;

import java.util.ArrayList;
import java.util.List;

public class ExpandNews extends AppCompatActivity {

    private TextView title;
    private TextView description;
    private TextView content;
    private TextView author;
    private TextView time;
    private TextView publishedAt;
    private ImageView imageView;
    private TextView source;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_news);

        init();

        configFade();

        Intent intent = getIntent();

        title.setText(intent.getStringExtra("title"));
        description.setText(intent.getStringExtra("description"));
        content.setText(intent.getStringExtra("content"));
        author.setText("Author - " + intent.getStringExtra("author"));
        source.setText("Source - " + intent.getStringExtra("source"));
        time.setText(intent.getStringExtra("time"));
        publishedAt.setText(intent.getStringExtra("publishedAt"));
        url = intent.getStringExtra("url");

        // Putting Image in ImageView
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.centerCrop();

        Glide.with(getApplicationContext())
                .load(intent.getStringExtra("imageURL"))
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, com.bumptech.glide.load.DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }

                })
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);

    }

    private void configFade() {
        Fade fade = new Fade();
        View view = getWindow().getDecorView();
        fade.excludeTarget(view.findViewById(R.id.action_bar_container),true);
        fade.excludeTarget(android.R.id.statusBarBackground,true);
        fade.excludeTarget(android.R.id.navigationBarBackground,true);

        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);
    }

    private void init() {
        title = findViewById(R.id.expand_title);
        description = findViewById(R.id.expand_description);
        content = findViewById(R.id.expand_content);
        author = findViewById(R.id.expand_author);
        source = findViewById(R.id.expand_source);
        time = findViewById(R.id.expand_time);
        publishedAt = findViewById(R.id.expand_publishedAt);
        imageView = findViewById(R.id.expand_image);
    }

    public void goToWebView(View view) {

        Intent intent = new Intent(getApplicationContext(),WebviewActivity.class);
        intent.putExtra("url",url);
        startActivity(intent);

    }

}