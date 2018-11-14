package com.hotmule.vkfeed.ui.post

import android.os.Bundle
import com.hotmule.vkfeed.R
import com.hotmule.vkfeed.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_post.*
import javax.inject.Inject
import android.graphics.Point
import android.support.constraint.ConstraintLayout
import android.view.*


class PostFragment : BaseFragment(), PostMvpView {

    @Inject
    lateinit var presenter: PostMvpPresenter<PostMvpView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fragmentComponent?.inject(this)
        presenter.onAttach(this)

        val postId = arguments?.get(PostActivity.POST_ID_ARG) as Int
        val sourceId = arguments?.get(PostActivity.SOURCE_ID_ARG) as Int
        val sourceName = arguments?.get(PostActivity.SOURCE_NAME) as String

        presenter.savePostKeys(postId, sourceId, sourceName)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolBar()
        setLikeButton()
        presenter.onPostPageOpened()
    }

    override fun setSwipeToRefresh() {
        baseSwipeRefreshLayout = srlPost
        srlPost.setOnRefreshListener {
            presenter.onPostPageOpened()
        }
    }

    private fun setToolBar() {
        (activity as PostActivity).setSupportActionBar(tbPostBar)
        (activity as PostActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as PostActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun setLikeButton() {
        fabLike.isEnabled = false
        fabLike.setOnClickListener { presenter.onLikeButtonClick() }
    }

    override fun setTitle(sourceName: String) {
        (activity as PostActivity).supportActionBar?.title = sourceName
    }

    override fun setText(text: String) {
        if (text.isEmpty())
            tvPostText.visibility = View.GONE
        else
            tvPostText.text = text
    }

    override fun setImages(imageLinks: MutableList<String>) {
        if (imageLinks.size > 0) {
            setSquareImagesViewPager()
            vpPostImages.adapter = PostImagesAdapter(imageLinks)

            if (imageLinks.size > 1)
                ciImagesIndicator.setViewPager(vpPostImages)
        }
    }

    private fun setSquareImagesViewPager() {
        val size = Point()
        activity?.windowManager?.defaultDisplay?.getSize(size)
        val width = size.x
        vpPostImages.layoutParams = ConstraintLayout.LayoutParams(width, width)
    }

    override fun setLikes(count: Int) {
        tvLikesCount.text = count.toString()
    }

    override fun setLikeButtonView(isLikedBefore: Boolean) {
        fabLike.isEnabled = true

        if (isLikedBefore)
            fabLike.setImageResource(R.drawable.ic_liked)
        else
            fabLike.setImageResource(R.drawable.ic_unliked)
    }

    override fun onDestroy() {
        presenter.onDetach()
        super.onDestroy()
    }
}