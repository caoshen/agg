package xyz.dcme.agg.ui.postdetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import xyz.dcme.agg.R;
import xyz.dcme.agg.ui.postdetail.data.PostDetailItem;

public class PostDetailFragment extends Fragment implements PostDetailContract.View,
        View.OnClickListener, TextWatcher {
    public static final String KEY_ARG_URL = "arg_url";
    private PostDetailContract.Presenter mPresenter;
    private PostDetailAdapter mAdapter;
    private List<PostDetailItem> mData = new ArrayList<>();
    private RecyclerView mRecycler;
    private String mUrl;
    private EditText mCommentText;
    private Button mSendBtn;

    public static PostDetailFragment newInstance(String url) {
        PostDetailFragment fragment = new PostDetailFragment();
        Bundle args = new Bundle();
        args.putString(KEY_ARG_URL, url);
        fragment.setArguments(args);
        return fragment;
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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
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
        mAdapter = new PostDetailAdapter(getActivity(), mData);
        mAdapter.addItemViewDelegate(new PostContentDelegate(getActivity()));
        mAdapter.addItemViewDelegate(new PostCommentDelegate(getActivity()));
        mRecycler.setAdapter(mAdapter);
        mCommentText = (EditText) view.findViewById(R.id.reply_content);
        mSendBtn = (Button) view.findViewById(R.id.send_reply);
        mSendBtn.setOnClickListener(this);
        mSendBtn.addTextChangedListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(mUrl)) {
            mPresenter.loadDetail(mUrl);
        }
    }

    @Override
    public void onRefresh(List<PostDetailItem> data) {
        mAdapter.getDatas().clear();
        mAdapter.getDatas().addAll(data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadMore(List<PostDetailItem> data) {

    }

    @Override
    public void addComment(String comment) {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.send_reply) {
            String comment = mCommentText.getText().toString();
            mPresenter.sendReply(comment);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String comment = mCommentText.getText().toString();
        mSendBtn.setEnabled(!TextUtils.isEmpty(comment));
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
