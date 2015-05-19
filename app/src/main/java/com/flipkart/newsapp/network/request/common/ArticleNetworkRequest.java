package com.flipkart.newsapp.network.request.common;



import android.content.Context;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.flipkart.newsapp.adapters.ArticleNewsAdapter;
import com.flipkart.newsapp.controllers.ArticleNewsController.ArticleNewsController;
import com.flipkart.newsapp.fragments.ArticleFragment;
import com.flipkart.newsapp.model.ResponseData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONObject;


/**
 * Created by ashokkumar.y on 15/05/15.
 */
public class ArticleNetworkRequest{


   // String             mUrl                   ="http://www.faroo.com/api?q=iphone&l=en&src=news&f=json&key=@aBBIz-CKJTNQshA8yhSbB3xJ1Q_";
    public     String             responseStringData;
    NetworkRequestQueue           queue;
    RequestQueue                  requestQueue;
    Context                       mContext;
    ListView                      mListView;
    //ArticleFragment articleFragment;
    ArticleNewsController articleNewsController;
   // ResponseData   mResponseObject;
    public  void  getArticleNews(Context context,int start,int length,ListView listView, final ArticleFragment articleFragment, final ArticleNewsController articleNewsController, final boolean appendDataOnScroll){
           String             mUrl                   ="http://www.faroo.com/api?q=iphone&l=en&src=news&f=json&key=@aBBIz-CKJTNQshA8yhSbB3xJ1Q_";
          // this.articleFragment=articleFragment;
           mContext=context;
           mListView=listView;
           queue = NetworkRequestQueue.getInstance();
           queue.initialize(mContext);
           requestQueue= queue.getRequestQueue();
           mUrl=mUrl+"&start="+start+"&length="+length;
        Log.d("requestUrl " , mUrl);
           final JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Request.Method.GET,mUrl,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                          System.out.println("Inside json request"+response);
                       // ArticleNewsController articleNewsController=new ArticleNewsController();
                        if(appendDataOnScroll==false) {
                            articleNewsController.processResponse(response);
                            articleFragment.setResponseInAdapter();
                        }
                        else{
                            articleNewsController.updateResponse(response);

                            articleFragment.setResponseInAdapter();
                            articleFragment.getArticleNewsAdapter().notifyDataSetChanged();
                           // articleNewsAdapter.notifyDataSetChanged();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                       Toast.makeText(mContext, error.toString(), Toast.LENGTH_LONG).show();
                        System.out.println("Inside json request"+error);

                    }
                });
        requestQueue.add(jsonObjRequest);
    }


}
