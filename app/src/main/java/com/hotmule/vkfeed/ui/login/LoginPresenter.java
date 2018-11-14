package com.hotmule.vkfeed.ui.login;

import com.hotmule.vkfeed.data.DataManager;
import com.hotmule.vkfeed.utils.UrlUtils;
import com.hotmule.vkfeed.utils.rx.SchedulerProvider;
import com.hotmule.vkfeed.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class LoginPresenter<V extends LoginMvpView> extends BasePresenter<V>
        implements LoginMvpPresenter<V> {

    @Inject
    public LoginPresenter(DataManager dataManager,
                          SchedulerProvider schedulerProvider,
                          CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Inject
    UrlUtils mUrlUtils;

    @Override
    public void onLoginPageOpened() {
        getMvpView().loadUrlPage(getDataManager().getAuthUrl());
    }

    @Override
    public void onWebPageStarted(String url) {

        getMvpView().showProgress();

        if (url.contains("access_token") && url.contains("expires_in")) {
            getDataManager().saveApiHeader(mUrlUtils.getTokenFromUrl(url));
            getMvpView().hideProgress();
            getMvpView().openFeedPage();
        }
    }

    @Override
    public void onWebPageLoaded(String url) {
        getMvpView().hideProgress();
    }

    @Override
    public void onReceivedError(Integer errorCode) {
        getMvpView().hideProgress();
        getMvpView().showError(errorCode);
    }
}
