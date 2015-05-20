package com.flipkart.newsapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.flipkart.newsapp.ArticleDetailPagerActivity;
import com.flipkart.newsapp.ArticleNewsListener.EndlessScrollListener;
import com.flipkart.newsapp.R;
import com.flipkart.newsapp.adapters.ArticleNewsAdapter;
import com.flipkart.newsapp.controllers.ArticleNewsController.ArticleNewsController;
import com.flipkart.newsapp.model.ResponseData;
import com.flipkart.newsapp.network.request.common.ArticleNetworkRequest;



/**
 * Created by amit.rs on 11/05/15.
 */
public class ArticleFragment extends Fragment {

    ListView mListView;
    Activity mActivity;
    Context mContext;
    int start;//new
    int length;//new
/*    int currentFirstVisibleItem;
    int currentVisibleItemCount;*/
  //  String             mUrl                   ="http://www.faroo.com/api?q=iphone&l=en&src=news&f=json&key=@aBBIz-CKJTNQshA8yhSbB3xJ1Q_";
    ArticleNetworkRequest getNewsListData= new ArticleNetworkRequest();
    ArticleNewsController articleNewsController;
    ResponseData mObject;
    boolean appendDataOnScroll=false;
    ArticleNewsAdapter articleNewsAdapter;
    public final String TAG = "ARTICLE_NEWS";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_article, container, false);
        mListView = (ListView) view.findViewById(R.id.listView);
        mActivity = getActivity();//new
        mContext = mActivity.getApplicationContext();//new
        articleNewsController=ArticleNewsController.getInstance();
        start = 1;
        length = 10;
        getNewsListData.getArticleNews(mContext, start, length, mListView,this,articleNewsController,appendDataOnScroll);
      mListView.setOnScrollListener(new EndlessScrollListener() {


          @Override
            public void onLoadMore(int page, int totalItemsCount) {
                Log.d(TAG,"Inside on load more");
              articleNewsController=ArticleNewsController.getInstance();
              start = this.getPreviousTotalItemCount()+1;
              length = start + 10-1;
               appendDataOnScroll=true;
               getNewsListData.getArticleNews(mContext, start, length, mListView,ArticleFragment.this,articleNewsController,appendDataOnScroll);

            }
        });
       mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //responseStringData=responseObject.toString();//new
                Intent intent;
                intent = new Intent(mContext, ArticleDetailPagerActivity.class);
               // intent.putExtra("data", responseStringData);
                intent.putExtra("position", i);
                startActivity(intent);
            }
        });


   /*  mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            public int currentFirstVisibleItem;
            public int currentVisibleItemCount;
            public int currentScrollState;
            public int currentTotalItemCount;
            public int lastItem;

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                this.currentFirstVisibleItem = firstVisibleItem;
                this.currentVisibleItemCount = visibleItemCount;
                this.currentTotalItemCount =  totalItemCount;
                lastItem = firstVisibleItem + visibleItemCount;
                System.out.println("scrolled");
            }
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                System.out.println("onScrollStateChanged");
                this.currentScrollState = scrollState;
               // if ((mArticleNewsAdapter.getCount() - (currentFirstVisibleItem + currentVisibleItemCount)) <= 1) {
                    this.isScrollCompleted();
                //}

            }

            private void isScrollCompleted() {
                System.out.println("isScrollCompleted");
                if (this.currentVisibleItemCount > 0 && lastItem == this.currentTotalItemCount) {
                    //&& this.currentScrollState == 1
                   *//*** In this way I detect if there's been a scroll which has completed ***//*
                   *//*** do the work for load more date! ***//*
                    start = this.currentTotalItemCount+1;
                    length = this.currentTotalItemCount + 10;
                    isLoading=false;
                   // System.out.println("url "+mUrl);
                    if(!isLoading){
                        isLoading = true;
                        appendDataOnScroll=true;
                        getNewsListData.getArticleNews(mContext, start, length, mListView,mUrl,ArticleFragment.this,articleNewsController,appendDataOnScroll);
                    }


                }
            }
        });*/

        return view;
    }
    public void setResponseInAdapter(){
       // ArticleNewsController articleNewsController = new ArticleNewsController();
        mObject=articleNewsController.getResponseObject();
        articleNewsAdapter = new ArticleNewsAdapter(mContext,mObject );
       // articleNewsAdapter.notifyDataSetChanged();
        mListView.setAdapter(articleNewsAdapter);
    }
    public ArticleNewsAdapter getArticleNewsAdapter(){
        return articleNewsAdapter;
    }
}




