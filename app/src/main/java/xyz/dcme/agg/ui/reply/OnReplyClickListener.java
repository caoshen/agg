package xyz.dcme.agg.ui.reply;

import android.content.Context;
import android.view.View;

import xyz.dcme.agg.frag.article.ArticleActivity;

public class OnReplyClickListener implements View.OnClickListener {
    private Context mContext;
    private String mUrl;

    public OnReplyClickListener(Context context, String url) {
        mContext = context;
        mUrl = url;
    }

    @Override
    public void onClick(View v) {
        ArticleActivity.startActivity(mContext, mUrl);
    }
}
