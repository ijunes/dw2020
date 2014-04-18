package com.eharmony.example.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by fsiu on 3/21/14.
 */
public class FiveHundredPxPhoto {

    private long id;

    @SerializedName("user_id")
    private long userId;

    private String name;
    private String description;

    @SerializedName("image_url")
    private String imageUrl;

    private ArrayList<FiveHundredPxImageMetadata> images;

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ArrayList<FiveHundredPxImageMetadata> getImages() {
        return images;
    }
}
