package net.msharma.news.andnews.utils;

import android.util.Log;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils {

    private static final String TAG = "DateTimeUtils";

    public static String formatDateFromString( String jsonDate) {
        String formattedDate = null;
        try {
            Date publishedAtDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(jsonDate);
            DateFormat dateFormat = new SimpleDateFormat("MMMM dd',' yyyy 'at' hh:mm:ss");
            formattedDate = dateFormat.format(publishedAtDate);
        } catch ( ParseException pe ) {
            Log.d(TAG, "Date string parse exception.");
            pe.printStackTrace();
        }
        return formattedDate;
    }

}