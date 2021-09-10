package com.vdcodeassociate.newsheadlines;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.vdcodeassociate.newsheadlines.Adapter.MainArticleAdapter;
import com.vdcodeassociate.newsheadlines.Model.Articles;
import com.vdcodeassociate.newsheadlines.Model.ResponseModel;
import com.vdcodeassociate.newsheadlines.RestApi.APIClient;
import com.vdcodeassociate.newsheadlines.RestApi.APIInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String API_KEY = "e6a7a646f7404e96a430f16b307c60e5";

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private MainArticleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        setUpRetrofit();

    }

    private void setUpRetrofit() {

        final APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<ResponseModel> call = apiInterface.getLatestNews("techcrunch",API_KEY);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {

                if(response.body().getStatus().equals("ok")){
                    List<Articles> articlesList = response.body().getArticles();
                    if(articlesList.size() > 0){
                        final MainArticleAdapter mainArticleAdapter = new MainArticleAdapter(articlesList,getApplicationContext());
                        recyclerView.setAdapter(mainArticleAdapter);
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Failed To Load Data!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init(){

        recyclerView = findViewById(R.id.main_recycleView);
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

    }
}