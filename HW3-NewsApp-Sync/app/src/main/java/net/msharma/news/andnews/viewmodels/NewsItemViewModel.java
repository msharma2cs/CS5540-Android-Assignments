package net.msharma.news.andnews.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import net.msharma.news.andnews.models.NewsItem;
import net.msharma.news.andnews.repositories.NewsItemRepository;
import java.util.List;

/**
 * News items ViewModel
 */
public class NewsItemViewModel extends AndroidViewModel {

    private NewsItemRepository newsItemRepository;
    private LiveData<List<NewsItem>> allNewsItems;

    public NewsItemViewModel(Application application) {
        super(application);
        newsItemRepository = new NewsItemRepository(application);
        allNewsItems = newsItemRepository.getAllNewsItems();
    }

    // To get all news items.
    public LiveData<List<NewsItem>> getAllNewsItem() {
        return allNewsItems;
    }

    // To sync the database.
    public void syncDb() {
        newsItemRepository.syncDb();
    }

    // To clear the database.
    public void clearDb() {
        newsItemRepository.clearDb();
    }

}