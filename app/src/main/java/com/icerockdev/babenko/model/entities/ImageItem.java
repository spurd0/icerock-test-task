package com.icerockdev.babenko.model.entities;

/**
 * Created by Roman Babenko on 11/05/17.
 */

public class ImageItem {
    private int mAlbumId;
    private int mId;
    private String mTitle;
    private String mUrl;
    private String mThumbnailUrl;

    public ImageItem(int albumId,
                     int id,
                     String title,
                     String url,
                     String thumbnailUrl) {
        mAlbumId = albumId;
        mId = id;
        mTitle = title;
        mUrl = url;
        mThumbnailUrl = thumbnailUrl;
    }

    public int getAlbumId() {
        return mAlbumId;
    }

    public int getId() {
        return mId;
    }

    public String getThumbnailUrl() {
        return mThumbnailUrl;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getUrl() {
        return mUrl;
    }
}
