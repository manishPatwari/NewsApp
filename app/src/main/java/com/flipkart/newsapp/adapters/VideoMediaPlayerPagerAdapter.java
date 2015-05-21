package com.flipkart.newsapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.flipkart.newsapp.R;
import com.flipkart.newsapp.model.VideosItem;

import java.util.ArrayList;

/**
 * Created by amit.rs on 18/05/15.
 */
public class VideoMediaPlayerPagerAdapter extends PagerAdapter {

    private Context mContext;
    private int numberOfPages;
    private ArrayList<VideosItem> video_list;
    private WebView webView;
    private Button shareButton;

    public VideoMediaPlayerPagerAdapter(Context context , int numberOfPages, ArrayList<VideosItem> video_list) {
        mContext = context;
        this.numberOfPages = numberOfPages;
        this.video_list = video_list;
    }

    @Override
    public int getCount() {
        return numberOfPages;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }


    @Override
    public Object instantiateItem(ViewGroup container, final int position) {


        LayoutInflater inflater = (LayoutInflater) container.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.activity_video_player, null);

        shareButton = (Button) view.findViewById(R.id.share_button);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //raising a share intent
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Youtube Video description");
                shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Youtube video title");
                mContext.startActivity(shareIntent);
            }
        });

        webView = (WebView) view.findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient( new WebViewClient());
        webView.loadUrl("https://www.youtube.com/watch?v="+ video_list.get(position).getVideoID());
        (container).addView(view, 0);

        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        (container).removeView((View) object);

    }
}
