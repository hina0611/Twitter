package com.codepath.apps.restclienttemplate.mvp;

import com.codepath.apps.restclienttemplate.data.Tweet;
import com.codepath.apps.restclienttemplate.handlers.TimeLineHandlers;

/**
 * Created by hinaikhan on 9/28/17.
 */

public class TweetPostPresenter {

    PostTweetFragment mPostTweetFragment;


    public TweetPostPresenter(PostTweetFragment postTweetFragment) {
        this.mPostTweetFragment = postTweetFragment;
    }

    public void postTweet(String tweet){
        TimeLineHandlers handlers = new TimeLineHandlers();
        handlers.postTweet(this, tweet);
    }

    public void displayNewTweet(Tweet tweet) {
        mPostTweetFragment.displayNewTweet(tweet);
    }

}
