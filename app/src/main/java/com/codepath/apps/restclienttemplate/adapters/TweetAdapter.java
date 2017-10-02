package com.codepath.apps.restclienttemplate.adapters;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.data.Entity;
import com.codepath.apps.restclienttemplate.data.Media;
import com.codepath.apps.restclienttemplate.data.Tweet;
import com.codepath.apps.restclienttemplate.data.User;
import com.codepath.apps.restclienttemplate.mvp.TimelineActivity;
import com.codepath.apps.restclienttemplate.mvp.TweetDetail;
import com.codepath.apps.restclienttemplate.utils.AppUtils;
import com.malmstein.fenster.controller.FensterPlayerControllerVisibilityListener;
import com.malmstein.fenster.controller.MediaFensterPlayerController;
import com.malmstein.fenster.controller.SimpleMediaFensterPlayerController;
import com.malmstein.fenster.view.FensterVideoView;

import java.util.ArrayList;
import java.util.List;

import static android.media.CamcorderProfile.get;
import static com.bumptech.glide.Glide.with;

/**
 * Created by hinaikhan on 9/29/17.
 */

public class TweetAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements FensterPlayerControllerVisibilityListener{

    //private Context mContext;
    private TimelineActivity mTimelineActivity;
    private List<Tweet> mTweets;
    private List<Long> mTweetIds;
    private LayoutInflater mLayoutInflater;


    private final int IMAGE = 0;
    private final int VIDEO = 1;




    public TweetAdapter(TimelineActivity timelineActivity, ArrayList<Tweet> mTweets) {
        this.mTimelineActivity = timelineActivity;
        this.mTweets = mTweets;
    }

    public void addAll(List<Tweet> tweets) {
        if (mTweets == null) {
            mTweets = new ArrayList<Tweet>();
        }

        if (mTweetIds == null) {
            mTweetIds = new ArrayList<Long>();
        }

        for (Tweet tweet : tweets) {
            if (mTweetIds.indexOf(tweet.getUid()) == -1) {
                mTweetIds.add(tweet.getUid());
                mTweets.add(tweet);
            } else {
                Log.d(" ", "Found duplicate tweet: " + tweet.getUid());
            }

        }
    }

    public void insertTweetAt(Tweet tweet, int index) {
        mTweets.add(index, tweet);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mLayoutInflater = LayoutInflater.from(mTimelineActivity);
        RecyclerView.ViewHolder mViewHolder = null;


        switch (viewType){
            case IMAGE: {
                View view = mLayoutInflater.inflate(R.layout.activity_tweet, parent, false);
                mViewHolder = new ViewHolder(view);
                break;

            }

            case VIDEO:{
                View view = mLayoutInflater.inflate(R.layout.activity_tweet_video_layout, parent,false);
                mViewHolder = new ViewHolderVideo(view);
            }
        }

        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Tweet tweet = mTweets.get(position); // get data as per position
        User user = tweet.getUser();
        Entity entity = tweet.getEntity();

        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.mTvTweetUserName.setText(tweet.getUser().getName());
            viewHolder.mTvTweetScreenName.setText("@" + tweet.getUser().getScreenName());
            viewHolder.mTvTweetDescription.setText(AppUtils.fromHtml(tweet.getBody()));
            viewHolder.mTvTweetHours.setText(AppUtils.getTwitterDateFormat(tweet.getCreatedAt()));
            viewHolder.mTvFavouriteCount.setText(String.valueOf(tweet.getFavCount()));
            Glide.with(holder.itemView.getContext()).load(tweet.getUser().getProfileImageUrl()).into(viewHolder.mImgUserProfile);

            List<Media> medias = tweet.getEntity().getMedias();
            if (medias != null && !medias.isEmpty()) {
                Media media = medias.get(0);
                if (media.isPhoto()) {
                    String mediaUrl = media.getMediaURL();
                    Glide.with(mTimelineActivity).load(mediaUrl).into(viewHolder.mImgUserTweetProfile);
                    viewHolder.mImgUserTweetProfile.setVisibility(View.VISIBLE);
                }else{
                    viewHolder.mImgUserTweetProfile.setVisibility(View.GONE);
                }
            } else {
                viewHolder.mImgUserTweetProfile.setVisibility(View.GONE);
            }

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mTimelineActivity.displayTweet(tweet);
                }
            });

        } else {
            final ViewHolderVideo viewHolderVideo = (ViewHolderVideo) holder;
            viewHolderVideo.mVTvTweetUserName.setText(tweet.getUser().getName());
            viewHolderVideo.mVTvTweetScreenName.setText("@" + tweet.getUser().getScreenName());
            viewHolderVideo.mVTvTweetDescription.setText(AppUtils.fromHtml(tweet.getBody()));

            if (tweet.getExtendedEntity() != null && tweet.getExtendedEntity().getVideoInfo() != null
                    && tweet.getExtendedEntity().getVideoInfo().getVariants().size() > 0
                    && tweet.getExtendedEntity().getVideoInfo().getVariants().get(0) != null) {

                if (tweet.getExtendedEntity().getType().equalsIgnoreCase("video")) {
                    viewHolderVideo.textureViewVideo.setMediaController(viewHolderVideo.fullScreenMediaPlayerController);
                    viewHolderVideo.textureViewVideo.setVideo(tweet.getExtendedEntity().getVideoInfo().getVariants().get(0).getUrl(),
                            MediaFensterPlayerController.DEFAULT_VIDEO_START);
                    viewHolderVideo.textureViewVideo.start();
                }

            }else{
                viewHolderVideo.textureViewVideo.setVisibility(View.GONE);
                viewHolderVideo.fullScreenMediaPlayerController.setVisibility(View.GONE);


            }

        }
    }




    private PendingIntent getPendingIntent(String url) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, url);
        return PendingIntent.getActivity(mTimelineActivity, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Override
    public int getItemCount() {
        return mTweets != null ? mTweets.size() : 0;
    }

    @Override
    public void onControlsVisibilityChange(boolean value) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        protected TextView mTvTweetUserName;
        protected ImageView mImgUserProfile;
        protected TextView mTvTweetScreenName;
        protected TextView mTvTweetHours;
        protected TextView mTvTweetDescription;
        protected ImageView mImgUserTweetProfile;
        protected TextView mTvImgDescription;
        protected TextView mTvFavouriteCount;
        protected ImageView mImgReplyTweet;

        public ViewHolder(View itemView) {
            super(itemView);

            mTvTweetUserName = (TextView) itemView.findViewById(R.id.tv_tweet_user_name);
            mImgUserProfile = (ImageView) itemView.findViewById(R.id.img_user_profile_pic);
            mTvTweetScreenName = (TextView) itemView.findViewById(R.id.tv_tweet_user_screen_name);
            mTvTweetHours = (TextView) itemView.findViewById(R.id.tv_tweet_addedd_timeline);
            mTvTweetDescription = (TextView) itemView.findViewById(R.id.tv_tweet_description);
            mImgUserTweetProfile = (ImageView) itemView.findViewById(R.id.img_user_tweet_profile);
            mTvImgDescription = (TextView) itemView.findViewById(R.id.tv_img_description);
            mTvFavouriteCount = (TextView) itemView.findViewById(R.id.tv_favourite_count);
            mImgReplyTweet = (ImageView) itemView.findViewById(R.id.img_reply);
        }

    }

    public class ViewHolderVideo extends RecyclerView.ViewHolder{

        protected TextView mVTvTweetUserName;
        protected ImageView mVImgUserProfile;
        protected TextView mVTvTweetScreenName;
        protected TextView mVTvTweetHours;
        protected TextView mVTvTweetDescription;
        protected TextView mVTvImgDescription;
        final  FensterVideoView textureViewVideo;
        final MediaFensterPlayerController fullScreenMediaPlayerController;


        public ViewHolderVideo(View itemView) {
            super(itemView);

            mVTvTweetUserName = (TextView) itemView.findViewById(R.id.tv_tweet_user_name);
            mVImgUserProfile = (ImageView) itemView.findViewById(R.id.img_user_profile_pic);
            mVTvTweetScreenName = (TextView) itemView.findViewById(R.id.tv_tweet_user_screen_name);
            mVTvTweetHours = (TextView) itemView.findViewById(R.id.tv_tweet_addedd_timeline);
            mVTvTweetDescription = (TextView) itemView.findViewById(R.id.tv_tweet_description);
            textureViewVideo = (FensterVideoView) itemView.findViewById(R.id.play_video_texture);
            fullScreenMediaPlayerController = (MediaFensterPlayerController) itemView.findViewById(R.id.play_video_controller);


        }


    }




}


