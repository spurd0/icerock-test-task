package com.icerockdev.babenko.model;

/**
 * Created by Roman Babenko on 10/05/17.
 */

public class ImageResponse {

    private int albumId;
    private int id;
    private String title;
    private String url;
    private String thumbnailUrl;

    public int getId() {
        return id;
    }

    public int getAlbumId() {
        return albumId;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}
