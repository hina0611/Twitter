package com.codepath.apps.restclienttemplate.adapters;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
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

        mTweets.addAll(tweets);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mLayoutInflater = LayoutInflater.from(mTimelineActivity);
        RecyclerView.ViewHolder mViewHolder = null;


//        // Inflate the custom layout
//        View tweetView = mLayoutInflater.inflate(R.layout.activity_tweet, parent, false);
//        // Return a new holder instance
//        ViewHolder viewHolder = new ViewHolder(tweetView);

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


        switch (holder.getItemViewType()) {

            case IMAGE: {

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
                        Glide.with(mTimelineActivity).load(media.getMediaURL()).into(viewHolder.mImgUserTweetProfile);
                    }
                }

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
//                        PendingIntent pendingIntent = getPendingIntent(tweet.getUser().getWebUrl());
//                        Bitmap bitmap = BitmapFactory.decodeResource(mTimelineActivity.getResources(), R.drawable.share_icon);
//                        builder.setActionButton(bitmap, "Share", pendingIntent);
//                        CustomTabsIntent intentCustomTab = builder.build();
//                        intentCustomTab.launchUrl(mTimelineActivity, Uri.parse(tweet.getUser().getWebUrl().toString()));
//                        builder.setToolbarColor(ContextCompat.getColor(mTimelineActivity, R.color.colorAccent));
                        mTimelineActivity.displayTweet(tweet);
                    }
                });

                viewHolder.mImgReplyTweet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mTimelineActivity.displayTweet(tweet);
                    }
                });

                break;
            }

            case VIDEO: {

                final ViewHolderVideo viewHolderVideo = (ViewHolderVideo) holder;
                viewHolderVideo.mVTvTweetUserName.setText(tweet.getUser().getName());
                viewHolderVideo.mVTvTweetScreenName.setText("@" + tweet.getUser().getScreenName());
                viewHolderVideo.mVTvTweetDescription.setText(Html.fromHtml(tweet.getBody()));

                List<Media> medias = tweet.getEntity().getMedias();
                if (medias != null && !medias.isEmpty()) {
                    Media media = medias.get(0);
                    if (media.isVideo()) {
//                       with(mContext).load(media.getMediaURL()).into(viewHolder.mImgUserTweetProfile);

//                       ViewHolder.mImgUserTweetProfile.setVisibility(View.GONE);
//                       final VideoView videoView = holder.mVideoUserTweetProfile;
//                       videoView.setVisibility(View.VISIBLE);
//                       Uri uri = Uri.parse("https://video.twimg.com/ext_tw_video/913352683725058048/pu/vid/240x240/vLEdT5Gky1Tk_eY9.mp4");
//                       videoView.setVideoPath("http://www.ebookfrenzy.com/android_book/movie.mp4");
//                       videoView.start();

                        viewHolderVideo.textureView.setMediaController(viewHolderVideo.fullScreenMediaPlayerController);
                        viewHolderVideo.textureView.setVideo("https://video.twimg.com/ext_tw_video/913352683725058048/pu/vid/240x240/vLEdT5Gky1Tk_eY9.mp4", viewHolderVideo.fullScreenMediaPlayerController.DEFAULT_VIDEO_START);
                        viewHolderVideo.textureView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {


                            @Override
                            public void onPrepared(MediaPlayer mp) {
                                viewHolderVideo.textureView.start();
                            }
                        });

                    }

                    break;
                }

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
        final  FensterVideoView textureView;
        final SimpleMediaFensterPlayerController fullScreenMediaPlayerController;


        public ViewHolderVideo(View itemView) {
            super(itemView);

            mVTvTweetUserName = (TextView) itemView.findViewById(R.id.tv_tweet_user_name);
            mVImgUserProfile = (ImageView) itemView.findViewById(R.id.img_user_profile_pic);
            mVTvTweetScreenName = (TextView) itemView.findViewById(R.id.tv_tweet_user_screen_name);
            mVTvTweetHours = (TextView) itemView.findViewById(R.id.tv_tweet_addedd_timeline);
            mVTvTweetDescription = (TextView) itemView.findViewById(R.id.tv_tweet_description);
            textureView = (FensterVideoView) itemView.findViewById(R.id.play_video_texture);
            fullScreenMediaPlayerController = (SimpleMediaFensterPlayerController) itemView.findViewById(R.id.play_video_controller);


        }
    }






}


