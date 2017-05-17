package xyz.dcme.agg.ui.node;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.zhy.adapter.recyclerview.wrapper.LoadMoreWrapper;

import java.util.ArrayList;
import java.util.List;

import xyz.dcme.agg.R;
import xyz.dcme.agg.model.Post;
import xyz.dcme.agg.ui.BaseFragment;
import xyz.dcme.agg.ui.post.PostCommonAdapter;
import xyz.dcme.agg.util.Constants;

public class NodeListFragment extends BaseFragment implements NodeListContract.View {

    private String mNodeName;
    private RecyclerView mNodeList;
    private PostCommonAdapter mAdapter;
    private LoadMoreWrapper mLoadMoreWrapper;
    private ArrayList<Post> mData = new ArrayList<>();
    private int mNextPage = 2;
    private NodeListContract.Presenter mPresenter;

    public static Fragment newInstance(Node node) {
        NodeListFragment fragment = new NodeListFragment();
        Bundle args = new Bundle();
        args.putString(Constants.NODE_NAME, node.getName());
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (null != args) {
            mNodeName = args.getString(Constants.NODE_NAME);
        }
    }

    @Override
    protected void initView() {
        mNodeList = (RecyclerView) mRootView.findViewById(R.id.node_list);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getActivity());
        mNodeList.setLayoutManager(lm);
        mAdapter = new PostCommonAdapter(getActivity(), R.layout.item_post, mData);
        mLoadMoreWrapper = new LoadMoreWrapper(mAdapter);
        mLoadMoreWrapper.setLoadMoreView(R.layout.load_more);
        mLoadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.load(mNodeName, mNextPage);
            }
        });
        mNodeList.setAdapter(mLoadMoreWrapper);
        mNodeList.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL));

        mPresenter.start(mNodeName);
    }

    @Override
    protected void initPresenter() {
        mPresenter = new NodeListPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_node_list;
    }

    @Override
    public void setPresenter(NodeListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onRefresh(List<Post> data) {
        mAdapter.getDatas().clear();
        mAdapter.getDatas().addAll(data);
        mLoadMoreWrapper.notifyDataSetChanged();
    }

    @Override
    public void onLoad(List<Post> data) {
        mAdapter.getDatas().addAll(data);
        mLoadMoreWrapper.notifyDataSetChanged();
        mNextPage++;
    }
}
