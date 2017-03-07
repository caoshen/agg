package xyz.dcme.agg.ui.postdetail;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import xyz.dcme.agg.R;
import xyz.dcme.agg.ui.postdetail.data.PostContent;
import xyz.dcme.agg.ui.postdetail.data.PostDetailItem;


public class PostContentDelegate implements ItemViewDelegate<PostDetailItem> {

    private final Context mContext;

    public PostContentDelegate(Context context) {
        mContext = context;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_post_content;
    }

    @Override
    public boolean isForViewType(PostDetailItem item, int position) {
        return item != null && (item instanceof PostContent);
    }

    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
    @SuppressWarnings("deprecation")
    @Override
    public void convert(ViewHolder holder, PostDetailItem postDetailItem, int position) {

        LinearLayout container = holder.getView(R.id.post_detail_container);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        container.setLayoutParams(params);

        holder.setText(R.id.post_detail_user, postDetailItem.getUserName());

        WebView webView = holder.getView(R.id.post_detail_content);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        webView.setLayoutParams(lp);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        WebSettings settings = webView.getSettings();

        // User settings
        settings.setJavaScriptEnabled(true);
        settings.setLoadsImagesAutomatically(true);
        // Auto fit screen
        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setDomStorageEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setAllowFileAccess(true);
        settings.setDatabaseEnabled(true);

        webView.setHorizontalScrollBarEnabled(false);
        webView.addJavascriptInterface(this, "App");

        String detail = postDetailItem.getContent();
        webView.loadDataWithBaseURL(null, detail, "text/html", "UTF-8", null);

        if (postDetailItem instanceof PostContent) {
            holder.setText(R.id.post_detail_title, ((PostContent) postDetailItem).getTitle());
        }
        ImageView avatar = holder.getView(R.id.post_detail_avatar);

        Glide.with(mContext)
                .load(postDetailItem.getAvatar())
                .centerCrop()
                .placeholder(R.drawable.ic_default_avatar)
                .crossFade()
                .into(avatar);
    }
}
