package com.codepath.apps.restclienttemplate.utils;

import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.text.format.DateUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

/**
 * Created by hinaikhan on 9/30/17.
 */

public class AppUtils {

    private final static String TAG = AppUtils.class.getSimpleName();


    public static String getTwitterDateFormat(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            sf.setLenient(true);
        }

        String relativeDate = "";
        try {
            long dateMillis = 0;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                dateMillis = sf.parse(rawJsonDate).getTime();
            }
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html){
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html,Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }

}
