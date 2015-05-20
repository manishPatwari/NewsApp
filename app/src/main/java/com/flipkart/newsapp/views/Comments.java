package com.flipkart.newsapp.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.flipkart.newsapp.R;
import com.flipkart.newsapp.adapters.CommentAdapter;
import com.flipkart.newsapp.controller.CommentsCtrl;

/**
 * Created by manish.patwari on 5/18/15.
 */
public class Comments extends LinearLayout {

    private Context mContext;
    private Button mPostBtn;
    private EditText mCommentText;
    private CommentsCtrl mCommentsCtrl;
    private CommentAdapter mCommentAdapter;
    private ListView mCommentList;

    public Comments(Context context) {
        super(context);
        mContext = context;
        initialize();
    }

    public Comments(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initialize();
    }

    public Comments(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initialize();
    }

    public Comments(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        initialize();
    }


    private void initialize()
    {
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View mCommentLayout = inflater.inflate(R.layout.comment_layout,this);

            mCommentText = (EditText) mCommentLayout.findViewById(R.id.comment_text);
        mPostBtn = (Button) mCommentLayout.findViewById(R.id.comment_post_btn);
        mCommentList = (ListView) mCommentLayout.findViewById(R.id.comment_list);

        mCommentsCtrl = new CommentsCtrl(new CommentsCtrl.Listeners() {
            @Override
            public void dataUpdated() {
                mCommentAdapter.updateData();
            }
        });

        mCommentAdapter = new CommentAdapter(mContext,inflater,mCommentsCtrl);

        mCommentList.setAdapter(mCommentAdapter);

        mPostBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String commentText = mCommentText.getText().toString();
                mCommentsCtrl.postComment(commentText, "Manish Patwari", "http://lh6.googleusercontent.com/-K5iaLXoeMmw/AAAAAAAAAAI/AAAAAAAAABE/iQIZJkprsPk/s48-c-k-no/photo.jpg");
                mCommentText.setText("");
            }
        });

        /*mCommentList.setOnTouchListener(new OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });*/
    }


}
