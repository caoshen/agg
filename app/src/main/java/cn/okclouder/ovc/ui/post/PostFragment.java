package cn.okclouder.ovc.ui.post;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zhy.adapter.recyclerview.wrapper.LoadMoreWrapper;

import java.util.ArrayList;
import java.util.List;

import cn.okclouder.ovc.R;
import cn.okclouder.ovc.model.Post;

public class PostFragment extends Fragment implements PostContract.View {

    public static final String TAG = "PostFragment";

    private PostContract.Presenter mPresenter;
    private RecyclerView mPostRecycler;
    private PostCommonAdapter mPostCommonAdapter;
    private LoadMoreWrapper mLoadMoreWrapper;
    private ArrayList<Post> mData = new ArrayList<>();
    private int mNextPage = 2;

    public static PostFragment newInstance() {
        return new PostFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        initPresenter();
        return root;
    }

    private void initPresenter() {
        new PostPresenter(this);
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
        mPostCommonAdapter = new PostCommonAdapter(getActivity(), R.layout.item_post, mData);
        mLoadMoreWrapper = new LoadMoreWrapper(mPostCommonAdapter);
        mLoadMoreWrapper.setLoadMoreView(R.layout.load_more);
        mLoadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.loadMore(mNextPage);
            }
        });
        mPostRecycler.setAdapter(mLoadMoreWrapper);
        mPostRecycler.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL));
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
    public void onRefresh(List<Post> data) {
        mPostCommonAdapter.getDatas().clear();
        mPostCommonAdapter.getDatas().addAll(data);
        mLoadMoreWrapper.notifyDataSetChanged();
    }

    @Override
    public void onLoadMore(List<Post> data) {
        mPostCommonAdapter.getDatas().addAll(data);
        mLoadMoreWrapper.notifyDataSetChanged();
        mNextPage++;
    }

    @Override
    public void onError() {

    }
}
