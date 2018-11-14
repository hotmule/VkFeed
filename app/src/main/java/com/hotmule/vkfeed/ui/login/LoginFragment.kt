package com.hotmule.vkfeed.ui.login

import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import com.hotmule.vkfeed.R
import com.hotmule.vkfeed.ui.base.BaseFragment
import com.hotmule.vkfeed.ui.feed.FeedActivity
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject


class LoginFragment : BaseFragment(), LoginMvpView {

    @Inject
    lateinit var presenter: LoginMvpPresenter<LoginMvpView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fragmentComponent?.inject(this)
        presenter.onAttach(this)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupWebView()
        presenter.onLoginPageOpened()
    }

    private fun setupWebView() {

        wvLogin.settings.javaScriptEnabled
        wvLogin.settings.defaultTextEncodingName = "utf-8"
        wvLogin.webViewClient = object : WebViewClient() {

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                presenter.onWebPageStarted(url)
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                presenter.onWebPageLoaded(url)
                super.onPageFinished(view, url)
            }

            override fun onReceivedError(view: WebView?,
                                         request: WebResourceRequest?,
                                         error: WebResourceError?) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    presenter.onReceivedError(error?.errorCode)
                else
                    presenter.onReceivedError(WebViewClient.ERROR_UNKNOWN)
            }
        }
    }

    override fun setSwipeToRefresh() {
        srlLogin.setOnRefreshListener { presenter.onAttach(this) }
        baseSwipeRefreshLayout = srlLogin
    }

    override fun loadUrlPage(url: String) {
        wvLogin.loadUrl(url)
    }

    override fun openFeedPage() {
        wvLogin.visibility = View.GONE
        startActivity(Intent(activity, FeedActivity::class.java))
        activity?.finish()
    }

    override fun onDestroy() {
        presenter.onDetach()
        super.onDestroy()
    }
}