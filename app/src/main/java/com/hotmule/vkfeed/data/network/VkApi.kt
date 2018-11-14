package com.hotmule.vkfeed.data.network

import com.hotmule.vkfeed.data.network.model.feed.Feed
import com.hotmule.vkfeed.data.network.model.like.Like
import com.hotmule.vkfeed.data.network.model.post.Post
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface VkApi {

    @GET("newsfeed.get")
    fun getPosts(@Query("access_token") token: String?,
                 @Query("filters") filter: String,
                 @Query("start_from") startFrom: String,
                 @Query("count") count: Int,
                 @Query("v") version: String): Observable<Feed>

    @GET("wall.getById")
    fun getPostDetails(@Query("access_token") token: String?,
                       @Query("posts") ownerId_postId: String,
                       @Query("v") version: String): Observable<Post>

    @GET("likes.add")
    fun likePost(@Query("access_token") token: String?,
                 @Query("type") type: String,
                 @Query("owner_id") ownerId: Int,
                 @Query("item_id") itemId: Int,
                 @Query("v") version: String): Observable<Like>

    @GET("likes.delete")
    fun unlikePost(@Query("access_token") token: String?,
                   @Query("type") type: String,
                   @Query("owner_id") ownerId: Int,
                   @Query("item_id") itemId: Int,
                   @Query("v") version: String): Observable<Like>
}