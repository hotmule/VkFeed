package com.hotmule.vkfeed.di.components;

import com.hotmule.vkfeed.di.modules.FragmentModule;
import com.hotmule.vkfeed.di.FragmentScope;
import com.hotmule.vkfeed.ui.feed.FeedFragment;
import com.hotmule.vkfeed.ui.login.LoginFragment;
import com.hotmule.vkfeed.ui.post.PostFragment;
import com.hotmule.vkfeed.ui.splash.SplashFragment;

import dagger.Component;

@FragmentScope
@Component(dependencies = ApplicationComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    void inject(SplashFragment splashFragment);

    void inject(LoginFragment loginFragment);

    void inject(FeedFragment feedFragment);

    void inject(PostFragment postFragment);
}
