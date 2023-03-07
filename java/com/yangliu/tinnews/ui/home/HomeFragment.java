package com.yangliu.tinnews.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.yangliu.tinnews.databinding.FragmentHomeBinding;
import com.yangliu.tinnews.model.Article;
import com.yangliu.tinnews.repository.NewsRepository;
import com.yangliu.tinnews.repository.NewsViewModelFactory;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.Duration;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting;


public class HomeFragment extends Fragment implements CardStackListener {

    private HomeViewModel viewModel;
    private FragmentHomeBinding binding;
    private CardStackLayoutManager layoutManager;
    private CardSwipeAdapter swipeAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_home, container, false);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        swipeAdapter = new CardSwipeAdapter();
        layoutManager = new CardStackLayoutManager(requireContext(), this);
        layoutManager.setStackFrom(StackFrom.Top); // 对layout 属性的布局
        binding.homeCardStackView.setLayoutManager(layoutManager);
        binding.homeCardStackView.setAdapter(swipeAdapter);
        // Handle like unlike button clicks
        binding.homeLikeButton.setOnClickListener(v -> swipeCard(Direction.Right));
        binding.homeUnlikeButton.setOnClickListener(new View.OnClickListener() {
            //和priorityqueue 的 comparator 一样
            @Override
            public void onClick(View v) {
                swipeCard(Direction.Left);
            }
        });


        NewsRepository newsRepository = new NewsRepository();

        // viewmodel = viewmodelprovider.get(HomeViewModel.class)
        //NewsViewModelFactory 自己做的小工厂
        viewModel = new ViewModelProvider(this, new NewsViewModelFactory(newsRepository)).get(HomeViewModel.class);
        viewModel.setCountryInput("us");

        //observe (lifecycleOwner -> onStop onPause 之类的lifecycle  ,
        //         observer -> 只要返回的livedata发生变化， 就会被观察到并执行相对应的方法)
        viewModel.getTopHeadlines().observe(getViewLifecycleOwner(), newsResponse -> {
            if (newsResponse != null) {
                Log.d("HomeFragment", newsResponse.toString());
                swipeAdapter.setArticles(newsResponse.articles);
            }
        });

        viewModel.addFavResult.observe(getViewLifecycleOwner(), setFar ->{
            //update UI
            Log.d("HomeFragment", "setFarResult : " + setFar);
        });

    }

    private void swipeCard(Direction direction) {
        SwipeAnimationSetting setting = new SwipeAnimationSetting.Builder()
                .setDirection(direction)
                .setDuration(Duration.Normal.duration)
                .build();
        //写成field 让他们share起来
        layoutManager.setSwipeAnimationSetting(setting);
        binding.homeCardStackView.swipe();
    }

    @Override
    public void onCardDragging(Direction direction, float v) {

    }

    @Override
    public void onCardSwiped(Direction direction) {
        if (direction == Direction.Left) {
            Log.d("CardStackView", "Unliked " + layoutManager.getTopPosition());
        } else if (direction == Direction.Right) {
            Log.d("CardStackView", "Liked "  + layoutManager.getTopPosition());
            // 1. get swiped article
            Article article = swipeAdapter.getArticles().get(layoutManager.getTopPosition() -1);
            // 2. repo.favorite(article ) to save to db
            viewModel.setFavoriteArticleInput(article);
        }
    }

    @Override
    public void onCardRewound() {

    }

    @Override
    public void onCardCanceled() {

    }

    @Override
    public void onCardAppeared(View view, int i) {

    }

    @Override
    public void onCardDisappeared(View view, int i) {

    }
}
