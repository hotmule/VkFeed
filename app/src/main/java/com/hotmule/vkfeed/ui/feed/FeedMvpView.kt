package com.hotmule.vkfeed.ui.feed

import com.hotmule.vkfeed.data.network.model.feed.FeedResponse
import com.hotmule.vkfeed.ui.base.MvpView

interface FeedMvpView : MvpView {

    fun showPosts(posts: FeedResponse)

    fun showMorePosts(posts: FeedResponse)

    fun closePage()
}
