package xyz.dcme.agg.ui.postdetail;

import android.annotation.SuppressLint;
import android.content.Context;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

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

    @SuppressLint("SetJavaScriptEnabled")
    @SuppressWarnings("deprecation")
    @Override
    public void convert(ViewHolder holder, PostDetailItem postDetailItem, int position) {

        holder.setText(R.id.post_detail_user, postDetailItem.getUserName());
        WebView content = holder.getView(R.id.post_detail_content);
        content.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT));
        WebSettings settings = content.getSettings();
        settings.setJavaScriptEnabled(true);
        content.loadData(postDetailItem.getContent(), "text/html", "utf-8");

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
