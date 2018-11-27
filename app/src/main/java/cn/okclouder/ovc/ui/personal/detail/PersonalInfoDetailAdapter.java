package cn.okclouder.ovc.ui.personal.detail;

import android.content.Context;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import cn.okclouder.ovc.R;

public class PersonalInfoDetailAdapter extends CommonAdapter<Detail> {
    public PersonalInfoDetailAdapter(Context context, int layoutId, List<Detail> data) {
        super(context, layoutId, data);
    }

    @Override
    protected void convert(ViewHolder holder, Detail detail, int position) {
        holder.setText(R.id.item_title, detail.title);
        holder.setText(R.id.item_content, detail.content);
    }
}
