package com.codepath.apps.restclienttemplate.data;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.List;

/**
 * Created by hinaikhan on 9/29/17.
 */
@Parcel
public class VideoInfo {

    public List<Variants> variants;

    public VideoInfo() {
    }

    public VideoInfo(List<Variants> variants) {
        this.variants = variants;
    }

    public List<Variants> getVariants() {
        return variants;
    }

    public void setVariants(List<Variants> variants) {
        this.variants = variants;
    }

    @Override
    public String toString() {
        return "VideoInfo{" +
                "variants=" + variants +
                '}';
    }

    public static VideoInfo fromJson(JSONObject jsonObject){
        VideoInfo videoInfo = new VideoInfo();
        try {
            videoInfo.setVariants(Variants.fromJsonArray(jsonObject.getJSONArray("variants")));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return videoInfo;
    }


}
