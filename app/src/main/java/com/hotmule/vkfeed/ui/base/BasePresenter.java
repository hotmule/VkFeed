package com.hotmule.vkfeed.ui.base;

import com.hotmule.vkfeed.data.DataManager;
import com.hotmule.vkfeed.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {

    private final DataManager mDataManager;
    private final SchedulerProvider mSchedulerProvider;
    private final CompositeDisposable mCompositeDisposable;

    private V mMvpView;

    @Inject
    public BasePresenter(DataManager dataManager,
                         SchedulerProvider schedulerProvider,
                         CompositeDisposable compositeDisposable) {

        mDataManager = dataManager;
        mSchedulerProvider = schedulerProvider;
        mCompositeDisposable = compositeDisposable;
    }

    @Override
    public void onAttach(V mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void onDetach() {
        mCompositeDisposable.dispose();
        mMvpView = null;
    }

    public V getMvpView() {
        return mMvpView;
    }

    protected DataManager getDataManager() {
        return mDataManager;
    }

    protected SchedulerProvider getScheduler() {
        return mSchedulerProvider;
    }

    protected CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }
}
