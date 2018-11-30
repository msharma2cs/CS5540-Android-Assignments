package net.msharma.news.andnews.utils;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import net.msharma.news.andnews.dao.NewsItemDao;
import net.msharma.news.andnews.models.NewsItem;

@Database(entities = {NewsItem.class}, version = 1)
public abstract class NewsRoomDatabase extends RoomDatabase {

    public abstract NewsItemDao newsItemDao();

    private static volatile NewsRoomDatabase INSTANCE;

    public static NewsRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (NewsRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), NewsRoomDatabase.class, "news_database").build();
                }
            }
        }
        return INSTANCE;
    }

}