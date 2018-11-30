package net.msharma.news.andnews.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import net.msharma.news.andnews.models.NewsItem;
import java.util.List;

@Dao
public interface NewsItemDao {

    @Query("SELECT * FROM news_item ORDER BY published_at DESC")
    LiveData<List<NewsItem>> loadAllNewsItems();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<NewsItem> items);

    @Query("DELETE FROM news_item")
    void clearAll();

}