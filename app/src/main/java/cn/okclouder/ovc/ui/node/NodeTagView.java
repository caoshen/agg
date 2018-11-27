package cn.okclouder.ovc.ui.node;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import cn.okclouder.library.widget.BaseTagView;


public class NodeTagView extends BaseTagView<Node> {
    public NodeTagView(@NonNull Context context) {
        super(context);
    }

    public NodeTagView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NodeTagView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
