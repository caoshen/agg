package cn.okclouder.ovc.ui.personal.page;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import cn.okclouder.ovc.R;
import cn.okclouder.ovc.frag.user.UserCommonRecyclerFragment;
import cn.okclouder.ovc.util.AccountUtils;
import cn.okclouder.ovc.util.AnimationUtils;

public class PersonalInfoPageFragment extends Fragment
        implements PersonalInfoPageContract.View, View.OnClickListener {

    private static final String EXTRA_NAME = "extra_name";
    private static final int ACTION_TOPIC = 1;
    private static final int ACTION_REPLY = 2;
    private static final int ACTION_FAV = 3;
    private PersonalInfoPageContract.Presenter mPresenter;
    private String mUserName;
    private ProgressBar mProgressBar;
    private LinearLayout mContent;
    private TextView mTopicView;
    private TextView mReplyView;
    private TextView mFavView;
    private TextView mReputationView;

    public static Fragment newInstance(String userName) {
        PersonalInfoPageFragment fragment = new PersonalInfoPageFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_NAME, userName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (null != args) {
            mUserName = args.getString(EXTRA_NAME);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_personal_info_page,
                container, false);
        initViews(root);
        initPresenter();
        return root;
    }

    private void initViews(View root) {
        mProgressBar = (ProgressBar) root.findViewById(R.id.progress);
        mContent = (LinearLayout) root.findViewById(R.id.content);
        mTopicView = (TextView) root.findViewById(R.id.topic_count);
        mReplyView = (TextView) root.findViewById(R.id.reply_count);
        mFavView = (TextView) root.findViewById(R.id.favourite_count);
        mReputationView = (TextView) root.findViewById(R.id.reputation_count);
        TextView myTopic = (TextView) root.findViewById(R.id.my_topic);
        TextView myReply = (TextView) root.findViewById(R.id.my_reply);
        TextView myFav = (TextView) root.findViewById(R.id.my_focus);
        LinearLayout topics = (LinearLayout) root.findViewById(R.id.topics);
        LinearLayout replies = (LinearLayout) root.findViewById(R.id.replies);
        LinearLayout fav = (LinearLayout) root.findViewById(R.id.favourites);

        if (!AccountUtils.isCurrentAccount(getActivity(), mUserName)) {
            myTopic.setText(R.string.his_topic);
            myReply.setText(R.string.his_reply);
            myFav.setText(R.string.his_fav);
        }

        topics.setOnClickListener(this);
        replies.setOnClickListener(this);
        fav.setOnClickListener(this);
        myTopic.setOnClickListener(this);
        myReply.setOnClickListener(this);
        myFav.setOnClickListener(this);
    }

    private void initPresenter() {
        new PersonalInfoPagePresenter(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.load(mUserName);
    }

    @Override
    public void setPresenter(PersonalInfoPageContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setLoadingIndicator(final boolean active) {
        AnimationUtils.showProgress(mProgressBar, mContent, active);
    }

    @Override
    public void show(Count count) {
        mTopicView.setText(count.topicCount);
        mReplyView.setText(count.replyCount);
        mFavView.setText(count.favCount);
        mReputationView.setText(count.reputationCount);
    }

    @Override
    public void onClick(View v) {
        int action = ACTION_TOPIC;

        switch (v.getId()) {
            case R.id.topics:
            case R.id.my_topic: {
                action = ACTION_TOPIC;
                // TopicActivity.start(getActivity(), mUserName);
                break;
            }
            case R.id.replies:
            case R.id.my_reply: {
                action = ACTION_REPLY;
                // ReplyActivity.start(getActivity(), mUserName);
                break;
            }
            case R.id.favourites:
            case R.id.my_focus: {
                action = ACTION_FAV;
                // FavoriteActivity.start(getActivity(), mUserName);
                break;
            }
        }
        addFragment(action);
    }

    private void addFragment(int action) {
        UserCommonRecyclerFragment fragment = UserCommonRecyclerFragment.newInstance(mUserName, action);
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.main_content, fragment, UserCommonRecyclerFragment.TAG)
                    .addToBackStack(UserCommonRecyclerFragment.TAG)
                    .commit();
        }
    }
}
