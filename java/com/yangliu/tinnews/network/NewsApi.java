package com.yangliu.tinnews.network;

import com.yangliu.tinnews.model.NewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApi {

    //https://newsapi.org/v2/top-headlines?country=us&category=business   + apikey
    @GET("top-headlines")
    Call<NewsResponse> getTopHeadlines(@Query("country") String country);

    //https://newsapi.org/v2/everything?q=tesla&pageSize=30 + apikey
    @GET("everything")
    Call<NewsResponse> getEverything(
            @Query("q") String query,
            @Query("pageSize") int pageSize
    );

}
