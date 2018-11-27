package cn.okclouder.ovc.ui.post;

import android.content.Context;
import android.view.View;

import cn.okclouder.ovc.frag.article.ArticleActivity;
import cn.okclouder.ovc.model.Post;

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
