package net.msharma.news.andnews.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import net.msharma.news.andnews.models.NewsItem;
import java.util.List;

/**
 * News item DAO class with required methods.
 */
@Dao
public interface NewsItemDao {

    // To load/fetch all news items from database order by date, latest on top.
    @Query("SELECT * FROM news_item ORDER BY published_at DESC")
    LiveData<List<NewsItem>> loadAllNewsItems();

    // To insert a list of news item retrieved from api into database.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<NewsItem> items);

    // To delete all the news items from database.
    @Query("DELETE FROM news_item")
    void clearAll();

    // To get a count of news items in database - now only used for verification purposes.
    @Query("SELECT COUNT(id) FROM news_item")
    int getDataCount();

}