package net.msharma.news.andnews.utils;

import android.content.Context;
import net.msharma.news.andnews.dao.NewsItemDao;
import net.msharma.news.andnews.models.NewsItem;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {NewsItem.class}, version = 1)
public abstract class NewsRoomDatabase extends RoomDatabase {

    public abstract NewsItemDao newsItemDao();

    private static volatile NewsRoomDatabase INSTANCE;

    public static NewsRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (NewsRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), NewsRoomDatabase.class, "news_database")
                                    .build();
                }
            }
        }
        return INSTANCE;
    }

}