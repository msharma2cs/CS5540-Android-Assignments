package net.msharma.news.andnews.models.dao;

import android.arch.lifecycle.LiveData;
import net.msharma.news.andnews.models.NewsItem;
import java.util.List;

public interface NewsItemDao {

    LiveData<List<NewsItem>> loadAllNewsItems();

    LiveData<List<NewsItem>> insert(List<NewsItem> items);

    LiveData<Boolean> clearAll();

}
