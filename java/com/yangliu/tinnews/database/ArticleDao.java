package com.yangliu.tinnews.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.yangliu.tinnews.model.Article;

import java.util.List;

@Dao
public interface ArticleDao {

    //执行时间长， 需要用其他thread去做， 这里自己handel 多线程
    @Insert
    void saveArticle(Article article);

    @Query("SELECT * FROM article")
    LiveData<List<Article>> getAllArticles();

    @Delete
    void deleteArticle(Article article);

}
