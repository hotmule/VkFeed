package com.hotmule.vkfeed.data.network

import com.hotmule.vkfeed.data.network.model.feed.Feed
import com.hotmule.vkfeed.data.network.model.like.Like
import com.hotmule.vkfeed.data.network.model.post.Post
import io.reactivex.Observable
import io.reactivex.Single

interface NetworkHelper {

    fun testInternetConnection() : Single<Boolean>

    fun generateAuthUrl(): String

    fun makeGetPostsRequest(token: String?, startFrom: String): Observable<Feed>

    fun makeGetPostDetailsRequest(token: String?, ownerId_postId: String): Observable<Post>

    fun makeLikePostRequest(token: String?, ownerId: Int, itemId: Int): Observable<Like>

    fun makeUnlikePostRequest(token: String?, ownerId: Int, itemId: Int): Observable<Like>

}