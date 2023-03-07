package com.yangliu.tinnews.repository;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.yangliu.tinnews.TinNewsApplication;
import com.yangliu.tinnews.database.TinNewsDatabase;
import com.yangliu.tinnews.model.Article;
import com.yangliu.tinnews.model.NewsResponse;
import com.yangliu.tinnews.network.NewsApi;
import com.yangliu.tinnews.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {
    private final NewsApi newsApi;

    private final TinNewsDatabase database;


    public NewsRepository() {
        newsApi = RetrofitClient.newInstance().create(NewsApi.class);
        database = TinNewsApplication.getDatabase();
    }


    public LiveData<NewsResponse> getTopHeadlines (String country) {
        Call<NewsResponse> call = newsApi.getTopHeadlines(country);

        MutableLiveData<NewsResponse> topHeadlinesLiveData = new MutableLiveData<>();

        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful()) { //200 202
                    //Log.d("getTopHeadlines", response.body().toString());
                    topHeadlinesLiveData.setValue(response.body()); // = react's setState
                } else {
                    Log.d("NewsRepository", response.toString());
                    topHeadlinesLiveData.setValue(null);
                }
            }
            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                Log.d("NewsRepository", t.toString());
                topHeadlinesLiveData.setValue(null);
            }
        });

        // 类似与return interface
        return topHeadlinesLiveData;
    }

    public LiveData<NewsResponse> searchNews(String query) {
        MutableLiveData<NewsResponse> everyThingLiveData = new MutableLiveData<>();
        newsApi.getEverything(query, 40)
                .enqueue(new Callback<NewsResponse>() { // 开thread
                    @Override
                    public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                        if (response.isSuccessful()) {
                            everyThingLiveData.setValue(response.body());
                        } else {
                            everyThingLiveData.setValue(null);
                        }
                    }

                    @Override
                    public void onFailure(Call<NewsResponse> call, Throwable t) {
                        everyThingLiveData.setValue(null);
                    }
                });
        return everyThingLiveData;
    }

    public LiveData<Boolean> favoriteArticle(Article article, NewsRepository.onResult<Boolean> onResult) {
        MutableLiveData<Boolean> resultLiveData = new MutableLiveData<>();
        // FavoriteAsyncTask new thread execute doing doInBackground
        new FavoriteAsyncTask(database, resultLiveData, onResult).execute(article);
        return resultLiveData;
    }

    //ExecutorService
    private static class FavoriteAsyncTask extends AsyncTask<Article, Void, Boolean> {
        private final MutableLiveData<Boolean> liveData;
        private final TinNewsDatabase database;
        private onResult<Boolean> onResult;

        private FavoriteAsyncTask(TinNewsDatabase database, MutableLiveData<Boolean> liveData, onResult<Boolean> onResult) {
            this.database = database;
            this.liveData = liveData;
            this.onResult = onResult;
        }


        //background thread
        @Override
        protected Boolean doInBackground(Article... articles) {
            Article article = articles[0];
            try {
                // time consuming database operation so it is in background thread
                database.articleDao().saveArticle(article);
            } catch (Exception e) {
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            //liveData.setValue(success);
            if (success) {
                onResult.onSuccess(success);
            }else {
                onResult.onFailure();
            }
        }
    }

    public interface onResult<T> {
        void onSuccess(T result);

        void onFailure();
    }

    public LiveData<List<Article>> getAllSavedArticles() {
        return database.articleDao().getAllArticles();
    }

    public void deleteSavedArticle(Article article) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                database.articleDao().deleteArticle(article);
            }
        });
    }



}
