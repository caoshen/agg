package xyz.dcme.agg.ui.topic;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.zhy.adapter.recyclerview.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

import xyz.dcme.agg.R;

public class TopicFragment extends Fragment implements TopicContract.View {

    private static final String KEY_USER_NAME = "key_username";
    private RecyclerView mTopicView;
    private ProgressBar mProgressBar;

    private String mUserName;
    private TopicContract.Presenter mPresenter;
    private List<Topic> mData;

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
        View root = inflater.inflate(R.layout.fragment_topic, container, false);
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
        new TopicPresenter(this);
    }

    private void initViews(View root) {
        mTopicView = (RecyclerView) root.findViewById(R.id.topic_list);
        mProgressBar = (ProgressBar) root.findViewById(R.id.progress_bar);
        mData = new ArrayList<Topic>();

        mTopicView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mTopicView.setHasFixedSize(true);
        CommonAdapter<Topic> adapter = new TopicAdapter(getActivity(),
                R.layout.item_topic, mData);
        mTopicView.setAdapter(adapter);
    }

    public static Fragment newInstance(String userName) {
        Fragment fragment = new TopicFragment();
        Bundle args = new Bundle();
        args.putString(KEY_USER_NAME, userName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setPresenter(TopicContract.Presenter presenter) {
        mPresenter = presenter;
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
        mTopicView.setVisibility(!active ? View.VISIBLE : View.GONE);
        mTopicView.animate().setDuration(time).alpha(!active ? 1 : 0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mTopicView.setVisibility(!active ? View.VISIBLE : View.GONE);
                    }
                });
    }

    @Override
    public void showTopics(List<Topic> topics) {
        mData.clear();
        mData.addAll(topics);
        mTopicView.getAdapter().notifyDataSetChanged();
    }
}
