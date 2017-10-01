package com.codepath.apps.restclienttemplate.data;

import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

/**
 * Created by hinaikhan on 9/28/17.
 */
@Parcel
public class User  implements Parcelable{

    public String uid;
    public String name;
    public String screenName;
    public String profileImageUrl;
    public String webUrl;


    public User(){

    }

    public User(String uid, String name, String screenName, String profileImageUrl, String webUrl) {
        this.uid = uid;
        this.name = name;
        this.screenName = screenName;
        this.profileImageUrl = profileImageUrl;
        this.webUrl = webUrl;

    }

    protected User(android.os.Parcel in) {
        uid = in.readString();
        name = in.readString();
        screenName = in.readString();
        profileImageUrl = in.readString();
        webUrl = in.readString();
    }

    @Override
    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeString(uid);
        dest.writeString(name);
        dest.writeString(screenName);
        dest.writeString(profileImageUrl);
        dest.writeString(webUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(android.os.Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", screenName='" + screenName + '\'' +
                ", profileImageUrl='" + profileImageUrl + '\'' +
                ", webUrl='" + webUrl + '\'' +
                '}';
    }

    public static User fromJson(JSONObject jsonObject){

        User user = new User();
        try {
            user.setUid(jsonObject.getString("id"));
            user.setName(jsonObject.getString("name"));
            user.setScreenName(jsonObject.getString("screen_name"));
            user.setProfileImageUrl(jsonObject.getString("profile_image_url"));
            user.setWebUrl(jsonObject.getString("url"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return user;
    }
}
