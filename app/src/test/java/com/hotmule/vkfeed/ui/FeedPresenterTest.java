package com.hotmule.vkfeed.ui;

import com.hotmule.vkfeed.data.DataManager;
import com.hotmule.vkfeed.data.network.model.feed.Feed;
import com.hotmule.vkfeed.data.network.model.feed.FeedResponse;
import com.hotmule.vkfeed.ui.feed.FeedMvpView;
import com.hotmule.vkfeed.ui.feed.FeedPresenter;
import com.hotmule.vkfeed.utils.TestSchedulerProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FeedPresenterTest {

    @Mock
    private FeedMvpView mFeedView;

    @Mock
    private DataManager mDataManager;

    private FeedPresenter<FeedMvpView> mFeedPresenter;
    private TestScheduler mTestScheduler;

    @Before
    public void setUp() {
        mTestScheduler = new TestScheduler();

        mFeedPresenter = new FeedPresenter<>(
                mDataManager,
                new TestSchedulerProvider(mTestScheduler),
                new CompositeDisposable());

        mFeedPresenter.onAttach(mFeedView);
    }

    @Test
    public void onFeedPageOpened_ShownLoadedPosts() {

        Feed feed = new Feed(
                new FeedResponse(
                        new ArrayList<>(),
                        new ArrayList<>(),
                        new ArrayList<>(),
                        "nextFrom"));

        Observable<Feed> answer = Observable.just(feed);

        when(mDataManager.getPosts()).thenReturn(answer);

        mFeedPresenter.onFeedPageOpened();
        mTestScheduler.triggerActions();

        verify(mFeedView).showProgress();
        verify(mFeedView).showPosts(feed.getResponse());
        verify(mFeedView).hideProgress();
    }

    @Test
    public void onFeedPageOpened_ShownError() {
        Throwable throwable = new Throwable();
        when(mDataManager.getPosts()).thenReturn(Observable.error(throwable));

        mFeedPresenter.onFeedPageOpened();
        mTestScheduler.triggerActions();

        verify(mFeedView).showProgress();
        verify(mFeedView).showError(throwable);
        verify(mFeedView).hideProgress();
    }

    @Test
    public void onBottomReached_ShownMorePosts() {

    }

    @Test
    public void onBottomReached_ShownError() {

    }

    @Test
    public void onLogOutButtonClick_ExitFromApp() {
        mFeedPresenter.onLogOutButtonClick();
        verify(mDataManager).logOut();
        verify(mFeedView).closePage();
    }

    @After
    public void tearDown() {
        mFeedPresenter.onDetach();
    }
}
