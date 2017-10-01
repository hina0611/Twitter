package com.codepath.apps.restclienttemplate.mvp;

import android.app.DialogFragment;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.adapters.TweetAdapter;
import com.codepath.apps.restclienttemplate.data.Tweet;

/**
 * Created by hinaikhan on 9/30/17.
 */

public class TweetDetail extends AppCompatActivity {

    private final String TAG = TweetDetail.class.getSimpleName();

    public static final String TWEET_RESPONSE = "TweetResponse";

    protected TextView mTvTweetUserNameDetail;
    protected ImageView mImgUserProfileDetail;
    protected TextView mTvTweetScreenNameDetail;
    protected TextView mTvTweetHoursDetail;
    protected TextView mTvTweetDescriptionDetail;
    protected ImageView mImgUserTweetProfileDetail;
    protected TextView mTvImgDescriptionDetail;
    protected TextView mTvFavouriteCountDetail;
    private Tweet mTweet;
    public TweetAdapter mTweetAdapter;


    public void setTweet(Tweet tweet) {
        this.mTweet = tweet;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tweet_dialog);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Twitter Deatils");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Object obj = extras.get(TWEET_RESPONSE);
            if (obj != null && obj instanceof Tweet) {
                mTweet = (Tweet) obj;
            }
        }

        initView();
        renderTweetView();
    }

    private void renderTweetView() {
        if (mTweet != null) {
            mTvTweetUserNameDetail.setText(mTweet.getUser().getName());

        }
    }

    private void initView() {
        mTvTweetUserNameDetail = (TextView) findViewById(R.id.tv_tweet_user_name);
        mImgUserProfileDetail = (ImageView) findViewById(R.id.img_user_profile_pic);
        mTvTweetScreenNameDetail = (TextView) findViewById(R.id.tv_tweet_user_screen_name);
        mTvTweetHoursDetail = (TextView) findViewById(R.id.tv_tweet_addedd_timeline);
        mTvTweetDescriptionDetail = (TextView) findViewById(R.id.tv_tweet_description);
        mImgUserTweetProfileDetail = (ImageView) findViewById(R.id.img_user_tweet_profile);
        mTvImgDescriptionDetail = (TextView) findViewById(R.id.tv_img_description);
        mTvFavouriteCountDetail = (TextView) findViewById(R.id.tv_favourite_count);
    }

}



