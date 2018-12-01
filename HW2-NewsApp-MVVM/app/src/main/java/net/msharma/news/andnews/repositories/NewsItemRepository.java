package net.msharma.news.andnews.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;
import net.msharma.news.andnews.dao.NewsItemDao;
import net.msharma.news.andnews.models.NewsItem;
import net.msharma.news.andnews.utils.JsonUtils;
import net.msharma.news.andnews.utils.NetworkUtils;
import net.msharma.news.andnews.utils.NewsRoomDatabase;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * News items repository class.
 */
public class NewsItemRepository {

    private static final String TAG = "NewsItemRepository";

    private NewsItemDao newsItemDao;
    private LiveData<List<NewsItem>> allNewsItems;

    public NewsItemRepository(Application application) {
        NewsRoomDatabase db = NewsRoomDatabase.getDatabase(application.getApplicationContext());
        newsItemDao = db.newsItemDao();
        allNewsItems = newsItemDao.loadAllNewsItems();
    }

    // To get all news items from database.
    public LiveData<List<NewsItem>> getAllNewsItems() {
        allNewsItems = newsItemDao.loadAllNewsItems();
        return allNewsItems;
    }

    /**
     * AsyncTask subclass to clear the database - clearing all existing news items
     */
    //    private static class LoadFromDbNewsItemAsyncTask extends AsyncTask<Void, Void, LiveData<List<NewsItem>>> {
    //        private NewsItemDao mAsyncTaskNewsItemDao;
    //
    //        LoadFromDbNewsItemAsyncTask(NewsItemDao mAsyncTaskNewsItemDao) {
    //            this.mAsyncTaskNewsItemDao = mAsyncTaskNewsItemDao;
    //        }
    //
    //        @Override
    //        protected LiveData<List<NewsItem>> doInBackground(Void... voids) {
    //            Log.d(TAG, "...doing in background ...for LoadFromDbNewsItemAsyncTask");
    //            Log.d(TAG, "Fetching in LoadFromDbNewsItemAsyncTask : Items count currently in db = " + mAsyncTaskNewsItemDao.getDataCount());
    //            return mAsyncTaskNewsItemDao.loadAllNewsItems();
    //        }
    //    }

    // To sync the database using async task.
    public void syncDb() {
        new SyncDbNewsItemAsyncTask(newsItemDao).execute();
    }

    /**
     * AsyncTask subclass to sync the database - clearing all existing news items, fetching from api and persisting in database.
     */
    private static class SyncDbNewsItemAsyncTask extends AsyncTask<Void, Void, Void> {
        private NewsItemDao mAsyncTaskNewsItemDao;

        SyncDbNewsItemAsyncTask(NewsItemDao mAsyncTaskNewsItemDao) {
            this.mAsyncTaskNewsItemDao = mAsyncTaskNewsItemDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d(TAG, "...doing in background ...for SyncDbNewsItemAsyncTask");
            Log.d(TAG, "Before clear : Items count currently in db = " + mAsyncTaskNewsItemDao.getDataCount());

            // clear the database.
            mAsyncTaskNewsItemDao.clearAll();

            Log.d(TAG, "After  clear : Items count currently in db = " + mAsyncTaskNewsItemDao.getDataCount());

            // Network call to get from API.
            URL newsSearchUrl = NetworkUtils.buildURL();
            String newsSearchResult = null;
            try {
                newsSearchResult = NetworkUtils.getResponseFromHttpUrl(newsSearchUrl);
            } catch (IOException e) {
                Log.d(TAG, "SyncDbNewsItemAsyncTask news query async IOException.");
                e.printStackTrace();
            }

            // parsing into list of news objects.
            ArrayList<NewsItem> newsItems = JsonUtils.parseNews(newsSearchResult);

            // persisting in database.
            mAsyncTaskNewsItemDao.insert(newsItems);

            Log.d(TAG, "After insert : Items count currently in db = " + mAsyncTaskNewsItemDao.getDataCount());
            return null;
        }
    }

    // To clear the database using async task.
    public void clearDb() {
        new ClearDbNewsItemAsyncTask(newsItemDao).execute();
    }

    /**
     * AsyncTask subclass to clear the database - clearing all existing news items
     */
    private static class ClearDbNewsItemAsyncTask extends AsyncTask<Void, Void, Void> {
        private NewsItemDao mAsyncTaskNewsItemDao;

        ClearDbNewsItemAsyncTask(NewsItemDao mAsyncTaskNewsItemDao) {
            this.mAsyncTaskNewsItemDao = mAsyncTaskNewsItemDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d(TAG, "...doing in background ...for ClearDbNewsItemAsyncTask");
            Log.d(TAG, "Before clear in ClearDbNewsItemAsyncTask : Items count currently in db = " + mAsyncTaskNewsItemDao.getDataCount());

            // clear the database.
            mAsyncTaskNewsItemDao.clearAll();

            Log.d(TAG, "After  clear in ClearDbNewsItemAsyncTask : Items count currently in db = " + mAsyncTaskNewsItemDao.getDataCount());
            return null;
        }
    }

}