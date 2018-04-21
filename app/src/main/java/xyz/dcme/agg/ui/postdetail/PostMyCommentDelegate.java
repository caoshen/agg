package xyz.dcme.agg.ui.postdetail;

import android.content.Context;

import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import xyz.dcme.agg.R;
import xyz.dcme.agg.ui.postdetail.data.PostDetailItem;
import xyz.dcme.agg.ui.postdetail.data.PostMyComment;

public class PostMyCommentDelegate implements ItemViewDelegate<PostDetailItem> {

    public PostMyCommentDelegate(Context context) {
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

    }
}
