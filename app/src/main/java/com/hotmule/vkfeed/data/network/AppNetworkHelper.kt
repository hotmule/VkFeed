package com.hotmule.vkfeed.data.network

import io.reactivex.Single
import java.net.InetSocketAddress
import java.net.Socket
import javax.inject.Inject
import javax.inject.Singleton

@Singleton class AppNetworkHelper
@Inject constructor(private val api: VkApi) : NetworkHelper {

    companion object {
        private const val AUTH_UTL = "https://oauth.vk.com/"
        private const val CLIENT_ID = 6439761
        private const val FILTER = "post"
        private const val POSTS_COUNT = 20
        private const val API_VERSION = "5.60"
    }

    override fun testInternetConnection(): Single<Boolean> {
        return Single.fromCallable {

            val timeoutMs = 1500
            val socket = Socket()
            val socketAddress = InetSocketAddress("8.8.8.8", 53)

            socket.connect(socketAddress, timeoutMs)
            socket.close()

            true
        }
    }

    override fun generateAuthUrl(): String
            = AUTH_UTL + "authorize" + "?" +
            "client_id=" + CLIENT_ID + "&" +
            "redirect_uri=" + AUTH_UTL + "blank.html" + "&" +
            "scope=" + "friends,wall" + "&" +
            "response_type=" + "token" + "&" +
            "v=" + API_VERSION

    override fun makeGetPostsRequest(token: String?, startFrom: String)
            = api.getPosts(token, FILTER, startFrom, POSTS_COUNT, API_VERSION)

    override fun makeGetPostDetailsRequest(token: String?, ownerId_postId: String)
            = api.getPostDetails(token, ownerId_postId, API_VERSION)

    override fun makeLikePostRequest(token: String?, ownerId: Int, itemId: Int)
            = api.likePost(token, FILTER, ownerId, itemId, API_VERSION)

    override fun makeUnlikePostRequest(token: String?, ownerId: Int, itemId: Int)
            = api.unlikePost(token, FILTER, ownerId, itemId, API_VERSION)

}