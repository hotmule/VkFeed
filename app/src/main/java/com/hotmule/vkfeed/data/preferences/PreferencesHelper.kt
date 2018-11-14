package com.hotmule.vkfeed.data.preferences

import java.util.*

interface PreferencesHelper {

    fun getAccessToken(): String?

    fun setAccessToken(accessToken: String)

    fun clearAccessToken()

    fun getExpiresInDate(): Calendar

    fun setExpiresInDate(expiresIn: Calendar)

    fun clearExpiresInDate()
}