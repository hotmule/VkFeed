package com.hotmule.vkfeed.data.network.model.post.content

import com.google.gson.annotations.Expose

class Repost(@Expose var text: String?,
             @Expose var attachments: MutableList<Attachment>?)