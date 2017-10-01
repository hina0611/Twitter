package com.codepath.apps.restclienttemplate.mvp;

import com.codepath.apps.restclienttemplate.data.Tweet;
import com.codepath.apps.restclienttemplate.handlers.TimeLineHandlers;

import java.util.List;

/**
 * Created by hinaikhan on 9/28/17.
 */

public class TimeLinePresenter {

    TimelineActivity mTimelineActivity;
    TweetDetail mTweetDetail;

    public TimeLinePresenter(TimelineActivity mTimelineActivity) {
        this.mTimelineActivity = mTimelineActivity;
    }

    public TimeLinePresenter(TweetDetail mTweetDetail){
        this.mTweetDetail = mTweetDetail;
    }

    public void fetchTimeLineHandlerData(long maxId){
        TimeLineHandlers handlers = new TimeLineHandlers();
        handlers.fetchTwitterClientData(this, maxId);
    }

    public void displayTweets(List<Tweet> tweets) {
        if (tweets != null) {
            mTimelineActivity.displayTweets(tweets);
        }
    }
}
