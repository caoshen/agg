package xyz.dcme.agg.ui.postdetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import xyz.dcme.agg.R;
import xyz.dcme.agg.ui.postdetail.delegate.PostCommentItemDelegate;
import xyz.dcme.agg.ui.postdetail.delegate.PostDetailItemDelegate;

public class PostDetailFragment extends Fragment implements PostDetailContract.View {
    public static final String KEY_ARG_URL = "arg_url";
    private PostDetailContract.Presenter mPresenter;
    private MultiItemTypeAdapter mAdapter;
    private List<PostDetailType> mData = new ArrayList<>();
    private RecyclerView mRecycler;
    private String mUrl;

    @Override
    public void onRefresh(List<PostDetailType> data) {

    }

    @Override
    public void setPresenter(PostDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mUrl = args.getString(KEY_ARG_URL);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_detail, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    private void initViews(View view) {
        mRecycler = (RecyclerView) view.findViewById(R.id.post_detail);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getActivity());
        mRecycler.setLayoutManager(lm);
        mAdapter = new MultiItemTypeAdapter<>(getActivity(), mData);
        mAdapter.addItemViewDelegate(new PostDetailItemDelegate());
        mAdapter.addItemViewDelegate(new PostCommentItemDelegate());
        mRecycler.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(mUrl)) {
            mPresenter.loadDetail(mUrl);
        }
    }

    public static PostDetailFragment newInstance(String url) {
        PostDetailFragment fragment = new PostDetailFragment();
        Bundle args = new Bundle();
        args.putString(KEY_ARG_URL, url);
        fragment.setArguments(args);
        return fragment;
    }
}
