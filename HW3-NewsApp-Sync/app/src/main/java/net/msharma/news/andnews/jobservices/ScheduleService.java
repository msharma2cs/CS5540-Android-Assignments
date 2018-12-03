/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.msharma.news.andnews.jobservices;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import java.util.concurrent.TimeUnit;

public class ScheduleService {

    private static final String TAG = "ScheduleService";

    private static final int SCHEDULE_INTERVAL_SECONDS = (int) (TimeUnit.SECONDS.toSeconds(10));;
    private static final int SYNC_FLEXTIME_SECONDS = SCHEDULE_INTERVAL_SECONDS;
    private static final String NEWS_REFRESH_JOB_TAG = "news_refresh_job_tag";
    private static boolean sInitialized = false;

    synchronized public static void scheduleRefreshJob(@NonNull final Context context) {

        Log.d(TAG, "News calling ScheduleService -> scheduleRefreshJob.");

        if(sInitialized) return;

        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

        Job newsRefreshJob = dispatcher.newJobBuilder()
                .setService(NewsFireBaseJobService.class)
                .setTag(NEWS_REFRESH_JOB_TAG)
                .setRecurring(true)
                .setLifetime(Lifetime.FOREVER)
                .setReplaceCurrent(true)
                .setTrigger(Trigger.executionWindow(SCHEDULE_INTERVAL_SECONDS,SCHEDULE_INTERVAL_SECONDS + SYNC_FLEXTIME_SECONDS))
                .build();

        dispatcher.schedule(newsRefreshJob);
        sInitialized = true;
    }

}