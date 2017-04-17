package xyz.dcme.agg.ui.reply;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.zhy.adapter.recyclerview.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

import xyz.dcme.agg.R;
import xyz.dcme.agg.util.AccountUtils;

public class ReplyFragment extends Fragment implements ReplyContract.View {

    private static final String KEY_USER_NAME = "key_username";
    private RecyclerView mReplyList;
    private ProgressBar mProgressBar;

    private String mUserName;
    private ReplyContract.Presenter mPresenter;
    private List<Reply> mData;
    private Toolbar mToolbar;

    public static Fragment newInstance(String userName) {
        Fragment fragment = new ReplyFragment();
        Bundle args = new Bundle();
        args.putString(KEY_USER_NAME, userName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mUserName = args.getString(KEY_USER_NAME);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_reply, container, false);
        initViews(root);
        initPresenter();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.load(mUserName);
    }

    private void initPresenter() {
        new ReplyPresenter(this);
    }

    private void initViews(View root) {
        mReplyList = (RecyclerView) root.findViewById(R.id.reply_list);
        mProgressBar = (ProgressBar) root.findViewById(R.id.progress_bar);
        mToolbar = (Toolbar) root.findViewById(R.id.toolbar);

        initToolbar();
        initRecycle();
    }

    private void initRecycle() {
        mData = new ArrayList<>();

        mReplyList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mReplyList.setHasFixedSize(true);
        CommonAdapter<Reply> adapter = new ReplyAdapter(getActivity(),
                R.layout.item_reply, mData);
        mReplyList.setAdapter(adapter);
    }

    private void initToolbar() {
        FragmentActivity activity = getActivity();
        if (activity != null && activity instanceof AppCompatActivity) {
            ((AppCompatActivity) activity).setSupportActionBar(mToolbar);
            ActionBar ab = ((AppCompatActivity) activity).getSupportActionBar();
            if (ab != null) {
                ab.setDisplayHomeAsUpEnabled(true);
                int titleResId = AccountUtils.isCurrentAccount(activity, mUserName)
                        ? R.string.reply : R.string.his_reply;
                ab.setTitle(titleResId);
            }
        }
    }

    @Override
    public void setLoadingIndicator(final boolean active) {
        int time = getResources().getInteger(android.R.integer.config_shortAnimTime);
        mProgressBar.setVisibility(active ? View.VISIBLE : View.GONE);
        mProgressBar.animate().setDuration(time).alpha(active ? 1 : 0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mProgressBar.setVisibility(active ? View.VISIBLE : View.GONE);
                    }
                });
        mReplyList.setVisibility(!active ? View.VISIBLE : View.GONE);
        mReplyList.animate().setDuration(time).alpha(!active ? 1 : 0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mReplyList.setVisibility(!active ? View.VISIBLE : View.GONE);
                    }
                });
    }

    @Override
    public void showReplies(List<Reply> replies) {
        mData.clear();
        mData.addAll(replies);
        mReplyList.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void showNoReplies() {

    }

    @Override
    public void setPresenter(ReplyContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
