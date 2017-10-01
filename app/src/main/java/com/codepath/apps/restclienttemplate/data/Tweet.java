package com.codepath.apps.restclienttemplate.data;

import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hinaikhan on 9/28/17.
 */


public class Tweet implements Parcelable{

    public String body;
    public long uid;
    public User user;
    public String createdAt;
    public Entity entity;
    public ExtendedEntity extendedEntity;
    public int favCount;

    public Tweet(){

    }

    public Tweet(String body, long uid, User user, String createdAt, Entity entity, ExtendedEntity extendedEntity, int favCount) {
        this.body = body;
        this.uid = uid;
        this.user = user;
        this.createdAt = createdAt;
        this.entity = entity;
        this.extendedEntity = extendedEntity;
        this.favCount = favCount;
    }

    protected Tweet(android.os.Parcel in) {
        body = in.readString();
        uid = in.readLong();
        user = in.readParcelable(User.class.getClassLoader());
        createdAt = in.readString();
        entity = in.readParcelable(Entity.class.getClassLoader());
        extendedEntity = in.readParcelable(ExtendedEntity.class.getClassLoader());
        favCount = in.readInt();

    }

    @Override
    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeString(body);
        dest.writeLong(uid);
        dest.writeParcelable(user, flags);
        dest.writeString(createdAt);
        dest.writeParcelable(entity, flags);
        dest.writeParcelable(extendedEntity, flags);
        dest.writeInt(favCount);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Tweet> CREATOR = new Creator<Tweet>() {
        @Override
        public Tweet createFromParcel(android.os.Parcel in) {
            return new Tweet(in);
        }

        @Override
        public Tweet[] newArray(int size) {
            return new Tweet[size];
        }
    };

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public ExtendedEntity getExtendedEntity() {
        return extendedEntity;
    }

    public void setExtendedEntity(ExtendedEntity extendedEntity) {
        this.extendedEntity = extendedEntity;
    }

    public int getFavCount() {
        return favCount;
    }

    public void setFavCount(int favCount) {
        this.favCount = favCount;
    }



    @Override
    public String toString() {
        return "Tweet{" +
                "body='" + body + '\'' +
                ", uid=" + uid +
                ", user=" + user +
                ", createdAt='" + createdAt + '\'' +
                ", entity=" + entity +
                ", extendedEntity=" + extendedEntity +
                ", favCount=" + favCount +
                '}';
    }

    //deserialize the JSON
    public static Tweet fromJson(JSONObject jsonObject) {
        Tweet tweet = new Tweet();

        //extract all the value from JSON
        try {
            tweet.setBody(jsonObject.getString("text"));
            tweet.setUid(jsonObject.getLong("id"));
            tweet.setUser(User.fromJson(jsonObject.getJSONObject("user")));
            tweet.setCreatedAt(jsonObject.getString("created_at"));
            tweet.setEntity(Entity.fromJson(jsonObject.getJSONObject("entities")));

            if(jsonObject.has("favourites_count")) {
                tweet.setFavCount(jsonObject.getInt("favourites_count"));
            }

            if (jsonObject.has("extended_entities")) {
                tweet.setExtendedEntity(ExtendedEntity.fromJson(jsonObject.getJSONObject("extended_entities")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tweet;

    }

    public static List<Tweet> fromJsonArray(JSONArray array) {
        List<Tweet> res = new ArrayList<Tweet>(array.length());
        for(int i = 0; i < array.length(); i++) {
            try {
                JSONObject object = array.getJSONObject(i);
                res.add(Tweet.fromJson(object));
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
        }
        return res;
    }
}
