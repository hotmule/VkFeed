package com.hotmule.vkfeed.data.network.model.feed

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.hotmule.vkfeed.data.network.model.feed.content.Group
import com.hotmule.vkfeed.data.network.model.feed.content.Item
import com.hotmule.vkfeed.data.network.model.feed.content.Profile

class FeedResponse(@Expose var items: MutableList<Item>,
                   @Expose var profiles: MutableList<Profile>,
                   @Expose var groups: MutableList<Group>,
                   @Expose @SerializedName("next_from") var nextFrom: String)