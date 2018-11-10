package net.msharma.news.andnews;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import net.msharma.news.andnews.models.NewsItem;
import net.msharma.news.andnews.utils.JsonUtils;
import net.msharma.news.andnews.utils.NetworkUtils;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;
    private NewsAdapter mAdapter;
    private String newsResults;
    private ArrayList<NewsItem> news = new ArrayList<>();
    private static final String SEARCH_QUERY_URL_EXTRA = "searchQuery";
    private static final String SEARCH_QUERY_RESULTS = "searchResults";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = (ProgressBar) findViewById(R.id.progress);
        mRecyclerView = (RecyclerView)findViewById(R.id.news_recyclerview);
        mAdapter = new NewsAdapter(this, news);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        if ( savedInstanceState != null && savedInstanceState.containsKey(SEARCH_QUERY_RESULTS) ) {
            String searchResults = savedInstanceState.getString(SEARCH_QUERY_RESULTS);
            populateRecyclerView(searchResults);
        } else {
            getAllNews();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SEARCH_QUERY_RESULTS, newsResults);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mProgressBar.setVisibility(View.GONE);
    }

    public void populateRecyclerView(String searchResults){
        news = JsonUtils.parseNews(searchResults);
        mAdapter.mNews.addAll(news);
        mAdapter.notifyDataSetChanged();
    }

    private void getAllNews() {
        URL url = NetworkUtils.buildURL();
        Bundle bundle = new Bundle();
        bundle.putString(SEARCH_QUERY_URL_EXTRA, url.toString());
        new NewsQueryTask().execute(url);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_search) {
            getAllNews();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.get_news, menu);
        return true;
    }

    public class NewsQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... params) {
            Log.d(TAG, "Calling NewsQueryTask doInBackground");
            URL newsSearchUrl = params[0];
            String newsSearchResult = null;
            try {
                newsSearchResult = NetworkUtils.getResponseFromHttpUrl(newsSearchUrl);
                newsResults = newsSearchResult;
            } catch (IOException e) {
                Log.d(TAG, "Main activity news query async IOException.");
                e.printStackTrace();
            }
            return newsSearchResult;
        }

        @Override
        protected void onPostExecute(String newsResult) {
            mProgressBar.setVisibility(View.GONE);

            if (newsResult != null && !newsResult.equals("")) {
                populateRecyclerView(newsResult);
            }
        }
    }

}