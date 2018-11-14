package com.hotmule.vkfeed.ui.splash;

import com.hotmule.vkfeed.ui.base.MvpPresenter;

public interface SplashMvpPresenter<V extends SplashMvpView> extends MvpPresenter<V> {

    void onSplashPageOpened();
}
