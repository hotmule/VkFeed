package com.hotmule.vkfeed.ui.post

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.hotmule.vkfeed.R
import com.hotmule.vkfeed.data.network.model.feed.content.Item

class PostActivity : AppCompatActivity() {

    companion object {
        const val POST_ID_ARG = "postId"
        const val SOURCE_ID_ARG = "sourceId"
        const val SOURCE_NAME = "sourceName"

        fun buildIntent(context: Context?, item: Item, sourceName: String): Intent {
            val intent = Intent(context, PostActivity::class.java)
            intent.putExtra(POST_ID_ARG, item.postId)
            intent.putExtra(SOURCE_ID_ARG, item.sourceId)
            intent.putExtra(SOURCE_NAME, sourceName)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        if (savedInstanceState == null) {
            val postId = intent.getIntExtra(POST_ID_ARG, 0)
            val sourceId = intent.getIntExtra(SOURCE_ID_ARG, 0)
            val sourceName = intent.getStringExtra(SOURCE_NAME)

            val bundle = Bundle()
            bundle.putInt(POST_ID_ARG, postId)
            bundle.putInt(SOURCE_ID_ARG, sourceId)
            bundle.putString(SOURCE_NAME, sourceName)

            val postFragment = PostFragment()
            postFragment.arguments = bundle

            supportFragmentManager.beginTransaction()
                    .add(R.id.fl_fragment_holder, postFragment)
                    .commit()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}