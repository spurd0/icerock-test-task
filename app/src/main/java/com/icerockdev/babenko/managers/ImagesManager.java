package com.icerockdev.babenko.managers;

import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import com.icerockdev.babenko.BuildConfig;
import com.icerockdev.babenko.IceRockApplication;
import com.icerockdev.babenko.R;
import com.icerockdev.babenko.model.ImageItem;
import com.icerockdev.babenko.model.ImageResponse;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.icerockdev.babenko.data.ApplicationConstants.REQUEST_IMAGES_URL;

/**
 * Created by Roman Babenko on 10/05/17.
 */

public class ImagesManager {
    private static final String TAG = "ImagesManager";

    private ArrayList<ImageItem> mImagesList;

    public void requestPicturesList(final ImagesCallback callback) {
        if (mImagesList == null) {
            final Call<ImageResponse[]> data = IceRockApplication.getInstance().getRetrofitManager()
                    .getService().requestImages(REQUEST_IMAGES_URL);
            data.enqueue(new Callback<ImageResponse[]>() {
                @Override
                public void onResponse(Call<ImageResponse[]> call, Response<ImageResponse[]> response) {
                    if (response.body() == null) {
                        callback.failedResponse(IceRockApplication.getInstance()
                                .getString(R.string.request_data_fields_error_null));
                    } else {
                        mImagesList = convertImagesList(response.body());
                        callback.successResponse(mImagesList);
                    }
                }

                @Override
                public void onFailure(Call<ImageResponse[]> call, Throwable t) {
                    callback.failedResponse(t.getLocalizedMessage());
                    if (BuildConfig.DEBUG)
                        Log.e(TAG, "Request error " + t.getLocalizedMessage());
                }
            });
        } else {
            callback.successResponse(mImagesList);
        }

    }

    private ArrayList<ImageItem> convertImagesList(ImageResponse[] images) {
        ArrayList<ImageItem> result = new ArrayList<>();
        for (ImageResponse imageResponse : images) {
            result.add(new ImageItem(imageResponse.getAlbumId(), imageResponse.getId(),
                    imageResponse.getTitle(), imageResponse.getUrl(), imageResponse.getThumbnailUrl()));
        }
        return result;
    }

    public interface ImagesCallback{
        void successResponse(ArrayList<ImageItem> images);
        void failedResponse(String error);
    }

    public void requestImage(ImageView targetIv, String imageUrl,
                             com.squareup.picasso.Callback imageCallback) {
        Picasso picasso = new Picasso.Builder(IceRockApplication.getInstance()).listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                if (BuildConfig.DEBUG) exception.printStackTrace();
            }
        }).downloader(new OkHttpDownloader(IceRockApplication.getInstance())).build();
        picasso.load(imageUrl).into(targetIv, imageCallback);
    }

}
