package com.codepath.apps.restclienttemplate.data;

import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.List;

/**
 * Created by hinaikhan on 9/29/17.
 */
@Parcel
public class Entity implements Parcelable{

    public List<Media> medias;

    public Entity() {

    }

    public Entity(List<Media> medias) {
        this.medias = medias;

    }

    protected Entity(android.os.Parcel in) {

    }

    public static final Creator<Entity> CREATOR = new Creator<Entity>() {
        @Override
        public Entity createFromParcel(android.os.Parcel in) {
            return new Entity(in);
        }

        @Override
        public Entity[] newArray(int size) {
            return new Entity[size];
        }
    };

    public List<Media> getMedias() {
        return medias;
    }

    public void setMedias(List<Media> medias) {
        this.medias = medias;
    }


    @Override
    public String toString() {
        return "Entity{" +
                "medias=" + medias +
                '}';
    }

    //deserialize the JSON
    protected static Entity fromJson(JSONObject jsonObject) {

        Entity entity = new Entity();

        try {
            if (jsonObject.has("media")) {
                entity.setMedias(Media.fromJsonArray(jsonObject.getJSONArray("media")));

            }
        } catch (JSONException e) {
            e.printStackTrace();

        }

        return entity;


    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(android.os.Parcel dest, int flags) {
    }
}
