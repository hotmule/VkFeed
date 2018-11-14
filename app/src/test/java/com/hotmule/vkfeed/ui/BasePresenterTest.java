package com.hotmule.vkfeed.ui;

import com.hotmule.vkfeed.data.DataManager;
import com.hotmule.vkfeed.ui.base.BasePresenter;
import com.hotmule.vkfeed.ui.base.MvpView;
import com.hotmule.vkfeed.utils.rx.SchedulerProvider;
import com.hotmule.vkfeed.utils.TestSchedulerProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.TestScheduler;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class BasePresenterTest {

    private BasePresenter<MvpView> mBasePresenter;

    @Mock
    private MvpView mMvpView;

    @Mock
    private DataManager mDataManager;

    @Before
    public void setUp() {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        SchedulerProvider schedulerProvider = new TestSchedulerProvider(new TestScheduler());

        mBasePresenter = new BasePresenter<>(
                mDataManager,
                schedulerProvider,
                compositeDisposable
        );

        mBasePresenter.onAttach(mMvpView);
    }

    @Test
    public void onAttach_MvpViewAttached() {
        assertThat(mBasePresenter.getMvpView())
                .isNotNull()
                .isEqualTo(mMvpView);
    }

    @Test
    public void onDetach_MvpViewDetached() {
        mBasePresenter.onDetach();
        assertThat(mBasePresenter.getMvpView()).isNull();
    }

    @After
    public void tearDown() {
        mBasePresenter.onDetach();
    }
}
