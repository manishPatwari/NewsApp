package com.flipkart.newsapp.adapters;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.flipkart.newsapp.R;
import com.flipkart.newsapp.model.FlickrResponse;
import com.flipkart.newsapp.model.JsonPhotoDataProvider;
import com.flipkart.newsapp.network.request.common.ImageResponse;
import com.flipkart.newsapp.network.request.common.NetworkRequestQueue;
import com.flipkart.newsapp.utils.HttpsTrustManager;

/**
 * Created by kumar.samdwar on 18/05/15.
 */
public class GalleryViewPagerAdapter extends PagerAdapter implements BaseNewsAdapterUpdaterListener {
    private static final String TAG = "FLICKR";
    private Context mContext;
    private NetworkRequestQueue mNetworkRequestQueue;
    private ImageResponse mImageResponse;
    private NetworkImageView mNetworkImageView;
    private ImageLoader mImageLoader;
    private String mImageURL;
    private static int mPosition;


    public GalleryViewPagerAdapter(Context context) {

        mContext = context;
        mImageResponse = ImageResponse.getInstance();
        mNetworkRequestQueue = NetworkRequestQueue.getInstance();
        mImageLoader = mNetworkRequestQueue.getImageLoader();
        mImageResponse.registerUpdateDataSourceListener(GalleryViewPagerAdapter.this);
    }

    private void setCurrentPosition(int position) {
        mPosition = position;
    }

    public int getCurrentPosition() {
        return mPosition;
    }

    @Override
    public int getCount() {
        return mImageResponse.getFlickrResponse().getFlickrPhoto().getPhoto().size();
    }


    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = (RelativeLayout) layoutInflater.inflate(R.layout.view_pager_image, container, false);

        TextView textView = (TextView) rowView.findViewById(R.id.title_text_view);
        mNetworkImageView = (NetworkImageView) rowView.findViewById(R.id.gallery_image_view);

        setCurrentPosition(position);
        final FlickrResponse flickrResponse = mImageResponse.getFlickrResponse();
        Log.d(TAG, "flickrResponse = :" + flickrResponse);
        if (flickrResponse != null && flickrResponse.mFlickrPhoto != null) {

            JsonPhotoDataProvider jsonPhotoDataProvider = new JsonPhotoDataProvider(flickrResponse, position);
            mImageURL = jsonPhotoDataProvider.getImageUrl();
            Log.d(TAG, mImageURL);
            mNetworkImageView.setImageUrl(mImageURL, mImageLoader);
            textView.setText(jsonPhotoDataProvider.getTitle());
            HttpsTrustManager.allowAllSSL();
            mImageLoader.get(mImageURL, ImageLoader.getImageListener(mNetworkImageView, R.drawable.loading_txt, R.drawable.error_ic));
        } else {
            textView.setText("Some Error Occurred !");
            mNetworkImageView.setErrorImageResId(R.drawable.error_ic);
        }


        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,
                        "Page " + position + " clicked",
                        Toast.LENGTH_LONG).show();
//                ((Activity)mContext).getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

//                showDialog(flickrResponse, mImageLoader, position);
                showFullScreenImageSlider(flickrResponse, mImageLoader, position);


            }
        });
        container.addView(rowView);
        return rowView;
    }

    private void showFullScreenImageSlider(FlickrResponse flickrResponse, ImageLoader imageLoader, int position) {
        final Dialog dialog = new Dialog(mContext, android.R.style.Theme_Material_Light_NoActionBar_Fullscreen);
//        Dialog dialog = new Dialog(mContext, android.R.style.Theme_Material);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
//        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        (Activity)mContext.


        dialog.setContentView(R.layout.full_screen_image);
        dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
        ViewPager viewPager = (ViewPager) dialog.findViewById(R.id.full_screen_image_slide_view);
        FullScreenImageSlideAdapter fullScreenImageSlideAdapter =  new FullScreenImageSlideAdapter(mContext);
        viewPager.setAdapter(fullScreenImageSlideAdapter);
        viewPager.setCurrentItem(position);
        dialog.show();
        ImageButton closeDialogButton = (ImageButton) dialog.findViewById(R.id.close_button);
        closeDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public NetworkImageView getNetworkImageView() {
        return mNetworkImageView;
    }

    /*void showDialog(FlickrResponse flickrResponse, ImageLoader imageLoader, int position) {
        final Dialog dialog = new Dialog(mContext, android.R.style.Theme_Material_Light_NoActionBar_Fullscreen);
        dialog.setContentView(R.layout.fragment_dialog);
        dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
        NetworkImageView fullScreenImageView = (NetworkImageView) dialog.findViewById(R.id.full_screen_image_view);
        JsonPhotoDataProvider jsonPhotoDataProvider = new JsonPhotoDataProvider(flickrResponse, position);
        String imageURL = jsonPhotoDataProvider.getImageUrl();
        fullScreenImageView.setImageUrl(imageURL, imageLoader);
        HttpsTrustManager.allowAllSSL();
        imageLoader.get(imageURL, ImageLoader.getImageListener(fullScreenImageView, R.drawable.loading_txt, R.drawable.error_ic));
        dialog.show();
        ImageButton closeDialogButton = (ImageButton) dialog.findViewById(R.id.close_button);
        closeDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }*/

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }

    @Override
    public float getPageWidth(int position) {
        return super.getPageWidth(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }

    @Override
    public void updateListener() {
        Log.i(TAG, "Just got update ...");
        this.notifyDataSetChanged();
    }
}