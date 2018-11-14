package com.hotmule.vkfeed.ui.feed

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.hotmule.vkfeed.R
import com.hotmule.vkfeed.data.network.model.feed.FeedResponse
import com.hotmule.vkfeed.data.network.model.feed.content.Item
import com.hotmule.vkfeed.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_feed.*
import javax.inject.Inject
import android.view.*
import com.hotmule.vkfeed.ui.post.PostActivity


class FeedFragment :
        BaseFragment(),
        FeedMvpView,
        FeedAdapter.OnPostClickListener,
        FeedAdapter.OnBottomReachedListener {

    @Inject
    lateinit var presenter: FeedMvpPresenter<FeedMvpView>

    private lateinit var postsAdapter: FeedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fragmentComponent?.inject(this)
        presenter.onAttach(this)

        postsAdapter = FeedAdapter(this, this)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolBar()
        setRecyclerView()

        if (savedInstanceState == null)
            presenter.onFeedPageOpened()
    }

    private fun setToolBar() {
        (activity as FeedActivity).setSupportActionBar(tbFeedBar)
        tbFeedBar.setOnClickListener { rvPosts.scrollToPosition(0) }
        setHasOptionsMenu(true)
    }

    private fun setRecyclerView() {
        rvPosts.layoutManager = LinearLayoutManager(activity)
        rvPosts.adapter = postsAdapter
    }

    override fun setSwipeToRefresh() {
        baseSwipeRefreshLayout = srlFeed
        srlFeed.setOnRefreshListener {
            presenter.onFeedPageOpened()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_log_out, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            R.id.btnLogOut -> presenter.onLogOutButtonClick()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun showPosts(posts: FeedResponse) {
        postsAdapter.setPosts(posts)
    }

    override fun onBottomReached() {
        presenter.onBottomReached()
    }

    override fun showMorePosts(posts: FeedResponse) {
        postsAdapter.setMorePosts(posts)
    }

    override fun onPostClick(item: Item, sourceName: String) {
        startActivity(PostActivity.buildIntent(context, item, sourceName))
    }

    override fun closePage() {
        activity?.finish()
    }

    override fun onDestroy() {
        presenter.onDetach()
        super.onDestroy()
    }
}
