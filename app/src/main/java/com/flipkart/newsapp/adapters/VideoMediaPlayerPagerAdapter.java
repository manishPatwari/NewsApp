package com.flipkart.newsapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;

import com.flipkart.newsapp.R;
import com.flipkart.newsapp.model.VideosItem;

import java.util.ArrayList;

/**
 * Created by amit.rs on 18/05/15.
 */
public class VideoMediaPlayerPagerAdapter extends PagerAdapter implements View.OnAttachStateChangeListener{

    private Context mContext;
    private int numberOfPages;
    private ArrayList<VideosItem> video_list;
    private View[] pages;
    private int totalRenderedPages = 3;
    private LayoutInflater inflater;
    private int lastPageCreatedIndex;


    public VideoMediaPlayerPagerAdapter(Context context , int numberOfPages, ArrayList<VideosItem> video_list) {
        mContext = context;
        this.numberOfPages = numberOfPages;
        this.video_list = video_list;
        this.pages = new View[totalRenderedPages];
         inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

        View view = pages[position % totalRenderedPages];
        Holder mHolder = null;


        if(view == null)
        {

            view = inflater.inflate(R.layout.activity_video_player, null);

            pages[position % totalRenderedPages] =  view;

            mHolder = new Holder();

            mHolder.webview = (WebView)view.findViewById(R.id.web_view);

            mHolder.progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

            mHolder.shareButton = (Button) view.findViewById(R.id.share_button);


            mHolder.webview.getSettings().setJavaScriptEnabled(true);

            mHolder.webview.setWebViewClient(new WebViewClient() {


                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                   // ((View)view.getTag()).setVisibility(View.VISIBLE);

                }

                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);

                   // ((View)view.getTag()).setVisibility(View.GONE);
                }
            });

            mHolder.shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //raising a share intent
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, video_list.get(position).getTitle().toString());
                    shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, video_list.get(position).getDescription().toString());
                    mContext.startActivity(shareIntent);
                }
            });


            view.setTag(mHolder);


        }
        else
        {
            mHolder = (Holder)view.getTag();

            container.removeView(view);

            int indexToStopVideo;

            // Swipe Forward
            if(position > lastPageCreatedIndex)
            {
                indexToStopVideo= (position-2);

            }
            else // Swipe Backward
            {
                indexToStopVideo= (position+2);

            }

            View previousPage = pages[indexToStopVideo % totalRenderedPages];
            Holder previousPageHolder =(Holder)previousPage.getTag();

            if(previousPage != null)
            {
                previousPageHolder.webview.loadUrl(previousPageHolder.webview.getUrl());
            }


        }


        mHolder.webview.loadUrl("https://www.youtube.com/watch?v="+ video_list.get(position).getVideoID());

        (container).addView(view);

        lastPageCreatedIndex = position;

        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

       // ((WebView)((View)object).getTag()).destroy();
      //  (container).removeView((View)object);
//        (container).removeAllViews();
//        webView.onPause();
//
//
//        webView.loadUrl("about:blank");
//        webView.destroy();
//        webView = null;
    }

    @Override
    public void onViewAttachedToWindow(View v) {
        Log.d("debug","Attach");

    }

    @Override
    public void onViewDetachedFromWindow(View v) {
        Log.d("debug","Detach");

    }

    class Holder{
        WebView webview;
        Button shareButton;
        ProgressBar progressBar;
    }

}
