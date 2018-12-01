package net.msharma.news.andnews;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import net.msharma.news.andnews.models.NewsItem;
import net.msharma.news.andnews.viewmodels.NewsItemViewModel;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ProgressBar mProgressBar;
    private NewsAdapter mAdapter;
    private ArrayList<NewsItem> news = new ArrayList<>();
    private NewsItemViewModel mNewsItemViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = (ProgressBar) findViewById(R.id.progress);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.news_recyclerview);
        mAdapter = new NewsAdapter(this, news);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mNewsItemViewModel = ViewModelProviders.of(this).get(NewsItemViewModel.class);
        mNewsItemViewModel.getAllNewsItem().observe(this, new Observer<List<NewsItem>>() {
            @Override
            public void onChanged(@Nullable final List<NewsItem> newsItems) {
                mProgressBar.setVisibility(View.GONE);
                // Update the cached copy of the news in the adapter.
                mAdapter.setmNews(newsItems);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mProgressBar.setVisibility(View.VISIBLE);
        int itemThatWasClickedId = item.getItemId();

        // If refresh was clicked, clear database and fetch new news items.
        if (itemThatWasClickedId == R.id.action_search) {
            mNewsItemViewModel.syncDb();
            return true;
        } else if (itemThatWasClickedId == R.id.action_clear) { // If clear was clicked, clear database only
            mNewsItemViewModel.clearDb();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.get_news, menu);
        return true;
    }

}