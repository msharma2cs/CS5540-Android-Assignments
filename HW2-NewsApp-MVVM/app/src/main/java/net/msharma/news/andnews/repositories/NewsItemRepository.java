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

public class NewsItemRepository {

    private NewsItemDao mNewsItemDao;
    private LiveData<List<NewsItem>> mAllNewsItems;

    public NewsItemRepository(Application application){
        NewsRoomDatabase newsDb = NewsRoomDatabase.getDatabase(application.getApplicationContext());
        mNewsItemDao = newsDb.newsItemDao();
        mAllNewsItems = mNewsItemDao.loadAllNewsItems();
    }

    LiveData<List<NewsItem>> getmAllNewsItems() {
        return this.mAllNewsItems;
    }

    public LiveData<List<NewsItem>> getAll() {
        new LoadAllNewsItemAsyncTask(mNewsItemDao).execute();
        return mAllNewsItems;
    }

    public class LoadAllNewsItemAsyncTask extends AsyncTask<Void, Void, LiveData<List<NewsItem>>> {
        private NewsItemDao mAsyncTaskNewsItemDao;

        LoadAllNewsItemAsyncTask(NewsItemDao mAsyncTaskNewsItemDao) {
            this.mAsyncTaskNewsItemDao = mAsyncTaskNewsItemDao;
        }

        @Override
        protected LiveData<List<NewsItem>> doInBackground(Void... voids) {
            return mAsyncTaskNewsItemDao.loadAllNewsItems();
        }
    }

    public void syncDb() {
        new SyncDbNewsItemAsyncTask(mNewsItemDao).execute();
    }

    public class SyncDbNewsItemAsyncTask extends AsyncTask<Void, Void, Void> {
        private NewsItemDao mAsyncTaskNewsItemDao;

        SyncDbNewsItemAsyncTask(NewsItemDao mAsyncTaskNewsItemDao) {
            this.mAsyncTaskNewsItemDao = mAsyncTaskNewsItemDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            // clear the database.
            mAsyncTaskNewsItemDao.clearAll();

            // Network call to get from API.
            URL newsSearchUrl = NetworkUtils.buildURL();
            String newsSearchResult = null;
            try {
                newsSearchResult = NetworkUtils.getResponseFromHttpUrl(newsSearchUrl);
            } catch (IOException e) {
                Log.d("NewsItemRepository", "Main activity news query async IOException.");
                e.printStackTrace();
            }

            // parsing into list of news objects.
            ArrayList<NewsItem> newsItems = JsonUtils.parseNews(newsSearchResult);

            // persisting in database.
            mAsyncTaskNewsItemDao.insert(newsItems);

            return null;
        }
    }

}