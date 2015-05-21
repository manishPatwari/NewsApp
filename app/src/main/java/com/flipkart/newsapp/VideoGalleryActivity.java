package com.flipkart.newsapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.android.volley.toolbox.ImageLoader;
import com.flipkart.newsapp.adapters.VideoMediaPlayerPagerAdapter;
import com.flipkart.newsapp.controller.VideoNewsController;
import com.flipkart.newsapp.model.VideosItem;
import com.flipkart.newsapp.network.request.common.NetworkRequestQueue;

import java.util.ArrayList;


public class VideoGalleryActivity extends ActionBarActivity {

    private ImageLoader mImageLoader;
    private NetworkRequestQueue mNetworkRequestQueue;
    private int listItemPosition;
    private int totalNumberOfItems;

    private Context mContext;
    private Toolbar toolbar;
    private ArrayList<VideosItem> video_list;

    // Activity lifecycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_gallery);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        if (toolbar != null) {
            toolbar.setTitle(R.string.app_name);
            setSupportActionBar(toolbar);
        }

        Intent intent = getIntent();

        mContext = this;

        if (intent != null) {

            if (null != intent.getExtras()) {
                listItemPosition = intent.getIntExtra("ListItemPosition", 0);
                totalNumberOfItems = intent.getIntExtra("TotalNumberOfItems", 0);

            }

            mNetworkRequestQueue = NetworkRequestQueue.getInstance();
            mNetworkRequestQueue.initialize(mContext);
            mImageLoader = mNetworkRequestQueue.getImageLoader();
            video_list = VideoNewsController.getInstance().getVideosItems();


            initViewPager();
        }
    }

    public void initViewPager() {

        ViewPager viewPager = (ViewPager) findViewById(R.id.video_pager);
        VideoMediaPlayerPagerAdapter customPagerAdapter = new VideoMediaPlayerPagerAdapter(VideoGalleryActivity.this, totalNumberOfItems, video_list);
        viewPager.setAdapter(customPagerAdapter);
        viewPager.setCurrentItem(listItemPosition);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
