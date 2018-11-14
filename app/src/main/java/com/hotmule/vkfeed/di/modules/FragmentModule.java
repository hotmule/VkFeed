package com.hotmule.vkfeed.di.modules;

import com.hotmule.vkfeed.utils.UrlUtils;
import com.hotmule.vkfeed.utils.rx.AppSchedulerProvider;
import com.hotmule.vkfeed.utils.rx.SchedulerProvider;
import com.hotmule.vkfeed.ui.feed.FeedMvpPresenter;
import com.hotmule.vkfeed.ui.feed.FeedPresenter;
import com.hotmule.vkfeed.ui.feed.FeedMvpView;
import com.hotmule.vkfeed.ui.login.LoginMvpPresenter;
import com.hotmule.vkfeed.ui.login.LoginMvpView;
import com.hotmule.vkfeed.ui.login.LoginPresenter;
import com.hotmule.vkfeed.ui.post.PostMvpPresenter;
import com.hotmule.vkfeed.ui.post.PostPresenter;
import com.hotmule.vkfeed.ui.post.PostMvpView;
import com.hotmule.vkfeed.ui.splash.SplashMvpPresenter;
import com.hotmule.vkfeed.ui.splash.SplashMvpView;
import com.hotmule.vkfeed.ui.splash.SplashPresenter;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class FragmentModule {

    @Provides
    UrlUtils provideUrlUtils() {
        return new UrlUtils();
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    SchedulerProvider schedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    SplashMvpPresenter<SplashMvpView> provideSplashPresenter(
            SplashPresenter<SplashMvpView> presenter) {
        return presenter;
    }

    @Provides
    LoginMvpPresenter<LoginMvpView> provideLoginPresenter(
            LoginPresenter<LoginMvpView> presenter) {
        return presenter;
    }

    @Provides
    FeedMvpPresenter<FeedMvpView> provideFeedPresenter(
            FeedPresenter<FeedMvpView> presenter) {
        return presenter;
    }

    @Provides
    PostMvpPresenter<PostMvpView> providePostPresenter(
            PostPresenter<PostMvpView> presenter) {
        return presenter;
    }
}