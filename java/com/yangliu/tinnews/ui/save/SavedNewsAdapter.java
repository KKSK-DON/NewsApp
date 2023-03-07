package com.yangliu.tinnews.ui.save;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yangliu.tinnews.R;
import com.yangliu.tinnews.databinding.SavedNewsItemBinding;
import com.yangliu.tinnews.model.Article;

import java.util.ArrayList;
import java.util.List;

public class SavedNewsAdapter extends RecyclerView.Adapter<SavedNewsAdapter.SavedNewsViewHolder>{

    // 1. Supporting data:
    private List<Article> articles = new ArrayList<>();

    private ItemCallback itemCallback;

    public void setArticles(List<Article> newsList) {
        articles.clear();
        articles.addAll(newsList);
        // 只要有新方法就call 这个就可以更新了
        notifyDataSetChanged();
    }

    public void setItemCallback(ItemCallback itemCallback) {
        this.itemCallback = itemCallback;
    }


    // 3. Adapter overrides: onBindViewHolder, onCreateViewHolder, getItemCount
    @NonNull
    @Override
    public SavedNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.saved_news_item, parent, false);
        SavedNewsViewHolder savedNewsViewHolder = new SavedNewsViewHolder(itemView);
        Log.d("cardSwipeViewHolder", savedNewsViewHolder.toString() + "onCreate");
        return savedNewsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SavedNewsViewHolder holder, int position) {
        Log.d("SaveOnBindViewHolder", holder.toString()+"onBind");
        Article article = articles.get(position);
        holder.authorTextView.setText(article.author);
        holder.descriptionTextView.setText(article.description);
        holder.favoriteIcon.setOnClickListener(v -> itemCallback.onRemoveFavorite(article));
        holder.itemView.setOnClickListener(v -> itemCallback.onOpenDetails(article));
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    // 2. SavedNewsViewHolder:
    public static class SavedNewsViewHolder extends RecyclerView.ViewHolder {
        TextView authorTextView;
        TextView descriptionTextView;
        ImageView favoriteIcon;
        public SavedNewsViewHolder(@NonNull View itemView) {
            super(itemView);
            SavedNewsItemBinding binding = SavedNewsItemBinding.bind(itemView);
            authorTextView = binding.savedItemAuthorContent;
            descriptionTextView = binding.savedItemDescriptionContent;
            favoriteIcon = binding.savedItemFavoriteImageView;
        }
    }

}
