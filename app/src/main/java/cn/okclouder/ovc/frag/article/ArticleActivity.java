package cn.okclouder.ovc.frag.article;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

import cn.okclouder.ovc.R;
import cn.okclouder.ovc.base.BaseFragmentActivity;
import cn.okclouder.ovc.database.HistoryInfo;
import cn.okclouder.ovc.database.helper.HistoryDbHelper;
import cn.okclouder.ovc.model.Post;


public class ArticleActivity extends BaseFragmentActivity {
    public static final String KEY_POST_DETAIL_URL = "key_post_detail_url";
    private static final String KEY_POST = "key_post";
    private String mUrl;
    private Post mPost;

    public static void startActivity(Context context, String url) {
        Intent intent = new Intent(context, ArticleActivity.class);
        intent.putExtra(KEY_POST_DETAIL_URL, url);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, Post post) {
        Intent intent = new Intent(context, ArticleActivity.class);
        intent.putExtra(KEY_POST, post);
        intent.putExtra(KEY_POST_DETAIL_URL, post.link);
        context.startActivity(intent);
    }

    @Override
    protected int getContextViewId() {
        return R.id.id_article_activity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QMUIStatusBarHelper.setStatusBarLightMode(this);
        initData();
        initFragment();
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            mUrl = intent.getStringExtra(KEY_POST_DETAIL_URL);
            mPost = intent.getParcelableExtra(KEY_POST);
        }
        addHistory();
    }

    private void addHistory() {
        if (null != mPost) {
            HistoryDbHelper.getInstance().insertHistory(this, new HistoryInfo(mPost));
        }
    }

    private void initFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(getContextViewId(), ArticleFragment.newInstance(mUrl), ArticleFragment.class.getSimpleName())
                .addToBackStack(ArticleFragment.class.getSimpleName())
                .commit();
    }
}
