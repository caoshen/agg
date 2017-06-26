package xyz.dcme.agg.ui.postdetail;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import xyz.dcme.agg.R;
import xyz.dcme.agg.ui.postdetail.data.PostContent;
import xyz.dcme.agg.ui.postdetail.data.PostDetailItem;
import xyz.dcme.agg.util.HtmlUtils;
import xyz.dcme.agg.util.transformation.CircleTransformation;


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
        holder.setText(R.id.post_detail_create_time, postDetailItem.getCreateTime());

        WebView webView = holder.getView(R.id.post_detail_content);
        HtmlUtils.initWebSettings(webView);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        webView.setLayoutParams(lp);

        String detail = postDetailItem.getContent();
        webView.setDrawingCacheEnabled(true);

        webView.loadDataWithBaseURL(null, HtmlUtils.makeHtml(detail), "text/html", "UTF-8", null);

        if (postDetailItem instanceof PostContent) {
            holder.setText(R.id.post_detail_title, ((PostContent) postDetailItem).getTitle());
            holder.setText(R.id.post_detail_click_count, ((PostContent) postDetailItem).getClickCount());
            holder.setText(R.id.post_detail_fav_count, ((PostContent) postDetailItem).getFavCount());
            holder.setText(R.id.post_detail_like_count, ((PostContent) postDetailItem).getLikeCount());
            holder.setText(R.id.post_detail_node, ((PostContent) postDetailItem).getNode());
        }
        ImageView avatar = holder.getView(R.id.post_detail_avatar);

        Glide.with(mContext)
                .load(postDetailItem.getAvatar())
                .placeholder(R.drawable.ic_default_avatar)
                .transform(new CircleTransformation(mContext))
                .into(avatar);
    }
}
