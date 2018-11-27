package cn.okclouder.ovc.ui.hot;

import android.content.Context;
import android.widget.ImageView;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import cn.okclouder.ovc.R;
import cn.okclouder.ovc.model.Post;
import cn.okclouder.ovc.ui.post.OnRvItemListener;
import cn.okclouder.library.util.ImageLoader;

public class HotListAdapter extends CommonAdapter<Post> {
    public HotListAdapter(Context context, int layoutId, List<Post> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, Post post, int position) {
        initHotItemView(holder, post);
    }

    private void initHotItemView(ViewHolder holder, Post post) {
        holder.setText(R.id.post_content, post.title);
        holder.setText(R.id.post_user_name, post.userName);

        ImageView iv = holder.getView(R.id.post_avatar);
        ImageLoader.displayCircle(mContext, iv, post.avatarUrl);

        holder.setOnClickListener(R.id.post_item, new OnRvItemListener(mContext, post));
    }
}
