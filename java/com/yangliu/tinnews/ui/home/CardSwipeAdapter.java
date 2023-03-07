package com.yangliu.tinnews.ui.home;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.yangliu.tinnews.R;
import com.yangliu.tinnews.databinding.SwipeNewsCardBinding;
import com.yangliu.tinnews.model.Article;

import java.util.ArrayList;
import java.util.List;

public class CardSwipeAdapter extends RecyclerView.Adapter<CardSwipeAdapter.CardSwipeViewHolder>{

    // 1. Supporting data:
    private List<Article> articles = new ArrayList<>();

    public void setArticles(List<Article> newsList) {
        articles.clear();
        articles.addAll(newsList);
        //来自于继承的class
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CardSwipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.swipe_news_card, parent, false);
        CardSwipeViewHolder cardSwipeViewHolder = new CardSwipeViewHolder(itemView);
        Log.d("cardSwipeViewHolder", cardSwipeViewHolder.toString() + "onCreate");
        return cardSwipeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CardSwipeViewHolder holder, int position) {
        // 把对应数据绑定到 created viewHolder
        Log.d("CardOnBindViewHolder", holder.toString()+"onBind");
        Article article = articles.get(position);
        holder.titleTextView.setText(article.title);
        holder.descriptionTextView.setText(article.description);
        if (article.urlToImage != null) {
            Picasso.get()
                    .load(article.urlToImage)
                    .into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    // 2. CardSwipeViewHolder:
    public static class CardSwipeViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView titleTextView;
        TextView descriptionTextView;

        public CardSwipeViewHolder(@NonNull View itemView) {
            super(itemView);
            SwipeNewsCardBinding binding = SwipeNewsCardBinding.bind(itemView);
            imageView = binding.swipeCardImageView;
            titleTextView = binding.swipeCardTitle;
            descriptionTextView = binding.swipeCardDescription;
        }
    }

    public List<Article> getArticles() {
        return articles;
    }


    // 3. Adapter overrides:
    // TODO


}
