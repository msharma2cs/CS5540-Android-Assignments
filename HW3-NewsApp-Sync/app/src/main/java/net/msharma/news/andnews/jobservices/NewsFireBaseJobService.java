package net.msharma.news.andnews.jobservices;

import android.os.AsyncTask;
import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import net.msharma.news.andnews.repositories.NewsItemRepository;

public class NewsFireBaseJobService extends JobService {

    private AsyncTask mBackgroundTask;

    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        Log.d("NewsFireBaseJobService", "News onStartJob.");

        mBackgroundTask = new AsyncTask() {
            private NewsItemRepository newsItemRepository;

            @Override
            protected Object doInBackground(Object[] objects) {
                newsItemRepository = new NewsItemRepository(getApplication());
                newsItemRepository.syncDb();
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                jobFinished(jobParameters, false);
            }
        };

        mBackgroundTask.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        Log.d("NewsFireBaseJobService", "News onStopJob.");
        if (mBackgroundTask != null) mBackgroundTask.cancel(true);
        return true;
    }

}