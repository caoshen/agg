package cn.okclouder.ovc.ui.post;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import cn.okclouder.ovc.R;
import cn.okclouder.ovc.model.Post;
import cn.okclouder.library.util.ImageLoader;

public class PostCommonAdapter extends CommonAdapter<Post> {
    public PostCommonAdapter(Context context, int layoutId, List<Post> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, Post post, int position) {
        holder.setText(R.id.post_content, post.title);
        holder.setText(R.id.post_user_name, post.userName);
        holder.setText(R.id.post_last_visit_time, post.lastVisitTime);
        if (TextUtils.isEmpty(post.commentCount)) {
            post.commentCount = "0";
        }
        String commentCount = mContext.getString(R.string.comment_count, post.commentCount);
        holder.setText(R.id.post_comment_count, commentCount);

        ImageView avatar = holder.getView(R.id.post_avatar);
        ImageLoader.displayCircle(mContext, avatar, post.avatarUrl);

        holder.setOnClickListener(R.id.post_item, new OnRvItemListener(mContext, post));
    }


}
