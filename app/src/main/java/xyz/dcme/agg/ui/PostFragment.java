package xyz.dcme.agg.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhy.adapter.recyclerview.wrapper.LoadMoreWrapper;

import java.util.ArrayList;
import java.util.List;

import xyz.dcme.agg.R;

public class PostFragment extends Fragment implements PostContract.View {

    private PostContract.Presenter mPresenter;
    private RecyclerView mPostRecycler;
    private PostAdapter mAdapter;
    private LoadMoreWrapper mLoadMoreWrapper;
    private ArrayList<String> mData = new ArrayList<>();
    private int mNextPage = 2;

    public static PostFragment newInstance() {
        return new PostFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    private void initViews(View view) {
        mPostRecycler = (RecyclerView) view.findViewById(R.id.post_list);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getActivity());
        mPostRecycler.setLayoutManager(lm);
        mAdapter = new PostAdapter(getActivity(), mData);
        mLoadMoreWrapper = new LoadMoreWrapper(mAdapter);
        mLoadMoreWrapper.setLoadMoreView(R.layout.load_more);
        mLoadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.loadMore(mNextPage);
            }
        });
        mPostRecycler.setAdapter(mAdapter);
    }

    @Override
    public void setPresenter(PostContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onRefresh(List<String> data) {
        mAdapter.clear();
        mAdapter.addData(data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadMore(List<String> data) {
        mAdapter.addData(data);
        mAdapter.notifyDataSetChanged();
        mNextPage++;
    }
}
