package cn.okclouder.ovc.ui.reply;

import android.content.Context;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import cn.okclouder.ovc.R;

class ReplyAdapter extends CommonAdapter<Reply> {
    public ReplyAdapter(Context context, int layoutId, List<Reply> data) {
        super(context, layoutId, data);
    }

    @Override
    protected void convert(ViewHolder holder, Reply reply, int position) {
        holder.setText(R.id.reply_title, reply.title);
        holder.setText(R.id.reply_content, reply.content);

        holder.setOnClickListener(R.id.reply_item, new OnReplyClickListener(mContext, reply.detailUrl));

    }
}
