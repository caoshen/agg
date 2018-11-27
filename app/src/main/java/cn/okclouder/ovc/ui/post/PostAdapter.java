package cn.okclouder.ovc.ui.post;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.okclouder.ovc.R;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<String> mData;

    public PostAdapter(Context context, ArrayList<String> data) {
        mContext = context;
        mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mContent.setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void addData(List<String> data) {
        mData.addAll(data);
    }

    public void clear() {
        mData.clear();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mContent;

        public ViewHolder(View itemView) {
            super(itemView);
            mContent = (TextView) itemView.findViewById(R.id.post_content);
        }
    }
}
