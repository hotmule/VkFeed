package com.hotmule.vkfeed.ui.post

import com.hotmule.vkfeed.ui.base.MvpView

interface PostMvpView : MvpView {

    fun setTitle(sourceName: String)

    fun setText(text: String)

    fun setImages(imageLinks: MutableList<String>)

    fun setLikes(count: Int)

    fun setLikeButtonView(isLikedBefore: Boolean)
}
