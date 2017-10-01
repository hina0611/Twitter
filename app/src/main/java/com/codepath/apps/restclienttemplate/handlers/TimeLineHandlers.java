package com.codepath.apps.restclienttemplate.handlers;

import android.util.Log;

import com.codepath.apps.restclienttemplate.TwitterApp;
import com.codepath.apps.restclienttemplate.TwitterClient;
import com.codepath.apps.restclienttemplate.adapters.TweetAdapter;
import com.codepath.apps.restclienttemplate.data.Tweet;
import com.codepath.apps.restclienttemplate.mvp.TimeLinePresenter;
import com.codepath.apps.restclienttemplate.mvp.TweetPostPresenter;
import com.github.scribejava.apis.TwitterApi;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by hinaikhan on 9/28/17.
 */

public class TimeLineHandlers {

    private TwitterClient mTwitterClient;
    private static final String TAG = TimeLineHandlers.class.getSimpleName();

    public void fetchTwitterClientData(TimeLinePresenter presenter, long maxId){
        mTwitterClient = TwitterApp.getRestClient();
        populateTimeLineData(presenter, maxId);

    }

    private void populateTimeLineData(final TimeLinePresenter presenter, long maxId){
        mTwitterClient.getHomeTimeLine(maxId, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d(TAG, "onSuccess Array response" + response.toString());
                List<Tweet> tweets = new ArrayList<>();

                for(int i= 0; i < response.length(); i++){
                    try {
                        Tweet mTweet = Tweet.fromJson(response.getJSONObject(i));
                        tweets.add(mTweet);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                presenter.displayTweets(tweets);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d(TAG, "onSuccess object response" + response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d(TAG, "onFailure object response" + errorResponse.toString());
                throwable.printStackTrace();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d(TAG, "onFailure Array response" + errorResponse.toString());
                throwable.printStackTrace();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d(TAG, "headers response" + responseString.toString());
                throwable.printStackTrace();
            }
        });
    }

    public void postTweet(final TweetPostPresenter presenter, String tweet){
        mTwitterClient = TwitterApp.getRestClient();
        mTwitterClient.postTweet(tweet, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d(TAG, "onSuccess Array response" + response.toString());
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d(TAG, "onSuccess object response" + response.toString());
                Tweet tweet = Tweet.fromJson(response);
                presenter.displayNewTweet(tweet);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d(TAG, "onFailure object response" + errorResponse.toString());
                throwable.printStackTrace();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d(TAG, "onFailure Array response" + errorResponse.toString());
                throwable.printStackTrace();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d(TAG, "headers response" + responseString.toString());
                throwable.printStackTrace();
            }
        });
    }
}
