package com.hotmule.vkfeed.di.components;

import com.hotmule.vkfeed.VkFeedApp;
import com.hotmule.vkfeed.data.DataManager;
import com.hotmule.vkfeed.di.modules.ApplicationModule;
import com.hotmule.vkfeed.utils.UrlUtils;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(VkFeedApp vkFeedApp);

    DataManager provideDataManager();
}
