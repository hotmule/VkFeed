package com.hotmule.vkfeed.data.network.model.feed.content

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Group(@Expose var id: Int,
            @Expose var name: String,
            @Expose @SerializedName("photo_100") var photoLink: String)