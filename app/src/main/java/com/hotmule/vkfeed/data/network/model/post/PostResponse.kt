package com.hotmule.vkfeed.data.network.model.post

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.hotmule.vkfeed.data.network.model.post.content.Attachment
import com.hotmule.vkfeed.data.network.model.post.content.Likes
import com.hotmule.vkfeed.data.network.model.post.content.Repost

class PostResponse(@Expose @SerializedName("copy_history") var reposts: MutableList<Repost>,
                   @Expose var attachments: MutableList<Attachment>?,
                   @Expose var text: String,
                   @Expose var likes: Likes?)