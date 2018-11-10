package net.msharma.news.andnews.models.dao.impl;

import android.arch.lifecycle.LiveData;
import net.msharma.news.andnews.models.NewsItem;
import net.msharma.news.andnews.models.dao.NewsItemDao;
import java.util.List;

public class NewsItemDaoImpl implements NewsItemDao {

    @Override
    public LiveData<List<NewsItem>> loadAllNewsItems() {
        return null;
    }

    @Override
    public LiveData<List<NewsItem>> insert(List<NewsItem> items) {
        return null;
    }

    @Override
    public LiveData<Boolean> clearAll() {
        return null;
    }

}