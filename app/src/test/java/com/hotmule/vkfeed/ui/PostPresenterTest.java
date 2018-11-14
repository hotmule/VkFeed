package com.hotmule.vkfeed.ui;

import com.hotmule.vkfeed.data.DataManager;
import com.hotmule.vkfeed.ui.post.PostMvpView;
import com.hotmule.vkfeed.ui.post.PostPresenter;
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
public class PostPresenterTest {

    @Mock
    private PostMvpView mPostView;

    @Mock
    private DataManager mDataManager;

    private PostPresenter<PostMvpView> mPostPresenter;
    private TestScheduler mTestScheduler;

    @Before
    public void setUp() {
        mTestScheduler = new TestScheduler();

        mPostPresenter = new PostPresenter<>(
                mDataManager,
                new TestSchedulerProvider(mTestScheduler),
                new CompositeDisposable());

        mPostPresenter.onAttach(mPostView);
    }

    @Test
    public void onPostPageOpened_shownPostData() {

    }

    @Test
    public void onPostPageOpened_shownError() {

    }

    @Test
    public void onLikeButtonClick_postLiked() {

    }

    @Test
    public void onLikeButtonClick_postUnLiked() {

    }

    @Test
    public void onLikeButtonClick_shownError() {

    }

    @After
    public void tearDown() {
        mPostPresenter.onDetach();
    }
}
