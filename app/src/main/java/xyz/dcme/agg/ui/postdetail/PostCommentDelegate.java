package xyz.dcme.agg.ui.postdetail;


import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import xyz.dcme.agg.R;
import xyz.dcme.agg.account.AccountInfo;
import xyz.dcme.agg.account.OnAccountClickListener;
import xyz.dcme.agg.ui.postdetail.data.PostComment;
import xyz.dcme.agg.ui.postdetail.data.PostDetailItem;
import xyz.dcme.agg.util.transformation.CircleTransformation;

public class PostCommentDelegate implements ItemViewDelegate<PostDetailItem> {

    private final Context mContext;

    public PostCommentDelegate(Context context) {
        mContext = context;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_post_comment;
    }

    @Override
    public boolean isForViewType(PostDetailItem item, int position) {
        return item != null && (item instanceof PostComment);
    }

    @Override
    public void convert(ViewHolder holder, PostDetailItem item, int position) {
        holder.setText(R.id.comment_name, item.getUserName());
        holder.setText(R.id.comment_content, item.getContent());
        holder.setText(R.id.comment_number, mContext.getString(R.string.x_floor, (position - 1) + ""));
        holder.setText(R.id.comment_create_time, item.getCreateTime());
        ImageView avatar = holder.getView(R.id.comment_avatar);

        Glide.with(mContext)
                .load(item.getAvatar())
                .placeholder(R.drawable.ic_default_avatar)
                .transform(new CircleTransformation(mContext))
                .into(avatar);

        AccountInfo info = new AccountInfo();
        info.setUserName(item.getUserName());
        info.setAvatarUrl(item.getAvatar());
        OnAccountClickListener listener = new OnAccountClickListener(mContext, info);

        holder.setOnClickListener(R.id.comment_avatar, listener);
        holder.setOnClickListener(R.id.comment_name, listener);
    }

}
