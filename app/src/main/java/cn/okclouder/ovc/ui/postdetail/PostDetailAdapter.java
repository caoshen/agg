package cn.okclouder.ovc.ui.postdetail;

import android.content.Context;

import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.List;

import cn.okclouder.ovc.ui.postdetail.data.PostDetailItem;

public class PostDetailAdapter extends MultiItemTypeAdapter<PostDetailItem> {

    public PostDetailAdapter(Context context, List<PostDetailItem> data) {
        super(context, data);
    }

}
