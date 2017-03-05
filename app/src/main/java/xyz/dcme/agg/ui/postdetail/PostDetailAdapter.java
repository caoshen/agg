package xyz.dcme.agg.ui.postdetail;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import xyz.dcme.agg.R;
import xyz.dcme.agg.model.PostComment;

public class PostDetailAdapter extends CommonAdapter<PostComment> {
    public PostDetailAdapter(Context context, int layoutId, List<PostComment> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, PostComment postComment, int position) {
        holder.setText(R.id.comment_name, postComment.userName);
        holder.setText(R.id.comment_content, postComment.content);
        ImageView avatar = holder.getView(R.id.comment_avatar);

        Glide.with(mContext)
                .load(postComment.avatar)
                .centerCrop()
                .placeholder(R.drawable.ic_default_avatar)
                .crossFade()
                .into(avatar);
    }
}
