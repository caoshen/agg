package xyz.dcme.agg.ui.news;

import android.content.Context;
import android.widget.ImageView;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import xyz.dcme.agg.R;
import xyz.dcme.agg.model.Post;
import xyz.dcme.agg.ui.post.PostCommonAdapter;
import xyz.dcme.library.util.ImageLoader;

public class NewsListAdapter extends CommonAdapter<Post> {
    public NewsListAdapter(Context context, int layoutId, List<Post> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, Post post, int position) {
        initPostItemView(holder, post);
    }

    private void initPostItemView(ViewHolder holder, Post post) {
        holder.setText(R.id.post_content, post.title);
        holder.setText(R.id.post_user_name, post.userName);
        holder.setText(R.id.post_last_visit_time, post.lastVisitTime);

        String commentCount = mContext.getString(R.string.comment_count, post.commentCount);
        holder.setText(R.id.post_comment_count, commentCount);

        ImageView iv = holder.getView(R.id.post_avatar);
        ImageLoader.displayCircle(mContext, iv, post.avatarUrl);

        holder.setOnClickListener(R.id.post_item, new PostCommonAdapter.OnRvItemListener(mContext, post));
    }
}
