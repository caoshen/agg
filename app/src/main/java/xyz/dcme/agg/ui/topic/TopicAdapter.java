package xyz.dcme.agg.ui.topic;

import android.content.Context;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import xyz.dcme.agg.R;

class TopicAdapter extends CommonAdapter<Topic> {
    public TopicAdapter(Context context, int layoutId, List<Topic> data) {
        super(context, layoutId, data);
    }

    @Override
    protected void convert(ViewHolder holder, Topic topic, int position) {
        holder.setText(R.id.topic_last_touched, topic.lastTouched);
        holder.setText(R.id.topic_title, topic.title);
        holder.setText(R.id.topic_reply_count, topic.replyCount);
    }
}
