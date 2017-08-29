package xyz.dcme.agg.ui.history;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import xyz.dcme.agg.R;
import xyz.dcme.agg.database.HistoryInfo;
import xyz.dcme.agg.ui.postdetail.PostDetailActivity;
import xyz.dcme.library.util.ImageLoader;

public class HistoryAdapter extends CommonAdapter<HistoryInfo> {
    public HistoryAdapter(Context context, int layoutId, List<HistoryInfo> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, final HistoryInfo historyInfo, int position) {
        holder.setText(R.id.post_content, historyInfo.title);
        holder.setText(R.id.post_user_name, historyInfo.author);
        ImageView avatar = holder.getView(R.id.post_avatar);
        ImageLoader.displayCircle(mContext, avatar, historyInfo.avatar);

        holder.setOnClickListener(R.id.post_item, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostDetailActivity.startActivity(mContext, historyInfo.link);
            }
        });
    }
}
