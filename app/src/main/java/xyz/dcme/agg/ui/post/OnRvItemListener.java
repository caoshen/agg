package xyz.dcme.agg.ui.post;

import android.content.Context;
import android.view.View;

import xyz.dcme.agg.frag.article.ArticleActivity;
import xyz.dcme.agg.model.Post;

public class OnRvItemListener implements View.OnClickListener {

    private Post mPost;
    private Context mContext;

    public OnRvItemListener(Context context, Post post) {
        mContext = context;
        mPost = post;
    }

    @Override
    public void onClick(View view) {
        ArticleActivity.startActivity(mContext, mPost);
    }
}
