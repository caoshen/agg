package xyz.dcme.agg.ui.topic;

import android.content.Context;
import android.widget.ImageView;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import xyz.dcme.agg.R;
import xyz.dcme.agg.util.ImageLoader;

public class TopicAdapter extends CommonAdapter<Topic> {
    public TopicAdapter(Context context, int layoutId, List<Topic> data) {
        super(context, layoutId, data);
    }

    @Override
    protected void convert(ViewHolder holder, Topic topic, int position) {
        holder.setText(R.id.topic_author_name, topic.authorName);
        holder.setText(R.id.topic_last_touched, topic.lastTouched);
        holder.setText(R.id.topic_title, topic.title);
        String reply = mContext.getString(R.string.comment_count, topic.replyCount);
        holder.setText(R.id.topic_reply_count, reply);

        ImageView avatar = holder.getView(R.id.topic_author_avatar);
        ImageLoader.loadAvatar(mContext, topic.authorAvatar, avatar);

        holder.setOnClickListener(R.id.topic_item, new OnTopicClickListener(mContext, topic.detailUrl));
    }
}
