package com.hotmule.vkfeed.ui.post

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import com.squareup.picasso.Picasso
import android.view.LayoutInflater
import com.hotmule.vkfeed.R
import com.squareup.picasso.Callback


class PostImagesAdapter(private var imageLinks: MutableList<String>) : PagerAdapter() {

    override fun isViewFromObject(view: View, any: Any) = view == any

    override fun getCount() = imageLinks.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val inflater = container.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val frameLayout = inflater.inflate(R.layout.item_post_image, null)
        val ivImage = frameLayout.findViewById<ImageView>(R.id.ivPostImage)
        val pbImageProgress = frameLayout.findViewById<ProgressBar>(R.id.pbPostImageProgress)

        Picasso.get().load(imageLinks[position])
                .into(ivImage, object : Callback {

                    override fun onSuccess() {
                        pbImageProgress.visibility = View.GONE
                    }

                    override fun onError(e: Exception?) {
                        pbImageProgress.visibility = View.GONE
                    }
                })

        (container as ViewPager).addView(frameLayout, 0)

        return frameLayout
    }

    override fun destroyItem(container: ViewGroup, position: Int, any: Any) {
        container.removeView(any as View)
    }

}