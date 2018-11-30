package net.msharma.news.andnews.dao;

import android.arch.lifecycle.LiveData;
import net.msharma.news.andnews.models.NewsItem;
import java.util.List;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface NewsItemDao {

    @Query("SELECT * FROM news_item")
    LiveData<List<NewsItem>> loadAllNewsItems();

    @Insert
    void insert(List<NewsItem> items);

    @Query("DELETE FROM news_item")
    void clearAll();

}