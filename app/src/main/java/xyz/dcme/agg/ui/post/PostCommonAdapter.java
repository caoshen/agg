package xyz.dcme.agg.ui.post;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import xyz.dcme.agg.R;
import xyz.dcme.agg.model.Post;
import xyz.dcme.agg.ui.postdetail.PostDetailActivity;
import xyz.dcme.library.util.CircleTransformation;

public class PostCommonAdapter extends CommonAdapter<Post> {
    public PostCommonAdapter(Context context, int layoutId, List<Post> datas) {
        super(context, layoutId, datas);
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
                .placeholder(R.drawable.ic_default_avatar)
                .transform(new CircleTransformation(mContext))
                .into(avatar);
        holder.setOnClickListener(R.id.post_item, new OnRvItemListener(mContext, post));
    }


    public static class OnRvItemListener implements View.OnClickListener {

        private Post mPost;
        private Context mContext;

        public OnRvItemListener(Context context, Post post) {
            mContext = context;
            mPost = post;
        }

        @Override
        public void onClick(View view) {
            PostDetailActivity.startActivity(mContext, mPost);
        }
    }
}
