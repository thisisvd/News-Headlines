package com.vdcodeassociate.newsheadlines;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback;
import com.vdcodeassociate.newsheadlines.Adapter.MainArticleAdapter;
import com.vdcodeassociate.newsheadlines.Model.Articles;
import com.vdcodeassociate.newsheadlines.Model.ResponseModel;
import com.vdcodeassociate.newsheadlines.RestApi.APIClient;
import com.vdcodeassociate.newsheadlines.RestApi.APIInterface;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String API_KEY = "e6a7a646f7404e96a430f16b307c60e5";

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private MainArticleAdapter.RecyclerViewClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        configFade();

        setUpRetrofit();

    }

    private void configFade() {
        Fade fade = new Fade();
        View view = getWindow().getDecorView();
        fade.excludeTarget(view.findViewById(R.id.action_bar_container), true);
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);

        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);
    }

    private void setOnClickListener(List<Articles> articlesList) {
        listener = new MainArticleAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position, ImageView imageView) {
                Intent intent = new Intent(getApplicationContext(), ExpandNews.class);
                intent.putExtra("title", articlesList.get(position).getTitle());
                intent.putExtra("description", articlesList.get(position).getDescription());
                intent.putExtra("content", articlesList.get(position).getContent());
                intent.putExtra("author", articlesList.get(position).getAuthor());
                intent.putExtra("source", articlesList.get(position).getSource().getName());
                intent.putExtra("time", articlesList.get(position).getPublishedAt());
                intent.putExtra("publishedAt", articlesList.get(position).getPublishedAt());
                intent.putExtra("imageURL", articlesList.get(position).getUrlToImage());
                intent.putExtra("url", articlesList.get(position).getUrl());

                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        MainActivity.this,imageView, ViewCompat.getTransitionName(imageView));
                startActivity(intent, optionsCompat.toBundle());
            }
        };
    }

    private void setUpRetrofit() {

        final APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<ResponseModel> call = apiInterface.getLatestNews("in", API_KEY);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {

                if (response.body().getStatus().equals("ok")) {
                    List<Articles> articlesList = response.body().getArticles();
                    if (articlesList.size() > 0) {
                        setOnClickListener(articlesList);
                        final MainArticleAdapter mainArticleAdapter = new MainArticleAdapter(articlesList, getApplicationContext(), listener);
                        recyclerView.setAdapter(mainArticleAdapter);
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed To Load Data!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init() {

        // Shared Transform -

        recyclerView = findViewById(R.id.main_recycleView);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

    }
}