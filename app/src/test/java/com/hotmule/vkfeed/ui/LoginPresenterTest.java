package com.hotmule.vkfeed.ui;

import com.hotmule.vkfeed.data.DataManager;
import com.hotmule.vkfeed.ui.login.LoginMvpView;
import com.hotmule.vkfeed.ui.login.LoginPresenter;
import com.hotmule.vkfeed.utils.TestSchedulerProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.TestScheduler;

@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterTest {

    @Mock
    private LoginMvpView mLoginView;

    @Mock
    private DataManager mDataManager;

    private LoginPresenter<LoginMvpView> mLoginPresenter;

    @Before
    public void setUp() {
        mLoginPresenter = new LoginPresenter<>(
                mDataManager,
                new TestSchedulerProvider(new TestScheduler()),
                new CompositeDisposable()
        );
    }

    @Test
    public void onWebPageStarted_OpensTokenUrl() {

    }

    @After
    public void tearDown() {

    }
}
