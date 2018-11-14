package com.hotmule.vkfeed.ui.base

interface MvpView {

    fun setSwipeToRefresh()

    fun showProgress()

    fun hideProgress()

    fun showError(throwable: Throwable)

    fun showError(errorCode: Int)
}