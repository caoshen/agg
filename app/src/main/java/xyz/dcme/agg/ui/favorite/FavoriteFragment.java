package xyz.dcme.agg.ui.favorite;

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
import xyz.dcme.agg.model.Post;
import xyz.dcme.agg.util.AccountUtils;

public class FavoriteFragment extends Fragment implements FavoriteContract.View {

    private static final String KEY_USER_NAME = "key_username";
    private RecyclerView mFavList;
    private ProgressBar mProgressBar;

    private String mUserName;
    private FavoriteContract.Presenter mPresenter;
    private List<Post> mData;
    private Toolbar mToolbar;

    public static Fragment newInstance(String userName) {
        Fragment fragment = new FavoriteFragment();
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
        View root = inflater.inflate(R.layout.fragment_favorite, container, false);
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
        new FavoritePresenter(this);
    }

    private void initViews(View root) {
        mFavList = (RecyclerView) root.findViewById(R.id.fav_list);
        mProgressBar = (ProgressBar) root.findViewById(R.id.fav_progress);
        mToolbar = (Toolbar) root.findViewById(R.id.toolbar);

        initToolbar();
        initRecycle();
    }

    private void initRecycle() {
        mData = new ArrayList<>();

        mFavList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mFavList.setHasFixedSize(true);
        CommonAdapter<Post> adapter = new FavoriteAdapter(getActivity(),
                R.layout.item_fav, mData);
        mFavList.setAdapter(adapter);
    }

    private void initToolbar() {
        FragmentActivity activity = getActivity();
        if (activity != null && activity instanceof AppCompatActivity) {
            ((AppCompatActivity) activity).setSupportActionBar(mToolbar);
            ActionBar ab = ((AppCompatActivity) activity).getSupportActionBar();
            if (ab != null) {
                ab.setDisplayHomeAsUpEnabled(true);
                int titleResId = AccountUtils.isCurrentAccount(activity, mUserName)
                        ? R.string.favourite : R.string.his_fav;
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
        mFavList.setVisibility(!active ? View.VISIBLE : View.GONE);
        mFavList.animate().setDuration(time).alpha(!active ? 1 : 0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mFavList.setVisibility(!active ? View.VISIBLE : View.GONE);
                    }
                });
    }

    @Override
    public void showFav(List<Post> posts) {
        mData.clear();
        mData.addAll(posts);
        mFavList.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void showNoData() {

    }

    @Override
    public void setPresenter(FavoriteContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
