package com.hotmule.vkfeed.data.network.model.feed.content

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Profile(@Expose var id: Int,
              @Expose @SerializedName("first_name") var firstName: String,
              @Expose @SerializedName("last_name") var lastName: String,
              @Expose @SerializedName("photo_100") var photoLink: String) {

    fun getFullName() = "$firstName $lastName"
}