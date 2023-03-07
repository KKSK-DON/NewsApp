package com.yangliu.tinnews.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.yangliu.tinnews.model.Article;

@Database(entities = {Article.class}, version = 1, exportSchema = false)
public abstract class TinNewsDatabase extends RoomDatabase {
    public abstract ArticleDao articleDao();

}
