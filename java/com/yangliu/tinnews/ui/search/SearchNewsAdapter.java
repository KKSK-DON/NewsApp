package com.yangliu.tinnews.ui.search;

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
import com.yangliu.tinnews.databinding.SearchNewsItemBinding;
import com.yangliu.tinnews.model.Article;

import java.util.ArrayList;
import java.util.List;

public class SearchNewsAdapter extends RecyclerView.Adapter<SearchNewsAdapter.SearchNewsViewHolder> {
    // 1. Supporting data:
    private List<Article> articles = new ArrayList<>();
    private ItemCallback itemCallback;

    public void setArticles(List<Article> newsList) {
        articles.clear();
        articles.addAll(newsList);
        //来自于继承的class
        notifyDataSetChanged();
    }
    public interface ItemCallback {
        void onOpenDetails(Article article);
    }

    public void setItemCallback(ItemCallback itemCallback) {
        this.itemCallback = itemCallback;
    }

    @NonNull
    @Override
    public SearchNewsAdapter.SearchNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 把search_news_item.xml 装换成view 放到viewHolder
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.search_news_item, parent, false);
        SearchNewsViewHolder searchNewsViewHolder = new SearchNewsViewHolder(itemView);
        Log.d("tim test", searchNewsViewHolder.toString() + "onCreate");
        return searchNewsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchNewsAdapter.SearchNewsViewHolder holder, int position) {
        // 把对应数据绑定到 created viewHolder
        Log.d("tim test", holder.toString()+"onBind");
        Article article = articles.get(position);
        holder.itemTitleTextView.setText(article.title);
        if (article.urlToImage != null) {
            Picasso.get()
                    .load(article.urlToImage)
                    .resize(200, 200)
                    .into(holder.itemImageView);
        }
        holder.itemView.setOnClickListener(v -> itemCallback.onOpenDetails(article));

    }

    @Override
    public int getItemCount() {
        return articles.size();
    }


    // 2. SearchNewsViewHolder:
    public static class SearchNewsViewHolder extends RecyclerView.ViewHolder {

        ImageView itemImageView;
        TextView itemTitleTextView;
        // itemView xml 生成出来的 + 裹的一层壳(RecyclerView.ViewHolder的meta data包括有没有显示 在哪里显示过的position)
        public SearchNewsViewHolder(@NonNull View itemView) {
            super(itemView);
            SearchNewsItemBinding binding = SearchNewsItemBinding.bind(itemView);
            itemImageView = binding.searchItemImage;
            itemTitleTextView = binding.searchItemTitle;
        }
    }
}
