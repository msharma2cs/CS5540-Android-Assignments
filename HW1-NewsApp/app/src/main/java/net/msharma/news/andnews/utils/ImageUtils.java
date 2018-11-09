package net.msharma.news.andnews.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageUtils {

    private static final String TAG = "ImageUtils";

    public static Drawable loadDrawableImageFromWeb(String url) {
        Drawable imageDrawable = null;
        try {
            Log.d(TAG, "Loading Image drawable : ");
            InputStream is = new URL(url).openStream();
            imageDrawable = Drawable.createFromStream(is, "src name");
            Log.d(TAG, "Image drawable : " + imageDrawable.toString() );
        } catch (Exception e) {
            Log.d(TAG, "loadDrawableImageFromWeb : Exception while loading drawable image from url.");
            e.printStackTrace();
        }
        return imageDrawable;
    }

    public static Bitmap loadBitmapImageFromWeb(String url) {
        Bitmap imageBitmap = null;
        try {
            InputStream in = new URL(url).openStream();
            imageBitmap = BitmapFactory.decodeStream(in);
        } catch (MalformedURLException me) {
            Log.d(TAG, "loadBitmapImageFromWeb : Exception while loading bitmap image from url.");
            me.printStackTrace();
        } catch (IOException ioe) {
            Log.d(TAG, "loadBitmapImageFromWeb : Exception while loading bitmap image from url.");
            ioe.printStackTrace();
        }
        return imageBitmap;
    }

}