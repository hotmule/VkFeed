package com.hotmule.vkfeed.ui.login

import com.hotmule.vkfeed.ui.base.MvpView

interface LoginMvpView : MvpView {

    fun loadUrlPage(url: String)

    fun openFeedPage()

}