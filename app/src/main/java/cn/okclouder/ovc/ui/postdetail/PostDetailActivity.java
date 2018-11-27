package cn.okclouder.ovc.ui.postdetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

import cn.okclouder.ovc.R;
import cn.okclouder.ovc.database.HistoryInfo;
import cn.okclouder.ovc.database.helper.HistoryDbHelper;
import cn.okclouder.ovc.model.Post;
import cn.okclouder.ovc.util.ActivityUtils;
import cn.okclouder.library.base.BaseActivity;


public class PostDetailActivity extends BaseActivity {

    public static final String KEY_POST_DETAIL_URL = "key_post_detail_url";
    private static final String KEY_POST = "key_post";
    private String mUrl;
    private Post mPost;

    public static void startActivity(Context context, String url) {
        Intent intent = new Intent(context, PostDetailActivity.class);
        intent.putExtra(KEY_POST_DETAIL_URL, url);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, Post post) {
        Intent intent = new Intent(context, PostDetailActivity.class);
        intent.putExtra(KEY_POST, post);
        intent.putExtra(KEY_POST_DETAIL_URL, post.link);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_post_detail;
    }

    @Override
    public void initView() {

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
        FragmentManager fm = getSupportFragmentManager();
        PostDetailFragment postFragment = (PostDetailFragment) fm.findFragmentById(R.id.post_detail_main);
        if (postFragment == null) {
            postFragment = PostDetailFragment.newInstance(mUrl);
            ActivityUtils.addFragmentToActivity(fm, postFragment, R.id.post_detail_main);
        }
    }
}
