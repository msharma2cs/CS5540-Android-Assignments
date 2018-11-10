package net.msharma.news.andnews.utils;

import android.net.Uri;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    private static final String TAG = "NetworkUtils";

    final static String BASE_URL = "https://newsapi.org/v1/articles";
    final static String PARAM_SOURCE = "source";
    final static String PARAM_SOURCE_VALUE = "the-next-web";
    final static String PARAM_SORT = "sortBy";
    final static String PARAM_SORT_VALUE = "latest";
    final static String PARAM_KEY = "apiKey";
    final static String API_KEY = "1da34870901a4857b901b4deeaec1c95";

    public static URL buildURL() {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_SOURCE, PARAM_SOURCE_VALUE)
                .appendQueryParameter(PARAM_SORT, PARAM_SORT_VALUE)
                .appendQueryParameter(PARAM_KEY, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            Log.d(TAG, "Malformed URL built exception.");
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();

            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } catch ( Exception e ) {
            Log.d(TAG, "Network connection HTTP exception.");
            e.printStackTrace();
            return null;
        } finally {
            urlConnection.disconnect();
        }
    }

}