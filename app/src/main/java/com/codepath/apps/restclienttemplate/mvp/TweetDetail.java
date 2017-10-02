package com.codepath.apps.restclienttemplate.mvp;

import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.adapters.TweetAdapter;
import com.codepath.apps.restclienttemplate.data.Media;
import com.codepath.apps.restclienttemplate.data.Tweet;
import com.codepath.apps.restclienttemplate.data.User;
import com.codepath.apps.restclienttemplate.utils.AppUtils;
import com.malmstein.fenster.controller.MediaFensterPlayerController;
import com.malmstein.fenster.view.FensterVideoView;

import org.parceler.Parcels;

import java.util.List;

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
    protected FensterVideoView video;
    private  MediaFensterPlayerController controller;


    public void setTweet(Tweet tweet) {
        this.mTweet = tweet;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.System.canWrite(getApplicationContext())) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS, Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, 200);
        } else {
            setContentView(R.layout.tweet_dialog);


            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            toolbar.setTitle("Twitter Detail");

            // add back arrow to toolbar
            if (getSupportActionBar() != null){
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
            }
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.twitter_color)));

            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                Object obj = extras.get(TWEET_RESPONSE);
                if (obj != null && obj instanceof Tweet) {
                    mTweet = (Tweet) obj;
                }
            }

            initView();
//        renderTweetView();
            postToUI();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void renderTweetView() {
        if (mTweet != null) {
            mTvTweetUserNameDetail.setText(mTweet.getUser().getName());
            Glide.with(this).load(mTweet.getUser().getProfileImageUrl()).into(mImgUserProfileDetail);
            mTvTweetScreenNameDetail.setText("@" + mTweet.getUser().getScreenName());
            mTvTweetUserNameDetail.setText(mTweet.getUser().getName());
            mTvTweetDescriptionDetail.setText(AppUtils.fromHtml(mTweet.getBody()));
            mTvTweetHoursDetail.setText(AppUtils.getTwitterDateFormat(mTweet.getCreatedAt()));
            mTvFavouriteCountDetail.setText(String.valueOf(mTweet.getFavCount()));


        }
    }

    private void postToUI() {
        List<Media> medias = mTweet.getEntity().getMedias();
        if (medias != null && !medias.isEmpty()) {
            Media media = medias.get(0);
            if (media.isPhoto()) {
                mImgUserTweetProfileDetail.setVisibility(View.VISIBLE);
                String mediaUrl = media.getMediaURL();
                Glide.with(this).load(mediaUrl).into(mImgUserTweetProfileDetail);
            }
        }

        /*if(mTweet.getExtendedEntity() != null
                && mTweet.getExtendedEntity().getVideoInfo() != null
                && mTweet.getExtendedEntity().getVideoInfo().getVariants() != null
                && mTweet.getExtendedEntity().getVideoInfo().getVariants().size() > 0
                && mTweet.getExtendedEntity().getVideoInfo().getVariants().get(0) != null) {
            if(mTweet.getExtendedEntity().getType().equals("video")) {
                video.setMediaController(controller);
                video.setVideo(mTweet.getExtendedEntity().getVideoInfo().getVariants().get(0).getUrl(), MediaFensterPlayerController.DEFAULT_VIDEO_START);
                video.start();
            }
            else if(mTweet.getExtendedEntity().getType().equals("photo")) {
                Glide.with(this).load(mTweet.getEntity().getMedias().get(0).getMediaURL()).centerCrop().into(mImgUserTweetProfileDetail);

            }
        }*/

        mTvTweetUserNameDetail.setText(mTweet.getUser().getName());
        mTvTweetScreenNameDetail.setText("@" + mTweet.getUser().getScreenName());
        mTvTweetDescriptionDetail.setText(AppUtils.fromHtml(mTweet.getBody()));
        mTvTweetHoursDetail.setText(AppUtils.getTwitterDateFormat(mTweet.getCreatedAt()));
        mTvFavouriteCountDetail.setText(String.valueOf(mTweet.getFavCount()));
        Glide.with(this).load(mTweet.getUser().getProfileImageUrl()).into(mImgUserProfileDetail);



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
        video = (FensterVideoView) findViewById(R.id.details_video);
        controller = (MediaFensterPlayerController)  findViewById(R.id.details_controller);
    }

}



