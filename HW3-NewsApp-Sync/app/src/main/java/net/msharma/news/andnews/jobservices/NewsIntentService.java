package net.msharma.news.andnews.jobservices;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import net.msharma.news.andnews.utils.NewsTaskUtils;

public class NewsIntentService extends IntentService {

    public NewsIntentService() {
        super("NewsIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String action = intent.getAction();
        NewsTaskUtils.executeTask(this, action);
    }

}