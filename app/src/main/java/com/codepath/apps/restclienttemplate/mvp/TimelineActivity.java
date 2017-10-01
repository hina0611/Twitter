package com.codepath.apps.restclienttemplate.mvp;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TwitterApp;
import com.codepath.apps.restclienttemplate.TwitterClient;
import com.codepath.apps.restclienttemplate.adapters.TweetAdapter;
import com.codepath.apps.restclienttemplate.data.Tweet;
import com.codepath.apps.restclienttemplate.utils.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;
import java.util.List;

import static android.media.CamcorderProfile.get;

public class TimelineActivity extends AppCompatActivity {

    private TwitterClient mTwitterClient;
    public TweetAdapter mTweetAdapter;
    private RecyclerView mRvTweet;
    private List<Tweet> mTweet;
    private EndlessRecyclerViewScrollListener scrollListener;
    private LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    private long maxId = 0;
    private FloatingActionButton fabPostTWeet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Twitter");

        fabPostTWeet = (FloatingActionButton) findViewById(R.id.fab_post_tweet);

        mTwitterClient = TwitterApp.getRestClient();
        mRvTweet = (RecyclerView) findViewById(R.id.rvTweeterClient);

        bindListeners();
    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    private void fetchTimeLineHandlerData(long maxId){
        TimeLinePresenter mTimeLinePresenter = new TimeLinePresenter(this);
        mTimeLinePresenter.fetchTimeLineHandlerData(maxId);
    }

    public void displayTweets(List<Tweet> tweets) {
        if (tweets != null && !tweets.isEmpty()) {
            mTweetAdapter.addAll(tweets);
            maxId = tweets.get(tweets.size()-1).getUid();
            mTweetAdapter.notifyDataSetChanged();
        }
    }

    public void displayNewTweet(Tweet tweet) {
        mTweetAdapter.insertTweetAt(tweet, 0);
        mTweetAdapter.notifyItemInserted(0);
        mRvTweet.scrollToPosition(0);
    }

    public void displayTweet(Tweet tweet) {
        Intent intent = new Intent(this, TweetDetail.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(TweetDetail.TWEET_RESPONSE, tweet);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void bindListeners() {
        fabPostTWeet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                composeTweet();
            }
        });

        mTweetAdapter = new TweetAdapter(this, new ArrayList<Tweet>());
        mRvTweet.setLayoutManager(layoutManager);
        mRvTweet.setAdapter(mTweetAdapter);

        mTweet = new ArrayList<Tweet>();
        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                fetchTimeLineHandlerData(maxId);
            }

        };

        // Adds the scroll listener to RecyclerView
        mRvTweet.addOnScrollListener(scrollListener);

        resetScrollListener();
    }

    private void resetScrollListener() {
        //First load
        maxId = 0;
        scrollListener.resetState();
        scrollListener.onLoadMore(0, mTweet.size(), mRvTweet);
    }

    private void composeTweet(){
        PostTweetFragment dialogFragment = new PostTweetFragment();
        dialogFragment.show(getFragmentManager(), "Show data");
    }

}
