package xyz.dcme.agg.ui.postdetail.delegate;

import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import xyz.dcme.agg.R;
import xyz.dcme.agg.model.PostDetail;
import xyz.dcme.agg.ui.postdetail.PostDetailType;

public class PostDetailItemDelegate implements ItemViewDelegate<PostDetailType> {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_post_detail;
    }

    @Override
    public boolean isForViewType(PostDetailType item, int position) {
        return item.getPostType() == PostDetailType.CONTENT_TYPE;
    }

    @Override
    public void convert(ViewHolder holder, PostDetailType postDetail, int position) {
        if (postDetail instanceof PostDetail) {
            holder.setText(R.id.post_detail_title, ((PostDetail) postDetail).title);
            holder.setText(R.id.post_detail_content, ((PostDetail) postDetail).content);
        }
    }
}
