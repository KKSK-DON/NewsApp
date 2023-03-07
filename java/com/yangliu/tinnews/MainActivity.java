package com.yangliu.tinnews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.yangliu.tinnews.databinding.ActivityMainBinding;
import com.yangliu.tinnews.model.NewsResponse;
import com.yangliu.tinnews.network.NewsApi;
import com.yangliu.tinnews.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class MainActivity extends AppCompatActivity {

    private NavController navController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        // click on tab on BottomNavView can navigate
        NavigationUI.setupWithNavController(navView, navController);
        // can display label on action bar
        NavigationUI.setupActionBarWithNavController(this, navController);

        /*
         // new a task, make the call<NewsResponse>
        add task to queue
        while(true) { retrofit keep check the queue }
        if queue has `task`, retrofit do task: call endpoint, parse json , etc
        once retrofit finish the task
        callback.onRsponse(response)
        if (task success) newsResponseCallback.onResponse()
        else newsResponseCallback.onFailure()

         */

        //NewsApi api = RetrofitClient.newInstance().create(NewsApi.class);



    }

    @Override
    public boolean onSupportNavigateUp() {
        // can click back
        return navController.navigateUp();
    }
}