package com.icerockdev.babenko.model;

/**
 * Created by Roman Babenko on 11/05/17.
 */

public class ImageItem {
    private int mAlbumId;
    private int mId;
    private String mTitle;
    private String mUrl;
    private String mThumbnailUrl;

    public ImageItem(ImageResponse imageResponse) {
        this.mAlbumId = imageResponse.getAlbumId();
        this.mId = imageResponse.getId();
        this.mTitle = imageResponse.getTitle();
        this.mUrl = imageResponse.getUrl();
        this.mThumbnailUrl = imageResponse.getThumbnailUrl();
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

    public void setAlbumId(int albumId) {
        this.mAlbumId = albumId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.mThumbnailUrl = thumbnailUrl;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public void setUrl(String url) {
        this.mUrl = url;
    }
}
