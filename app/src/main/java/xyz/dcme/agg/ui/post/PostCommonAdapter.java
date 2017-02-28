package xyz.dcme.agg.ui.post;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import xyz.dcme.agg.R;
import xyz.dcme.agg.model.Post;

public class PostCommonAdapter extends CommonAdapter<Post> {
    public PostCommonAdapter(Context context, int layoutId, List<Post> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, Post post, int position) {
        holder.setText(R.id.post_content, post.title);
        ImageView avatar = holder.getView(R.id.post_avatar);
        Glide.with(mContext)
                .load(post.avatarUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_default_avatar)
                .crossFade()
                .into(avatar);
    }
}
