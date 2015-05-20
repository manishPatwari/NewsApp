package com.flipkart.newsapp.controller;

import com.flipkart.newsapp.ArticleNewsListener.AdapterListener;
import com.flipkart.newsapp.adapters.ArticleNewsAdapter;
import com.flipkart.newsapp.fragments.ArticleFragment;
import com.flipkart.newsapp.model.ResponseData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

/**
 * Created by ashokkumar.y on 18/05/15.
 */
public class ArticleNewsController {
   public   ResponseData responseObject;
    ArticleNewsAdapter articleNewsAdapter;
    ArticleFragment articleFragment;
    private AdapterListener baseAdapterListener;
    private static ArticleNewsController instance;

    private ArticleNewsController(){}

    public static ArticleNewsController getInstance(){
        if(instance == null){
            synchronized (ArticleNewsController.class){
                if(instance == null){
                    instance = new ArticleNewsController();
                }
            }
        }
        return instance;
    }

   //private ArrayList<ResponseData> mResponseObject= new ArrayList<ResponseData>();
   public void registerDataSourceListener(AdapterListener adapterListener){
       this.baseAdapterListener = adapterListener;
   }

 public void  processResponse(JSONObject response){
     //System.out.println("inside controller");
     String responseStringData=response.toString();
     GsonBuilder gsonBuilder = new GsonBuilder();
     Gson gson = gsonBuilder.create();
     //List of require data
     this.responseObject = gson.fromJson(responseStringData, ResponseData.class);
     System.out.println("inside controller"+this.responseObject);


 }
    public ResponseData  getResponseObject(){
        return  this.responseObject;
    }

    public void updateResponse(JSONObject responseData){

        ResponseData responseToUpdate;
        String responseStringData=responseData.toString();
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        responseToUpdate = gson.fromJson(responseStringData, ResponseData.class);
        responseObject.getNewsData().addAll(responseToUpdate.getNewsData());
        if(baseAdapterListener !=null){
            baseAdapterListener.notifyListener();
        }
    }

}
