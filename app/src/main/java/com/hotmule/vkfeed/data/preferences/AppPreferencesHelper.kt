package com.hotmule.vkfeed.data.preferences

import android.content.SharedPreferences
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton class AppPreferencesHelper
@Inject constructor(private var preferences: SharedPreferences) : PreferencesHelper {

    companion object {
        private const val PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN"
        private const val PREF_KEY_EXPIRES_IN = "PREF_KEY_EXPIRES_IN"
    }

    override fun getAccessToken(): String? = preferences.getString(PREF_KEY_ACCESS_TOKEN, null)

    override fun setAccessToken(accessToken: String) {
        preferences.edit()
                .putString(PREF_KEY_ACCESS_TOKEN, accessToken)
                .apply()
    }

    override fun clearAccessToken() {
        preferences.edit()
                .remove(PREF_KEY_ACCESS_TOKEN)
                .apply()
    }

    override fun getExpiresInDate(): Calendar {
        val calendar = GregorianCalendar()
        calendar.timeInMillis = preferences.getLong(PREF_KEY_EXPIRES_IN, 0)
        return calendar
    }

    override fun setExpiresInDate(expiresIn: Calendar) {
        preferences.edit()
                .putLong(PREF_KEY_EXPIRES_IN, expiresIn.timeInMillis)
                .apply()
    }

    override fun clearExpiresInDate() {
        preferences.edit()
                .remove(PREF_KEY_EXPIRES_IN)
                .apply()
    }
}