package com.hotmule.vkfeed.data

import com.hotmule.vkfeed.data.network.model.Header
import com.hotmule.vkfeed.data.network.model.feed.Feed
import com.hotmule.vkfeed.data.network.model.like.Like
import com.hotmule.vkfeed.data.network.model.post.Post
import io.reactivex.Observable
import io.reactivex.Single

interface DataManager {

    fun isUserLoggedIn() : Boolean

    fun logOut()

    fun isNetworkConnected() : Single<Boolean>

    fun getAuthUrl(): String

    fun saveApiHeader(header: Header)

    fun getPosts(): Observable<Feed>

    fun getMorePosts(startFrom: String): Observable<Feed>

    fun getPostDetails(ownerId_postId: String): Observable<Post>

    fun likePost(ownerId: Int, itemId: Int): Observable<Like>

    fun unlikePost(ownerId: Int, itemId: Int): Observable<Like>
}