package com.hotmule.vkfeed

import android.app.Application

import com.hotmule.vkfeed.di.components.ApplicationComponent
import com.hotmule.vkfeed.di.components.DaggerApplicationComponent
import com.hotmule.vkfeed.di.modules.ApplicationModule

class VkFeedApp : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()

        applicationComponent.inject(this)
    }
}
