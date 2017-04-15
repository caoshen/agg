package xyz.dcme.agg.ui.topic;

import android.content.Context;
import android.view.View;

import xyz.dcme.agg.ui.postdetail.PostDetailActivity;

class OnTopicClickListener implements View.OnClickListener {
    private Context mContext;
    private String mUrl;

    public OnTopicClickListener(Context context, String url) {
        mContext = context;
        mUrl = url;
    }

    @Override
    public void onClick(View v) {
        PostDetailActivity.startActivity(mContext, mUrl);
    }
}
