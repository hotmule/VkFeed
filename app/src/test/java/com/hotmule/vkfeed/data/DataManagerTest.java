package com.hotmule.vkfeed.data;

import com.hotmule.vkfeed.data.network.AppNetworkHelper;
import com.hotmule.vkfeed.data.network.NetworkHelper;
import com.hotmule.vkfeed.data.network.VkApi;
import com.hotmule.vkfeed.data.preferences.PreferencesHelper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Calendar;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DataManagerTest {

    @Mock
    private PreferencesHelper mPreferencesHelper;

    @Mock
    private VkApi vkApi;

    private DataManager mDataManager;

    private Calendar mTokenDeathDate;

    @Before
    public void setUp() {
        NetworkHelper networkHelper = new AppNetworkHelper(vkApi);
        mDataManager = new AppDataManager(networkHelper, mPreferencesHelper);

        mTokenDeathDate = Calendar.getInstance();
        when(mPreferencesHelper.getExpiresInDate()).thenReturn(mTokenDeathDate);
        when(mPreferencesHelper.getAccessToken()).thenReturn("testToken");
    }

    @Test
    public void isUserLoggedIn_FalseIfTokenIsNull() {
        when(mPreferencesHelper.getAccessToken()).thenReturn(null);
        assertThat(mDataManager.isUserLoggedIn()).isFalse();
    }

    @Test
    public void isUserLoggedIn_TrueIfTokenIsNotDeath() {
        mTokenDeathDate.add(Calendar.SECOND, 1000);
        assertThat(mDataManager.isUserLoggedIn()).isTrue();
    }

    @Test
    public void isUserLoggedIn_FalseIfTokenIsDeath() {
        mTokenDeathDate.add(Calendar.SECOND, -2000);
        assertThat(mDataManager.isUserLoggedIn()).isFalse();
    }

    @Test
    public void generateAuthUrl_returnsValidUrl() {
        String validAuthUrl = "https://oauth.vk.com/authorize?client_id=6439761&redirect_uri=" +
                "https://oauth.vk.com/blank.html&scope=friends,wall&response_type=token&v=5.60";

        assertThat(mDataManager.getAuthUrl()).isEqualTo(validAuthUrl);
    }

}
