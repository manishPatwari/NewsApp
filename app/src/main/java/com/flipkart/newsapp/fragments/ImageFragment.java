package com.flipkart.newsapp.fragments;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.flipkart.newsapp.GalleryActivity;
import com.flipkart.newsapp.R;
import com.flipkart.newsapp.adapters.ImageListAdapter;
import com.flipkart.newsapp.config.AppPreferences;
import com.flipkart.newsapp.listener.EndlessListScrollListener;
import com.flipkart.newsapp.model.FlickrResponse;
import com.flipkart.newsapp.model.JsonPhotoDataProvider;
import com.flipkart.newsapp.network.request.common.ImageResponse;
import com.flipkart.newsapp.network.request.common.NetworkRequestQueue;
import com.flipkart.newsapp.utils.GSONBuilderHelper;
import com.flipkart.newsapp.utils.HttpsTrustManager;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by amit.rs on 11/05/15.
 */
public class ImageFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ListView mListView;
    private Activity mActivity;

    private RequestQueue mRequestQueue;
    NetworkImageView mNetworkImageView;
    ImageLoader mImageLoader;
    ListView mImageListView;
    private String mJsonResponse;
    FlickrResponse mFlickrRespons;
    private static String TAG = "FLICKR";
    private static String FLICKR_API_URL;
    Context mContext;
    ProgressBar mProgressBar;
    private int totalNumberOfItems;
    private NetworkRequestQueue mNetworkRequestQueue;
    private int lastPosition = -1;
    private ImageListAdapter mImageListAdapter;
    GSONBuilderHelper mGsonBuilderHelper;
    JsonPhotoDataProvider mJsonPhotoDataProvider;
    ImageResponse mImageResponse;
    SwipeRefreshLayout mSwipeRefreshLayout;

    private OnFragmentInteractionListener mListener;

    public ImageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        mActivity = getActivity();
        mImageListView = (ListView) view.findViewById(R.id.list_fragment);
        mContext = mActivity.getApplicationContext();
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressbar);

        mImageResponse = ImageResponse.getInstance();
        Log.d(TAG, "mProgressBar : " + mProgressBar);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.image_fragment_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d(TAG,"onRefresh Called");
                mSwipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);
            }
        });
        mGsonBuilderHelper = new GSONBuilderHelper();
        FLICKR_API_URL = AppPreferences.FLICKR_API_URL;
        Intent intent = mActivity.getIntent();
        FLICKR_API_URL = FLICKR_API_URL + "&text=car";

        if (intent != null) {
            Log.d(TAG, intent.toString());
            if (null != intent.getExtras()) {
                String searchString = intent.getStringExtra(SearchManager.QUERY);
                if (searchString != null)
                    FLICKR_API_URL = FLICKR_API_URL + "&text=" + URLEncoder.encode(searchString);
            }
        }
        mNetworkRequestQueue = NetworkRequestQueue.getInstance();
        mNetworkRequestQueue.initialize(mContext);
        mRequestQueue = mNetworkRequestQueue.getRequestQueue();
        mImageLoader = mNetworkRequestQueue.getImageLoader();

        HttpsTrustManager.allowAllSSL();

        mNetworkRequestQueue.makeStringRequest(FLICKR_API_URL + "&text=car");
        mNetworkRequestQueue.setmVolleyStringResponseListener(new NetworkRequestQueue.VolleyStringResponseListener() {
            @Override
            public void onVolleyResponse(String response) {
                FlickrResponse flickrResponse = mGsonBuilderHelper.getJsonFromString(response, FlickrResponse.class);
                mFlickrRespons = flickrResponse;
                mJsonResponse = response;
                mImageResponse.setFlickrResponse(flickrResponse);
                Log.d(TAG, flickrResponse + "");
                initListViewAdapter(mContext);
            }

            @Override
            public void onVolleyErrorResponse(VolleyError volleyError) {
                Log.d(TAG, "Error Occurred :" + volleyError);
                Cache.Entry entry = mRequestQueue.getCache().get(FLICKR_API_URL + "&text=car");
                if (entry != null) {
                    try {
                        String data = new String(entry.data, "UTF-8");
                        System.out.println(" Reading from Cache Data");
                        Log.d(TAG, "Reading from Cache Data");
                        FlickrResponse flickrResponse = mGsonBuilderHelper.getJsonFromString(data, FlickrResponse.class);
                        mFlickrRespons = flickrResponse;
                        mJsonResponse = data;
                        mImageResponse.setFlickrResponse(flickrResponse);
                        initListViewAdapter(mContext);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.d(TAG, "Cache Data is NULL");
                }

            }
        });


        mImageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(mContext, "You selected : " + (i + 1) + " item", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, GalleryActivity.class);
                intent.putExtra("ListItemPosition", i);
                intent.putExtra("TotalNumberOfItems", totalNumberOfItems);
                /*Log.d(TAG, "mJsonStringReponse before sending data : " + mJsonResponse);
                intent.putExtra("JsonResponseString", mJsonResponse);*/
                startActivity(intent);
                mActivity.overridePendingTransition(R.anim.slide_in_right, R.anim.abc_fade_out);
            }
        });


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(TAG, "onAttach()");

        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach()");

        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }


    public void initListViewAdapter(Context context) {
        Log.d(TAG, "Init ListView Adapter...");
        mImageListAdapter = new ImageListAdapter(context, mFlickrRespons);
        mImageListView.setAdapter(mImageListAdapter);
        mImageListView.setOnScrollListener(new EndlessListScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                Log.d(TAG, "Loading more data... page = "+ page);
                loadNextPageData(page);
                this.isLoading=false;
            }
        });
        totalNumberOfItems = mImageListView.getCount();

        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressBar.setVisibility(View.GONE);
            }
        });

    }

    private void loadNextPageData(int page) {
        mNetworkRequestQueue.makeStringRequest(FLICKR_API_URL + "&text=car&page="+page);
        mNetworkRequestQueue.setmVolleyStringResponseListener(new NetworkRequestQueue.VolleyStringResponseListener() {
            @Override
            public void onVolleyResponse(String response) {
                FlickrResponse flickrResponse = mGsonBuilderHelper.getJsonFromString(response, FlickrResponse.class);
                mFlickrRespons = flickrResponse;
                mJsonResponse = response;
                mImageResponse.updateFlickrResponse(flickrResponse);
//                Log.d(TAG, "For page  = "+ 2+" Response is :"+flickrResponse + "");
//                initListViewAdapter(mContext);
                mImageListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onVolleyErrorResponse(VolleyError volleyError) {
                Log.d(TAG, "Error Occurred :" + volleyError);
                Cache.Entry entry = mRequestQueue.getCache().get(FLICKR_API_URL + "&text=car");
                if (entry != null) {
                    try {
                        String data = new String(entry.data, "UTF-8");
                        System.out.println(" Reading from Cache Data");
                        Log.d(TAG, "Reading from Cache Data");
                        FlickrResponse flickrResponse = mGsonBuilderHelper.getJsonFromString(data, FlickrResponse.class);
                        mFlickrRespons = flickrResponse;
                        mJsonResponse = data;
//                        mImageResponse.setFlickrResponse(flickrResponse);
//                        initListViewAdapter(mContext);
                        mImageResponse.updateFlickrResponse(flickrResponse);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.d(TAG, "Cache Data is NULL");
                }

            }
        });

    }
}

