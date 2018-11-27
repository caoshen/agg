package cn.okclouder.ovc.ui.notify;

import android.content.Context;
import android.view.View;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import cn.okclouder.ovc.R;
import cn.okclouder.ovc.ui.postdetail.PostDetailActivity;


public class MessageAdapter extends CommonAdapter<Message> {
    public MessageAdapter(Context context, int layoutId, List<Message> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, final Message message, int position) {
        holder.setText(R.id.message_title, message.getTitle());
        holder.setText(R.id.message_content, message.getContent());

        holder.setOnClickListener(R.id.post_item, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostDetailActivity.startActivity(mContext, message.getLink());
            }
        });
    }
}
