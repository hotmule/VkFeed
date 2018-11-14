package com.hotmule.vkfeed.ui.post;

import com.hotmule.vkfeed.data.DataManager;
import com.hotmule.vkfeed.data.network.model.post.PostResponse;
import com.hotmule.vkfeed.data.network.model.post.content.Attachment;
import com.hotmule.vkfeed.data.network.model.post.content.Likes;
import com.hotmule.vkfeed.data.network.model.post.content.Photo;
import com.hotmule.vkfeed.utils.rx.SchedulerProvider;
import com.hotmule.vkfeed.ui.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class PostPresenter<V extends PostMvpView> extends BasePresenter<V>
        implements PostMvpPresenter<V>  {

    private int mPostId;
    private int mSourceId;
    private String mSourceName;
    private boolean mIsLikedBefore;

    @Inject
    public PostPresenter(DataManager dataManager,
                         SchedulerProvider schedulerProvider,
                         CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void savePostKeys(int postId, int sourceId, String sourceName) {
        mPostId = postId;
        mSourceId = sourceId;
        mSourceName = sourceName;
    }

    @Override
    public void onPostPageOpened() {
        
        getMvpView().setTitle(mSourceName);

        if (mPostId != 0 && mSourceId != 0) {

            getMvpView().showProgress();
            String sourceId_postId = String.format("%s_%s", mSourceId, mPostId);

            Disposable disposable = getDataManager().getPostDetails(sourceId_postId)
                    .subscribeOn(getScheduler().io())
                    .doAfterTerminate(() -> getMvpView().hideProgress())
                    .observeOn(getScheduler().ui())
                    .subscribe(
                            answer -> setPostDetails(answer.getResponse().get(0)),
                            error -> getMvpView().showError(error)
                    );

            getCompositeDisposable().add(disposable);
        }
    }

    private void setPostDetails(PostResponse response) {
        getMvpView().setText(response.getText());

        List<Attachment> attachments = response.getAttachments();
        if (attachments != null)
            setImages(attachments);

        Likes likes = response.getLikes();
        if (likes != null)
            setLikes(likes.getCount(), likes.getUserLikes() != 0);
    }

    private void setImages(List<Attachment> attachments) {
        ArrayList<String> imageUrls = new ArrayList<>();

        for (Attachment attachment : attachments) {
            if (attachment != null) {
                Photo photo = attachment.getPhoto();
                if (photo != null)
                    imageUrls.add(attachment.getPhoto().getPhotoLink());
            }
        }

        getMvpView().setImages(imageUrls);
    }

    private void setLikes(int likesCount, boolean isLikedBefore) {
        mIsLikedBefore = isLikedBefore;
        getMvpView().setLikeButtonView(mIsLikedBefore);
        getMvpView().setLikes(likesCount);
    }

    @Override
    public void onLikeButtonClick() {

        Disposable disposable;

        if (!mIsLikedBefore)
            disposable = getDataManager().likePost(mSourceId, mPostId)
                    .subscribeOn(getScheduler().io())
                    .observeOn(getScheduler().ui())
                    .subscribe(
                            answer -> setLikes(answer.getResponse().getLikes(), true),
                            error -> getMvpView().showError(error));
        else
            disposable = getDataManager().unlikePost(mSourceId, mPostId)
                    .subscribeOn(getScheduler().io())
                    .observeOn(getScheduler().ui())
                    .subscribe(
                            answer -> setLikes(answer.getResponse().getLikes(), false),
                            error -> getMvpView().showError(error));

        getCompositeDisposable().add(disposable);
    }
}
