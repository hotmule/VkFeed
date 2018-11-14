package com.hotmule.vkfeed.ui.feed

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.hotmule.vkfeed.R

class FeedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        if (savedInstanceState == null)
            supportFragmentManager.beginTransaction()
                    .add(R.id.fl_fragment_holder, FeedFragment())
                    .commit()
    }
}