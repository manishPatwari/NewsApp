package com.flipkart.newsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.flipkart.newsapp.adapters.ArticleNewsDetailAdapter;
import com.flipkart.newsapp.controllers.ArticleNewsController.ArticleNewsController;
import com.flipkart.newsapp.model.ResponseData;

/**
 * Created by ashokkumar.y on 13/05/15.
 */
public class ArticleDetailPagerActivity extends ActionBarActivity {

    ViewPager mViewPager;
    private int position;
    ArticleNewsController articleNewsController;
    ResponseData detailObject;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_layout_view_pager);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        if (toolbar != null) {
            toolbar.setTitle(R.string.app_name);
            setSupportActionBar(toolbar);
        }
        Intent intent = getIntent();
        if(intent!=null){
            position=intent.getIntExtra("position", position);
        }
        articleNewsController=ArticleNewsController.getInstance();
        detailObject=articleNewsController.getResponseObject();
        ArticleNewsDetailAdapter mNewsDetailAdapter = new ArticleNewsDetailAdapter(this,detailObject,position);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mNewsDetailAdapter);
        mViewPager.setCurrentItem(position);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
       /* if (id == R.id.menu_item_share) {
            Intent share = new Intent(Intent.ACTION_SEND);
          // MenuItem shareItem = (MenuItem)item.findItem(R.id.article_item_share);
           // ShareActionProvider mShare = (ShareActionProvider) shareItem.getActionProvider();
            String newsUrl;
           // newsUrl=detailObject.getNewsData().get(position).getContentUrl();
        }*/
        return super.onOptionsItemSelected(item);
    }
}
