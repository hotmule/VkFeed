package com.hotmule.vkfeed.ui.login;

import com.hotmule.vkfeed.ui.base.MvpPresenter;

public interface LoginMvpPresenter<V extends LoginMvpView> extends MvpPresenter<V> {

    void onLoginPageOpened();

    void onWebPageStarted(String url);

    void onWebPageLoaded(String url);

    void onReceivedError(Integer errorCode);
}
