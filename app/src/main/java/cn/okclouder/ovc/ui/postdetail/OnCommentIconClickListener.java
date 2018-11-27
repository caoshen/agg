package cn.okclouder.ovc.ui.postdetail;

import android.text.TextUtils;
import android.view.View;

import cn.okclouder.ovc.util.Constants;

public class OnCommentIconClickListener implements View.OnClickListener {

    private final OnCommentListener mListener;
    private String mUsername;

    public OnCommentIconClickListener(String username, OnCommentListener listener) {
        mUsername = username;
        mListener = listener;
    }

    @Override
    public void onClick(View v) {
        if (mListener != null && !TextUtils.isEmpty(mUsername)) {
            mListener.onCommentToFloor(Constants.AT + mUsername + Constants.SPACE);
        }
    }
}
