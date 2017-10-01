package com.codepath.apps.restclienttemplate.data;

import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

/**
 * Created by hinaikhan on 9/29/17.
 */
@Parcel
public class ExtendedEntity implements Parcelable{

    public VideoInfo videoInfo;
    public String type;

    public ExtendedEntity() {
    }

    public ExtendedEntity(VideoInfo videoInfo, String type) {
        this.videoInfo = videoInfo;
        this.type = type;
    }

    protected ExtendedEntity(android.os.Parcel in) {
        type = in.readString();
    }

    @Override
    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeString(type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ExtendedEntity> CREATOR = new Creator<ExtendedEntity>() {
        @Override
        public ExtendedEntity createFromParcel(android.os.Parcel in) {
            return new ExtendedEntity(in);
        }

        @Override
        public ExtendedEntity[] newArray(int size) {
            return new ExtendedEntity[size];
        }
    };

    public VideoInfo getVideoInfo() {
        return videoInfo;
    }

    public void setVideoInfo(VideoInfo videoInfo) {
        this.videoInfo = videoInfo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ExtendedEntity{" +
                "videoInfo=" + videoInfo +
                ", type='" + type + '\'' +
                '}';
    }

    public static ExtendedEntity fromJson(JSONObject jsonObject) throws Exception {
        ExtendedEntity extendedEntity = new ExtendedEntity();

            if (jsonObject.has("video_info")) {
                extendedEntity.setVideoInfo(VideoInfo.fromJson(jsonObject.getJSONObject("video_info")));
            }
            if (jsonObject.has("type")) {
                extendedEntity.setType(jsonObject.getString("type"));
            }


        return extendedEntity;
    }



}
