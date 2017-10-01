package com.codepath.apps.restclienttemplate.mvp;

import android.app.DialogFragment;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

    protected TextView mTvTweetUserName;
    //    protected ImageView mImgUserProfile;
//    protected TextView mTvTweetScreenName;
//    protected TextView mTvTweetHours;
//    protected TextView mTvTweetDescription;
//    protected ImageView mImgUserTweetProfile;
//    protected TextView mTvImgDescription;
//    protected TextView mTvFavouriteCount;
    private Tweet mTweet;
    public TweetAdapter mTweetAdapter;


    public void setTweet(Tweet tweet) {
        this.mTweet = tweet;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tweet_dialog);


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
            mTvTweetUserName.setText(mTweet.getUser().getName());

        }
    }

    private void initView() {
        mTvTweetUserName = (TextView) findViewById(R.id.tv_tweet_user_name);
//        mImgUserProfile = (ImageView) view.findViewById(R.id.img_user_profile_pic);
//        mTvTweetScreenName = (TextView) view.findViewById(R.id.tv_tweet_user_screen_name);
//        mTvTweetHours = (TextView) view.findViewById(R.id.tv_tweet_addedd_timeline);
//        mTvTweetDescription = (TextView) view.findViewById(R.id.tv_tweet_description);
//        mImgUserTweetProfile = (ImageView) view.findViewById(R.id.img_user_tweet_profile);
//        mTvImgDescription = (TextView) view.findViewById(R.id.tv_img_description);
//        mTvFavouriteCount = (TextView) view.findViewById(R.id.tv_favourite_count);
    }

}



