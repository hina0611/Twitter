package com.codepath.apps.restclienttemplate.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;

/**
 * Created by hinaikhan on 9/29/17.
 */
@Parcel
public class Variants {

    long bitrate;
    String contentType;
    String url;

    public Variants() {
    }

    public Variants(long bitrate, String contentType, String url) {
        this.bitrate = bitrate;
        this.contentType = contentType;
        this.url = url;
    }

    public long getBitrate() {
        return bitrate;
    }

    public void setBitrate(long bitrate) {
        this.bitrate = bitrate;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Variant{" +
                "bitrate=" + bitrate +
                ", contentType='" + contentType + '\'' +
                ", url='" + url + '\'' +
                '}';
    }


    public static Variants fromJson(JSONObject jsonObject){
        Variants variants = new Variants();
        try {
            variants.setBitrate(jsonObject.getLong("bitrate"));
            variants.setContentType(jsonObject.getString("ontent_type"));
            variants.setUrl(jsonObject.getString("url"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return variants;
    }

    public static List<Variants> fromJsonArray(JSONArray jsonArray){
        List<Variants> variantses = new ArrayList<>(jsonArray.length());

        for(int i=0 ; i < jsonArray.length(); i++){
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                variantses.add(Variants.fromJson(jsonObject));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return variantses;
    }

}
