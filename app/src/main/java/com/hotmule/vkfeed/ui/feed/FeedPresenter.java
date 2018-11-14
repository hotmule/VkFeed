package com.hotmule.vkfeed.ui.feed;

import com.hotmule.vkfeed.data.DataManager;
import com.hotmule.vkfeed.utils.rx.SchedulerProvider;
import com.hotmule.vkfeed.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class FeedPresenter<V extends FeedMvpView> extends BasePresenter<V>
        implements FeedMvpPresenter<V> {

    @Inject
    public FeedPresenter(DataManager dataManager,
                         SchedulerProvider schedulerProvider,
                         CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    private String mStartFrom;

    @Override
    public void onFeedPageOpened() {
        getMvpView().showProgress();

        Disposable disposable = getDataManager().getPosts()
                .subscribeOn(getScheduler().io())
                .doAfterTerminate(() -> getMvpView().hideProgress())
                .observeOn(getScheduler().ui())
                .subscribe(
                        answer -> {
                            mStartFrom = answer.getResponse().getNextFrom();
                            getMvpView().showPosts(answer.getResponse());
                        },
                        error -> getMvpView().showError(error)
                );

        getCompositeDisposable().add(disposable);
    }

    @Override
    public void onBottomReached() {

        getMvpView().showProgress();

        Disposable disposable = getDataManager().getMorePosts(mStartFrom)
                .subscribeOn(getScheduler().io())
                .doAfterTerminate(() -> getMvpView().hideProgress())
                .observeOn(getScheduler().ui())
                .subscribe(
                        answer -> {
                            mStartFrom = answer.getResponse().getNextFrom();
                            getMvpView().showMorePosts(answer.getResponse());
                        },
                        error -> getMvpView().showError(error)
                );

        getCompositeDisposable().add(disposable);
    }

    @Override
    public void onLogOutButtonClick() {
        getDataManager().logOut();
        getMvpView().closePage();
    }
}
