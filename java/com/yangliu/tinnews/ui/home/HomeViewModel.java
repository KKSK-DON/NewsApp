package com.yangliu.tinnews.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.yangliu.tinnews.model.Article;
import com.yangliu.tinnews.model.NewsResponse;
import com.yangliu.tinnews.repository.NewsRepository;

public class HomeViewModel extends ViewModel {

    private final NewsRepository repository;

    //country
    private final MutableLiveData<String> countryInput = new MutableLiveData<>();

    MutableLiveData<Boolean> addFavResult = new MutableLiveData<>();

    public HomeViewModel(NewsRepository repository) {
        this.repository = repository;
    }

    //event
    public void setCountryInput(String country) {
        countryInput.setValue(country);
    }

    //拿到country 做的get NewsResponse
    public LiveData<NewsResponse> getTopHeadlines() {
        // country -> NewsResponse
        // list.stream.map{item -> item + 1}
        return Transformations.switchMap(countryInput, repository::getTopHeadlines);

    }

    //改return type 然后实现改提示
//    public void setFavoriteArticleInput(Article article) {
//        repository.favoriteArticle(article);
//    }


    public void setFavoriteArticleInput(Article article) {
        repository.favoriteArticle(article, new NewsRepository.onResult<Boolean>() {
            @Override
            public void onSuccess(Boolean result) {
                addFavResult.setValue(result);
            }

            @Override
            public void onFailure() {
                addFavResult.setValue(false);
            }
        });
    }


}
