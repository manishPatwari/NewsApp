package com.flipkart.newsapp.network.request.common;

import android.util.Log;

import com.flipkart.newsapp.model.FlickrResponse;
import com.flipkart.newsapp.model.Photo;

import java.util.ArrayList;

/**
 * Created by kumar.samdwar on 17/05/15.
 */
public class ImageResponse {


    private static final String TAG = "FLICKR";
    private static ImageResponse instance;
    private FlickrResponse mFlickrResponse;


    private ImageResponse() {
    }

    public static ImageResponse getInstance() {
        if (instance == null) {
            synchronized (ImageResponse.class) {
                if (instance == null) {
                    instance = new ImageResponse();
                }
            }
        }
        return instance;
    }


    public FlickrResponse getFlickrResponse() {
        return mFlickrResponse;
    }

    public void setFlickrResponse(FlickrResponse mFlickrResponse) {
        this.mFlickrResponse = mFlickrResponse;
    }

    public void updateFlickrResponse(FlickrResponse flickrResponse) {
        ArrayList<Photo> photoArrayList = mFlickrResponse.getFlickrPhoto().getPhoto();

        String photo = "[";
        ;
        for (Photo temp : photoArrayList) {
//            System.out.println(temp);
            photo = photo + "{ id : " + temp.getID() + ", owner: " + temp.getOwner() + ", secret: " + temp.getSecret() +
                    ", server: " + temp.getServer() + ", farm: " + temp.getFarm() +
                    ", title: " + temp.getTitle() +
                    ", ispublic: " + temp.getIsPublic() + ", isfriend: " + temp.getIsFriend() + ", isfamily: " + temp.getIsFamily() + " },";
        }

        ArrayList<Photo> newPhotoArrayList = flickrResponse.getFlickrPhoto().getPhoto();
        Log.d(TAG, "After Updating for Page -2 : " + newPhotoArrayList);

        photoArrayList.addAll(newPhotoArrayList);
        mFlickrResponse.getFlickrPhoto().setPhoto(photoArrayList);

        Log.d(TAG, "Size of ArrayList After Appending " + mFlickrResponse.getFlickrPhoto().getPhoto().size());


    }


}