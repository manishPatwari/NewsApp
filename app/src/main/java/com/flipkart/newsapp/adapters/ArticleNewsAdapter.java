package com.flipkart.newsapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.flipkart.newsapp.R;
import com.flipkart.newsapp.model.ResponseData;
import com.flipkart.newsapp.network.request.common.NetworkRequestQueue;

import java.util.zip.Inflater;

/**
 * Created by ashokkumar.y on 15/05/15.
 */
public class ArticleNewsAdapter extends BaseAdapter {

    Context mContext;
    ResponseData mResponse;
    // RequestQueue        mRequestQueue ;
    ImageLoader mImageLoader;
    NetworkRequestQueue mQueue;


    //constructor
    // public ArticleNewsAdapter(Context context,  ResponseData mResponse){
    public ArticleNewsAdapter(Context context, ResponseData mResponse) {
        this.mContext = context;
        this.mResponse = mResponse;
        mQueue = NetworkRequestQueue.getInstance();
        mQueue.initialize(context);
        // mRequestQueue=mQueue.getRequestQueue();
        mImageLoader = mQueue.getImageLoader();

    }

    //constructor
    public ArticleNewsAdapter(Context context) {
        this.mContext = context;


    }

    @Override
    public int getCount() {
        return mResponse.getNewsData().size();
    }

    @Override
    public Object getItem(int position) {
        return mResponse.getNewsData().get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View rowItem = convertView;
        mViewHolder holder;
        if (rowItem == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
            rowItem = inflater.inflate(R.layout.article_item_list_view, viewGroup, false);
            holder = new mViewHolder(rowItem);
            rowItem.setTag(holder);
        } else {
            holder = (mViewHolder) rowItem.getTag();
        }
        //setting text to  Text View for title
        holder.articleTitle.setText(mResponse.getNewsData().get(position).getTitle());
        if (mResponse.getNewsData().get(position).getImageUrl() != "") {
            //setting image to  Image View for image
            holder.articleImageView.setImageUrl(mResponse.getNewsData().get(position).getImageUrl(), mImageLoader);
        } else {
            holder.articleImageView.setImageUrl("https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcQSRh1LEkGycmDCVeK5A7W3VL3EVgXfnsoPUGPKSR50eCBqHu_-gQ", mImageLoader);
        }
        return rowItem;

    }
}
class mViewHolder{
    TextView articleTitle;
    NetworkImageView articleImageView;
    mViewHolder(View v){
        articleTitle= (TextView) v.findViewById(R.id.titleView);
        articleImageView= (NetworkImageView) v.findViewById(R.id.imageView);

    }

}
