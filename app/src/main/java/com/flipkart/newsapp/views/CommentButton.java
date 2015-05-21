package com.flipkart.newsapp.views;

import android.app.Dialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.flipkart.newsapp.R;

/**
 * Created by manish.patwari on 5/21/15.
 */
public class CommentButton extends LinearLayout {
    private Context mContext;
    private Boolean isDialogBoxCreated = false;
    private  Dialog dialog;
    public CommentButton(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public CommentButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public CommentButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    public CommentButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        init();
    }

    public void init(){

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        Button btn = new Button(mContext);

        btn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.comment,0,0,0);

        btn.setLayoutParams(params);
        btn.setPadding(25,30,25,15);
        btn.setText(" Comments");
        addView(btn);
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isDialogBoxCreated)
                {
                    createCommentDialog();
                }
                dialog.show();
            }
        });

    }

    public void createCommentDialog(){
        dialog = new Dialog(mContext, android.R.style.Theme_Material_Light_NoActionBar_Fullscreen);
        dialog.setContentView(R.layout.comment_dialog);
        dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
        dialog.show();
        ImageButton closeDialogButton = (ImageButton) dialog.findViewById(R.id.close_button);
        closeDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        isDialogBoxCreated = true;
    }
}
