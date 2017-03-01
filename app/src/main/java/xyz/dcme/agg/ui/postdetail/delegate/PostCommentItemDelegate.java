package xyz.dcme.agg.ui.postdetail.delegate;

import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import xyz.dcme.agg.R;
import xyz.dcme.agg.model.PostComment;
import xyz.dcme.agg.ui.postdetail.PostDetailType;

public class PostCommentItemDelegate implements ItemViewDelegate<PostDetailType> {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_post_comment;
    }

    @Override
    public boolean isForViewType(PostDetailType item, int position) {
        return item.getPostType() == PostDetailType.COMMENT_TYPE;
    }

    @Override
    public void convert(ViewHolder holder, PostDetailType item, int position) {
        if (item instanceof PostComment) {
            holder.setText(R.id.commenter_name, ((PostComment) item).userName);
            holder.setText(R.id.comment_content, ((PostComment) item).content);
        }
    }
}
