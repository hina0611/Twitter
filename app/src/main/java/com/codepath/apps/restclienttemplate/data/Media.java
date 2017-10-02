package com.codepath.apps.restclienttemplate.data;

import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hinaikhan on 9/29/17.
 */
@Parcel
public class Media implements Parcelable {

    public long mId;
    private String type;
    public String mediaURL; //media_url

    public Media(){

    }

    public Media(long mId, String type, String mediaURL) {
        this.mId = mId;
        this.type = type;
        this.mediaURL = mediaURL;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getmId() {
        return mId;
    }

    public void setmId(long mId) {
        this.mId = mId;
    }

    public String getMediaURL() {
        return mediaURL;
    }

    public void setMediaURL(String mediaURL) {
        this.mediaURL = mediaURL;
    }

    @Override
    public String toString() {
        return "Media{" +
                "mId=" + mId +
                ", type='" + type + '\'' +
                ", mediaURL='" + mediaURL + '\'' +
                '}';
    }

    public static Media fromJson(JSONObject jsonObject){

        Media media = new Media();

        try {
            media.setmId(jsonObject.getLong("id"));
            media.setMediaURL(jsonObject.getString("media_url"));
            media.setType(jsonObject.getString("type"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return media;
    }

    public static List<Media> fromJsonArray(JSONArray jsonArray){

        List<Media> list = new ArrayList<>(jsonArray.length());
        for(int i =0;  i < jsonArray.length(); i++){
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                list.add(Media.fromJson(jsonObject));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return list;
    }

    public boolean isPhoto() {
        return this.type.equalsIgnoreCase("photo");
    }

    public boolean isVideo() {
        return this.type.equalsIgnoreCase("video");
    }

    public Media(android.os.Parcel in) {
        mId = in.readLong();
        type = in.readString();
        mediaURL = in.readString();
    }

    @Override
    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeLong(mId);
        dest.writeString(type);
        dest.writeString(mediaURL);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Media> CREATOR = new Creator<Media>() {
        @Override
        public Media createFromParcel(android.os.Parcel in) {
            return new Media(in);
        }

        @Override
        public Media[] newArray(int size) {
            return new Media[size];
        }
    };

}
