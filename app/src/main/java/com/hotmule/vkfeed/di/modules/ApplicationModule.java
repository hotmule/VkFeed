package com.hotmule.vkfeed.di.modules;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.GsonBuilder;
import com.hotmule.vkfeed.data.AppDataManager;
import com.hotmule.vkfeed.data.DataManager;
import com.hotmule.vkfeed.data.network.VkApi;
import com.hotmule.vkfeed.data.network.AppNetworkHelper;
import com.hotmule.vkfeed.data.network.NetworkHelper;
import com.hotmule.vkfeed.data.preferences.AppPreferencesHelper;
import com.hotmule.vkfeed.data.preferences.PreferencesHelper;
import com.hotmule.vkfeed.utils.UrlUtils;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApplicationModule {

    private Context mContext;

    public ApplicationModule(Context context) {
        mContext = context;
    }

    @Provides
    SharedPreferences providePreferences() {
        return mContext.getSharedPreferences("TOKEN_PREF", Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager dataManager) {
        return dataManager;
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(AppPreferencesHelper preferencesHelper) {
        return preferencesHelper;
    }

    @Provides
    @Singleton
    NetworkHelper provideNetworkHelper(AppNetworkHelper networkHelper) {
        return networkHelper;
    }

    @Provides
    @Singleton
    VkApi provideApi() {
        return new Retrofit.Builder()
                .baseUrl("https://api.vk.com/method/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory
                        .createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory
                        .create(new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()))
                .build()
                .create(VkApi.class);
    }
}
