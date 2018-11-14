package com.hotmule.vkfeed.ui.splash;

import com.hotmule.vkfeed.data.DataManager;
import com.hotmule.vkfeed.utils.rx.SchedulerProvider;
import com.hotmule.vkfeed.ui.base.BasePresenter;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class SplashPresenter<V extends SplashMvpView> extends BasePresenter<V>
        implements SplashMvpPresenter<V> {

    @Inject
    public SplashPresenter(DataManager dataManager,
                           SchedulerProvider schedulerProvider,
                           CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onSplashPageOpened() {

        Disposable disposable = getDataManager().isNetworkConnected()
                .subscribeOn(getScheduler().io())
                .doAfterTerminate(() -> getMvpView().hideProgress())
                .observeOn(getScheduler().ui())
                .subscribe(
                        isConnected -> openNeededPage(),
                        error -> getMvpView().showError(error)
                );

        getCompositeDisposable().add(disposable);
    }

    private void openNeededPage() {
        if (getDataManager().isUserLoggedIn())
            getMvpView().openFeedPage();
        else
            getMvpView().openLogInPage();
    }
}
