package com.hotmule.vkfeed.ui.splash

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.hotmule.vkfeed.R
import com.hotmule.vkfeed.ui.base.BaseFragment
import com.hotmule.vkfeed.ui.feed.FeedActivity
import com.hotmule.vkfeed.ui.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_splash.*
import javax.inject.Inject

class SplashFragment : BaseFragment(), SplashMvpView {

    @Inject
    lateinit var presenter: SplashMvpPresenter<SplashMvpView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fragmentComponent?.inject(this)
        presenter.onAttach(this)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null)
            presenter.onSplashPageOpened()
    }

    override fun setSwipeToRefresh() {
        srlSplash.setOnRefreshListener { presenter.onSplashPageOpened() }
        baseSwipeRefreshLayout = srlSplash
    }

    override fun openLogInPage() {
        startActivity(Intent(activity, LoginActivity::class.java))
        activity?.finish()
    }

    override fun openFeedPage() {
        startActivity(Intent(activity, FeedActivity::class.java))
        activity?.finish()
    }

    override fun onDestroy() {
        presenter.onDetach()
        super.onDestroy()
    }
}
