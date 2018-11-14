package com.hotmule.vkfeed.data

import com.hotmule.vkfeed.data.network.NetworkHelper
import com.hotmule.vkfeed.data.network.model.Header
import com.hotmule.vkfeed.data.preferences.PreferencesHelper
import io.reactivex.Single
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton class AppDataManager
@Inject constructor(private val networkHelper: NetworkHelper,
                    private val preferencesHelper: PreferencesHelper) : DataManager {

    override fun isUserLoggedIn(): Boolean {

        val todayDate = Calendar.getInstance()
        val tokenDeathDate = preferencesHelper.getExpiresInDate()

        if (tokenDeathDate.after(todayDate) && preferencesHelper.getAccessToken() != null)
            return true

        return false
    }

    override fun logOut() {
        preferencesHelper.clearAccessToken()
        preferencesHelper.clearExpiresInDate()
    }

    override fun saveApiHeader(header: Header) {
        preferencesHelper.setAccessToken(header.token)
        preferencesHelper.setExpiresInDate(header.expiresIn)
    }

    override fun isNetworkConnected(): Single<Boolean>
            = networkHelper.testInternetConnection().delay(500, TimeUnit.MILLISECONDS)

    override fun getAuthUrl(): String
            = networkHelper.generateAuthUrl()

    override fun getPosts()
            = networkHelper.makeGetPostsRequest(preferencesHelper.getAccessToken(), "0")

    override fun getMorePosts(startFrom: String)
            = networkHelper.makeGetPostsRequest(preferencesHelper.getAccessToken(), startFrom)

    override fun getPostDetails(ownerId_postId: String)
            = networkHelper.makeGetPostDetailsRequest(preferencesHelper.getAccessToken(), ownerId_postId)

    override fun likePost(ownerId: Int, itemId: Int)
            = networkHelper.makeLikePostRequest(preferencesHelper.getAccessToken(), ownerId, itemId)

    override fun unlikePost(ownerId: Int, itemId: Int)
            = networkHelper.makeUnlikePostRequest(preferencesHelper.getAccessToken(), ownerId, itemId)
}