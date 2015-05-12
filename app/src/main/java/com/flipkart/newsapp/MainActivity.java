package com.flipkart.newsapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.flipkart.newsapp.config.AppPreferences;


public class MainActivity extends ActionBarActivity {

    AppInit mAppInit;
    ProgressDialog mDialog;
    ListView mNewsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAppInit = AppInit.getInstance();
        mAppInit.initialize(getApplicationContext());

        ((TextView)findViewById(R.id.userName)).setText(getResources().getString(R.string.greet) + ", " + AppPreferences.getInstance().getUserName());

//        mDialog = new ProgressDialog(this);
//        mDialog.setMessage("Searching for Book");
//        mDialog.setCancelable(false);



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

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAppInit.destroy();
    }
}
