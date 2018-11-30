package net.msharma.news.andnews.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import net.msharma.news.andnews.models.NewsItem;
import net.msharma.news.andnews.repositories.NewsItemRepository;
import java.util.List;

public class NewsItemViewModel extends AndroidViewModel {

    private NewsItemRepository newsItemRepository;
    private LiveData<List<NewsItem>> allNewsItems;

    public NewsItemViewModel(Application application) {
        super(application);
        newsItemRepository = new NewsItemRepository(application);
        allNewsItems = newsItemRepository.getAllNewsItems();
    }

    public LiveData<List<NewsItem>> getAllNewsItem() {
        return allNewsItems;
    }

    public void syncDb() {
        newsItemRepository.syncDb();
    }

}