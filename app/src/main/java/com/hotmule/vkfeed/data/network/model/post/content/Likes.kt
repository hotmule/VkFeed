package com.hotmule.vkfeed.data.network.model.post.content

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Likes(@Expose var count: Int,
            @Expose @SerializedName("user_likes") var userLikes: Int)