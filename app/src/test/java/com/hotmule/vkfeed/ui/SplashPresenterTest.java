package com.hotmule.vkfeed.ui;

import com.hotmule.vkfeed.data.DataManager;
import com.hotmule.vkfeed.ui.splash.SplashMvpView;
import com.hotmule.vkfeed.ui.splash.SplashPresenter;
import com.hotmule.vkfeed.utils.TestSchedulerProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class SplashPresenterTest {

    @Mock
    private SplashMvpView mSplashView;

    @Mock
    private DataManager mDataManager;

    private SplashPresenter<SplashMvpView> mSplashPresenter;
    private TestScheduler mTestScheduler;

    @Before
    public void setUp() {
        mTestScheduler = new TestScheduler();

        mSplashPresenter = new SplashPresenter<>(
                mDataManager,
                new TestSchedulerProvider(mTestScheduler),
                new CompositeDisposable());

        mSplashPresenter.onAttach(mSplashView);
    }

    @Test
    public void onSplashOpened_IfNoNetworkConnectedErrorShown() {
        Throwable throwable = new Throwable();
        when(mDataManager.isNetworkConnected()).thenReturn(Single.error(throwable));

        mSplashPresenter.onSplashPageOpened();
        mTestScheduler.triggerActions();

        verify(mSplashView).hideProgress();
        verify(mSplashView).showError(throwable);
    }

    @Test
    public void onSplashOpened_IfUserNotAuthorizedOpensLogInPage() {
        when(mDataManager.isNetworkConnected()).thenReturn(Single.fromCallable(() -> true));
        when(mDataManager.isUserLoggedIn()).thenReturn(false);

        mSplashPresenter.onSplashPageOpened();
        mTestScheduler.triggerActions();

        verify(mSplashView).hideProgress();
        verify(mSplashView).openLogInPage();
    }

    @Test
    public void onSplashOpened_IfUserAuthorizedOpensFeedPage() {
        when(mDataManager.isNetworkConnected()).thenReturn(Single.fromCallable(() -> true));
        when(mDataManager.isUserLoggedIn()).thenReturn(true);

        mSplashPresenter.onSplashPageOpened();
        mTestScheduler.triggerActions();

        verify(mSplashView).hideProgress();
        verify(mSplashView).openFeedPage();
    }

    @After
    public void tearDown() {
        mSplashPresenter.onDetach();
    }
}
