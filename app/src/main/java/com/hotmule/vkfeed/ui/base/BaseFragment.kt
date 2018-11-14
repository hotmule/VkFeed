package com.hotmule.vkfeed.ui.base

import android.os.Build
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.system.ErrnoException
import android.view.Gravity
import android.view.View
import android.webkit.WebViewClient
import android.widget.FrameLayout
import com.hotmule.vkfeed.R
import com.hotmule.vkfeed.VkFeedApp
import com.hotmule.vkfeed.di.components.DaggerFragmentComponent
import com.hotmule.vkfeed.di.components.FragmentComponent
import com.hotmule.vkfeed.di.modules.FragmentModule
import java.net.ConnectException
import java.net.UnknownHostException

abstract class BaseFragment : Fragment(), MvpView {

    var fragmentComponent: FragmentComponent? = null
    var baseSwipeRefreshLayout: SwipeRefreshLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fragmentComponent = DaggerFragmentComponent.builder()
                .fragmentModule(FragmentModule())
                .applicationComponent((activity?.application as VkFeedApp).applicationComponent)
                .build()

        retainInstance = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setSwipeToRefresh()
    }

    override fun showProgress() {
        baseSwipeRefreshLayout?.post { baseSwipeRefreshLayout?.isRefreshing = true }
    }

    override fun hideProgress() {
        baseSwipeRefreshLayout?.post { baseSwipeRefreshLayout?.isRefreshing = false }
    }

    override fun showError(throwable: Throwable) {

        var messageId = R.string.unexpected_error

        if (throwable is UnknownHostException || throwable is ConnectException)
            messageId = R.string.network_connection_error

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            if (throwable is ErrnoException)
                messageId = R.string.network_connection_error

        showSnackBar(messageId)
    }

    override fun showError(errorCode: Int) {

        val messageId: Int = when (errorCode) {
            WebViewClient.ERROR_HOST_LOOKUP -> R.string.network_connection_error
            else -> R.string.unexpected_error
        }

        showSnackBar(messageId)
    }

    private fun showSnackBar(messageId: Int) {

        if (view != null) {
            val snackBar = Snackbar.make(view!!, messageId, Snackbar.LENGTH_SHORT)

            if (view!!.id == R.id.clPost) {
                val params = snackBar.view.layoutParams as CoordinatorLayout.LayoutParams

                params.anchorId = R.id.babPostBar
                params.anchorGravity = Gravity.TOP
                params.gravity = Gravity.TOP

                params.setMargins(
                        params.leftMargin,
                        params.topMargin,
                        params.rightMargin,
                        params.bottomMargin + 230
                )

                snackBar.view.layoutParams = params
            }

            snackBar.show()
        }
    }
}