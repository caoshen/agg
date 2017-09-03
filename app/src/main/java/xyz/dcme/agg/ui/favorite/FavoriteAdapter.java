package xyz.dcme.agg.ui.favorite;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import xyz.dcme.agg.R;
import xyz.dcme.agg.model.Post;
import xyz.dcme.agg.ui.post.PostCommonAdapter;
import xyz.dcme.library.util.CircleTransformation;

class FavoriteAdapter extends CommonAdapter<Post> {
    public FavoriteAdapter(Context context, int layoutId, List<Post> data) {
        super(context, layoutId, data);
    }

    @Override
    protected void convert(ViewHolder holder, Post post, int position) {
        holder.setText(R.id.post_content, post.title);
        holder.setText(R.id.post_user_name, post.userName);
        holder.setText(R.id.post_last_visit_time, post.lastVisitTime);
        String commentCount = mContext.getString(R.string.comment_count, post.commentCount);
        holder.setText(R.id.post_comment_count, commentCount);
        ImageView avatar = holder.getView(R.id.post_avatar);
        Glide.with(mContext)
                .load(post.avatarUrl)
                .transform(new CircleTransformation(mContext))
                .into(avatar);
        holder.setOnClickListener(R.id.post_item, new PostCommonAdapter.OnRvItemListener(mContext, post));
    }
}
