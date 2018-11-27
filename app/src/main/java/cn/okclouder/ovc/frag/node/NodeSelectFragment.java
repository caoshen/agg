package cn.okclouder.ovc.frag.node;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.qmuiteam.qmui.widget.QMUITopBar;

import java.util.List;

import cn.okclouder.ovc.R;
import cn.okclouder.ovc.base.BaseFragment;
import cn.okclouder.ovc.database.NodeDbHelper;
import cn.okclouder.ovc.ui.node.Node;
import cn.okclouder.ovc.ui.node.NodeTagAdapter;
import cn.okclouder.library.widget.IItemSelectedListener;
import cn.okclouder.library.widget.TagFlowLayout;


public class NodeSelectFragment extends BaseFragment {
    private static final String ARGS_KEY_SELECTED_NODE = "args_key_selected_node";
    private Node mSelection;

    public static NodeSelectFragment newInstance(Node node) {
        NodeSelectFragment fragment = new NodeSelectFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARGS_KEY_SELECTED_NODE, node);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mSelection = arguments.getParcelable(ARGS_KEY_SELECTED_NODE);
        }
    }

    @Override
    protected boolean canDragBack() {
        return true;
    }

    @Override
    protected View onCreateView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_node_select, null);
        initView(view);
        return view;
    }

    public void initView(View view) {
        List<Node> data = NodeDbHelper.getInstance().queryAllNodes(getActivity());
        TagFlowLayout tagFlowLayout = (TagFlowLayout) view.findViewById(R.id.flow);

        NodeTagAdapter tagAdapter = new NodeTagAdapter(getActivity(), data);
        tagAdapter.setSelectedListener(new IItemSelectedListener<Node>() {
            @Override
            public void onItemSelect(List<Node> data) {
                if (!data.isEmpty()) {
                    Intent args = new Intent();
                    mSelection = data.get(0);
                    args.putExtra(NodeSelectActivity.KEY_SELECTED_NODE, mSelection);
                    FragmentActivity activity = getActivity();
                    if (activity != null) {
                        activity.setResult(Activity.RESULT_OK, args);
                    }
                }
            }
        });
        tagFlowLayout.setAdapter(tagAdapter);

        if (null != mSelection) {
            tagAdapter.enterSingleMode(mSelection);
        }

        QMUITopBar topbar = (QMUITopBar) view.findViewById(R.id.topbar);
        topbar.setTitle(R.string.tag_select);
        topbar.addLeftImageButton(R.drawable.ic_topbar_back_blue, R.id.qmui_topbar_item_left_back)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popBackStack();
                    }
                });
    }
}
