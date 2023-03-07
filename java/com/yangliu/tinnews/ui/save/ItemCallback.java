package com.yangliu.tinnews.ui.save;

import com.yangliu.tinnews.model.Article;

public interface ItemCallback {

    void onOpenDetails(Article article);
    void onRemoveFavorite(Article article);
}
