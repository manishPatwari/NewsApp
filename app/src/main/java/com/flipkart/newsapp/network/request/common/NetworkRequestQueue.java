package com.flipkart.newsapp.network.request.common;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.flipkart.newsapp.utils.LruBitmapCache;

/**
 * Created by manish.patwari on 5/10/15.
 */
public class NetworkRequestQueue {

    private RequestQueue mRequestQueue;
    private static NetworkRequestQueue instance;
    private NetworkRequestQueue(){};
    private ImageLoader mImageLoader;

    public static NetworkRequestQueue getInstance(){
        if(instance == null){
            synchronized (NetworkRequestQueue.class){
                if(instance == null){
                    instance = new NetworkRequestQueue();
                }
            }
        }
        return instance;
    }

    //A key concept is that the RequestQueue must be instantiated with the Application context,
    // not an Activity context.
    // This ensures that the RequestQueue will last for the lifetime of your app,
    // instead of being recreated every time the activity is recreated.
    public NetworkRequestQueue initialize(Context mContext){

        // Instantiate the cache
       // Cache cache = new DiskBasedCache(mContext.getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
       // BasicNetwork network = new BasicNetwork(new HurlStack());

          // Instantiate the RequestQueue with the cache and network.
       // mRequestQueue = new RequestQueue(cache, network);

        mRequestQueue =  Volley.newRequestQueue(mContext.getApplicationContext(),1024 * 1024); // 1MB cap
         // Start the queue
        mRequestQueue.start();

        mImageLoader = new ImageLoader(mRequestQueue, new LruBitmapCache(LruBitmapCache.getCacheSize(mContext.getApplicationContext())));

        return instance;
    }

    public RequestQueue getRequestQueue(){
        return mRequestQueue;
    }

   public ImageLoader getImageLoader()
   {
       return mImageLoader;
   }





    // Add the request to the RequestQueue.
    public <T> void addToRequestQueue(Request<T> req) {
        mRequestQueue.add(req);
    }


    public void cancelAllRequest(String tag)
    {
        mRequestQueue.cancelAll(tag);
    }

    public void destroy(){
        mRequestQueue.stop();
        mRequestQueue = null;
        instance = null;

        mImageLoader = null;
    }
}
