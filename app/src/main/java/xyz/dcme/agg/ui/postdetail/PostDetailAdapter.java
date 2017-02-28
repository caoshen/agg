package xyz.dcme.agg.ui.postdetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;


public class PostDetailAdapter extends CommonAdapter {
    private static final int ITEM_TYPE_POST_DETAIL = 10;
    private static final int ITEM_TYPE_POST_COMMENT = 20;

    public PostDetailAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, Object o, int position) {

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }
}
