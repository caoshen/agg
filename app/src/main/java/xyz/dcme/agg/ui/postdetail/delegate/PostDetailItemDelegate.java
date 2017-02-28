package xyz.dcme.agg.ui.postdetail.delegate;

import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import xyz.dcme.agg.R;
import xyz.dcme.agg.model.PostDetail;

public class PostDetailItemDelegate implements ItemViewDelegate<PostDetail> {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_post_detail;
    }

    @Override
    public boolean isForViewType(PostDetail item, int position) {
        return true;
    }

    @Override
    public void convert(ViewHolder holder, PostDetail postDetail, int position) {
        holder.setText(R.id.post_detail_title, postDetail.title);
        holder.setText(R.id.post_detail_content, postDetail.content);
    }
}
