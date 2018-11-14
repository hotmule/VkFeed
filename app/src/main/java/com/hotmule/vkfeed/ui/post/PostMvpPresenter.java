package com.hotmule.vkfeed.ui.post;

import com.hotmule.vkfeed.ui.base.MvpPresenter;

public interface PostMvpPresenter<V extends PostMvpView> extends MvpPresenter<V> {

    void savePostKeys(int postId, int sourceId, String sourceName);

    void onPostPageOpened();

    void onLikeButtonClick();
}
