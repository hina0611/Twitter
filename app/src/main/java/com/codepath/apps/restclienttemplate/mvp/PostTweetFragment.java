package com.codepath.apps.restclienttemplate.mvp;

import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.data.Media;
import com.codepath.apps.restclienttemplate.data.Tweet;
import com.codepath.apps.restclienttemplate.data.User;

import java.util.List;

import static com.codepath.apps.restclienttemplate.mvp.TweetDetail.TWEET_RESPONSE;

/**
 * Created by hinaikhan on 10/1/17.
 */

public class PostTweetFragment extends DialogFragment {

    public static final int TWEET_MAX_CHARS = 140;

    private Button btnComposeTweet;
    private EditText etTweet;
    private TextView tvTweetCharacterCount;
    private ImageView imgCloseCompose, imgProfilePic;
    private Tweet mTweets;

    private static final String TAG = PostTweetFragment.class.getSimpleName();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.post_tweet, container, false);

        Bundle extras = new Bundle();
        if (extras != null) {
            Object obj = extras.get(TWEET_RESPONSE);
            if (obj != null && obj instanceof Tweet) {
                mTweets = (Tweet) obj;
            }
        }



        initializeViews(view);
        bindListeners();
        close();

        return view;
    }

    private void initializeViews(View view) {
        btnComposeTweet = (Button) view.findViewById(R.id.compose_btn);
        etTweet = (EditText) view.findViewById(R.id.et_tweet);
        tvTweetCharacterCount = (TextView) view.findViewById(R.id.tv_tweet_character_count);
        tvTweetCharacterCount.setText(TWEET_MAX_CHARS +  " left");
        tvTweetCharacterCount.setPadding(0,0,24,0);

        imgCloseCompose = (ImageView) view.findViewById(R.id.img_compose_close);
        imgProfilePic = (ImageView) view.findViewById(R.id.img_compose_profile);

    }

    private void bindListeners() {
        btnComposeTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postTweet();
            }
        });
        etTweet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > TWEET_MAX_CHARS) {
                    tvTweetCharacterCount.setText("Exceeded " + TWEET_MAX_CHARS + " characters limit");
                    btnComposeTweet.setEnabled(false);
                } else {
                    tvTweetCharacterCount.setText(TWEET_MAX_CHARS - s.length() + " left");
                    if (!btnComposeTweet.isEnabled()) {
                        btnComposeTweet.setEnabled(true);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



    }

    private void postTweet() {
        TweetPostPresenter presenter = new TweetPostPresenter(this);
        presenter.postTweet(etTweet.getText().toString());
    }

    public void displayNewTweet(Tweet tweet) {
        dismiss();
        ((TimelineActivity)getActivity()).displayNewTweet(tweet);
    }

    public void close() {
        imgCloseCompose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    @Override
    public void onResume() {

        Window window = getDialog().getWindow();
        Point size = new Point();
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        //set layout as 75% of screen width
        window.setLayout ((int) (size.x * 0.9), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        super.onResume();
    }



}
