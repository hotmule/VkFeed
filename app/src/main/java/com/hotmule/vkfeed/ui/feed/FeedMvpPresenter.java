package com.hotmule.vkfeed.ui.feed;

import com.hotmule.vkfeed.ui.base.MvpPresenter;

public interface FeedMvpPresenter<V extends FeedMvpView> extends MvpPresenter<V> {

    void onFeedPageOpened();

    void onBottomReached();

    void onLogOutButtonClick();
}
