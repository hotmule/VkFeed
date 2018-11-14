package com.hotmule.vkfeed.utils;

import com.hotmule.vkfeed.data.network.model.Header;

import java.util.Calendar;

public class UrlUtils {

    public Header getTokenFromUrl(String url) {

        String[] parsedUrl = url.replace("https://oauth.vk.com/blank.html#", "")
                .replace("access_token=", "")
                .replace("expires_in=", "")
                .split("&");

        String token = parsedUrl[0];
        int secondsToExpire = Integer.parseInt(parsedUrl[1]);

        Calendar expiresIn = Calendar.getInstance();
        expiresIn.add(Calendar.SECOND, secondsToExpire / 2);

        return new Header(token, expiresIn);
    }
}
