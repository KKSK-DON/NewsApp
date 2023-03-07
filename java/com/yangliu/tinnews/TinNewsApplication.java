package com.yangliu.tinnews;

import android.app.Application;

import androidx.room.Room;

import com.yangliu.tinnews.database.TinNewsDatabase;

public class TinNewsApplication extends Application {

    private static TinNewsDatabase database;

    @Override
    public void onCreate(){
        super.onCreate();
        database = Room.databaseBuilder(this, TinNewsDatabase.class, "tinnews_db").build();
    }

    //get query
    public static TinNewsDatabase getDatabase(){
        return database;
    }
}
