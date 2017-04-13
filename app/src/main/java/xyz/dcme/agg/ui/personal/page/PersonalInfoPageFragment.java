package xyz.dcme.agg.ui.personal.page;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import xyz.dcme.agg.R;
import xyz.dcme.agg.util.AccountUtils;

public class PersonalInfoPageFragment extends Fragment
    implements PersonalInfoPageContract.View {

    private static final String EXTRA_NAME = "extra_name";
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

        String accountName = AccountUtils.getActiveAccountName(getActivity());
        if (!TextUtils.isEmpty(mUserName) && mUserName.equals(accountName)) {
            TextView myTopic = (TextView) root.findViewById(R.id.my_topic);
            TextView myReply = (TextView) root.findViewById(R.id.my_reply);
            TextView myFav = (TextView) root.findViewById(R.id.my_focus);
            myTopic.setText(R.string.his_topic);
            myReply.setText(R.string.his_reply);
            myFav.setText(R.string.his_fav);
        }
    }

    private void initPresenter() {
        new PersonalInfoPagePresenter(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.load(mUserName);
    }

    @Override
    public void setPresenter(PersonalInfoPageContract.Presenter presenter) {
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
        mContent.setVisibility(!active ? View.VISIBLE : View.GONE);
        mContent.animate().setDuration(time).alpha(!active ? 1 : 0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mContent.setVisibility(!active ? View.VISIBLE : View.GONE);
                    }
                });
    }

    @Override
    public void show(Count count) {
        mTopicView.setText(count.topicCount);
        mReplyView.setText(count.replyCount);
        mFavView.setText(count.favCount);
        mReputationView.setText(count.reputationCount);
    }
}
