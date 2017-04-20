package xyz.dcme.agg.ui.postdetail;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import xyz.dcme.agg.R;
import xyz.dcme.agg.account.AccountInfo;
import xyz.dcme.agg.account.OnAccountClickListener;
import xyz.dcme.agg.ui.postdetail.data.PostDetailItem;
import xyz.dcme.agg.ui.postdetail.data.PostMyComment;
import xyz.dcme.agg.util.transformation.CircleTransformation;

class PostMyCommentDelegate implements ItemViewDelegate<PostDetailItem> {
    private Context mContext;
    private View.OnClickListener mOnMyCommentClickListener;

    public PostMyCommentDelegate(Context context) {
        mContext = context;
    }

    public void setOnMyCommentClickListener(View.OnClickListener onMyCommentClickListener) {
        mOnMyCommentClickListener = onMyCommentClickListener;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_post_my_comment;
    }

    @Override
    public boolean isForViewType(PostDetailItem item, int position) {
        return item != null && (item instanceof PostMyComment);
    }

    @Override
    public void convert(ViewHolder holder, PostDetailItem item, int position) {
        if (item instanceof PostMyComment) {
            holder.setText(R.id.total_comment_count, ((PostMyComment) item).getTotalCount());
        }

        if (mOnMyCommentClickListener != null) {
            holder.setOnClickListener(R.id.send_comment, mOnMyCommentClickListener);
        }

        if (TextUtils.isEmpty(item.getUserName()) || TextUtils.isEmpty(item.getAvatar())) {
            return;
        }

        ImageView avatar = holder.getView(R.id.my_avatar);

        Glide.with(mContext)
                .load(item.getAvatar())
                .placeholder(R.drawable.ic_default_avatar)
                .transform(new CircleTransformation(mContext))
                .into(avatar);

        AccountInfo info = new AccountInfo();
        info.setUserName(item.getUserName());
        info.setAvatarUrl(item.getAvatar());
        OnAccountClickListener listener = new OnAccountClickListener(mContext, info);

        holder.setOnClickListener(R.id.my_avatar, listener);
    }
}
