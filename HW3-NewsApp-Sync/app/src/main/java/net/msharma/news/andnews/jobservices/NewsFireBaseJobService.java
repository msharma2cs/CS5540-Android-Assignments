package net.msharma.news.andnews.jobservices;

import android.os.AsyncTask;
import android.util.Log;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import net.msharma.news.andnews.repositories.NewsItemRepository;
import net.msharma.news.andnews.utils.NewsTaskUtils;

public class NewsFireBaseJobService extends JobService {

    private static final String TAG = "NewsFireBaseJobService";

    private AsyncTask mBackgroundTask;

    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        Log.d(TAG, "News onStartJob.");

        mBackgroundTask = new AsyncTask() {
            private NewsItemRepository newsItemRepository;

            @Override
            protected Object doInBackground(Object[] objects) {
                Log.d(TAG, "News AsyncTask doInBackground.");
                NewsTaskUtils.executeTask(NewsFireBaseJobService.this, NewsTaskUtils.ACTION_NEWS_REFRESH_SYNC);
                newsItemRepository = new NewsItemRepository(getApplication());
                newsItemRepository.syncDb();
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                Log.d(TAG, "News AsyncTask onPostExecute.");
                jobFinished(jobParameters, false);
            }
        };

        mBackgroundTask.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        Log.d(TAG, "News onStopJob.");
        if (mBackgroundTask != null) mBackgroundTask.cancel(true);
        return true;
    }

}