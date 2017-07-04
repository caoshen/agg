package xyz.dcme.agg.ui.publish;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import xyz.dcme.agg.R;
import xyz.dcme.agg.ui.BaseFragment;
import xyz.dcme.agg.util.AccountUtils;
import xyz.dcme.agg.util.Constants;
import xyz.dcme.agg.util.HtmlUtils;
import xyz.dcme.agg.util.transformation.CircleTransformation;

public class PreviewFragment extends BaseFragment {
    public static final String LOG_TAG = "PreviewFragment";
    private static final String KEY_TITLE = "preview_title";
    private static final String KEY_CONTENT = "preview_content";
    private String mTitle;
    private String mContent;
    private String mUserName;
    private String mImageUrl;

    public static Fragment newInstance(String title, String content) {
        Fragment fragment = new PreviewFragment();
        Bundle args = new Bundle();
        args.putString(KEY_TITLE, title);
        args.putString(KEY_CONTENT, content);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAccount();
        initArgs();
    }

    private void initArgs() {
        Bundle args = getArguments();
        if (args != null) {
            mTitle = args.getString(KEY_TITLE);
            mContent = args.getString(KEY_CONTENT);
        }
    }

    private void initAccount() {
        mUserName = AccountUtils.getUserName(getContext());
        mImageUrl = AccountUtils.getImageUrl(getContext());
    }

    @Override
    protected void initView() {
        TextView userName = (TextView) mRootView.findViewById(R.id.preview_name);
        userName.setText(mUserName);
        TextView createTime = (TextView) mRootView.findViewById(R.id.preview_create_time);
        createTime.setText(R.string.publish_rightnow);

        WebView webView = (WebView) mRootView.findViewById(R.id.preview_content);
        HtmlUtils.initWebSettings(webView);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        webView.setLayoutParams(lp);
        webView.setDrawingCacheEnabled(true);
        webView.loadDataWithBaseURL(null, HtmlUtils.makeHtml(mContent), "text/html", "UTF-8", null);

        TextView title = (TextView) mRootView.findViewById(R.id.preview_title);
        title.setText(mTitle);

        TextView click = (TextView) mRootView.findViewById(R.id.preview_click_count);
        click.setText(getString(R.string.post_click_count, Constants.ONE));

        TextView fav = (TextView) mRootView.findViewById(R.id.preview_fav_count);
        fav.setText(getString(R.string.fav_count, Constants.ONE));

        TextView like = (TextView) mRootView.findViewById(R.id.preview_like_count);
        like.setText(getString(R.string.like_count, Constants.ONE));

        ImageView avatar = (ImageView) mRootView.findViewById(R.id.preview_avatar);

        Glide.with(this)
                .load(mImageUrl)
                .transform(new CircleTransformation(getContext()))
                .into(avatar);
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_preview;
    }
}
