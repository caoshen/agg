package xyz.dcme.agg.ui.personal;

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
import java.util.HashMap;
import java.util.List;

import xyz.dcme.agg.R;

public class PersonalInfoDetailFragment extends Fragment
        implements PersonalInfoDetailContract.View {

    private RecyclerView mDetailList;
    private ArrayList<Detail> mData;
    private PersonalInfoDetailContract.Presenter mPresenter;
    private ProgressBar mProgressView;

    public static PersonalInfoDetailFragment newInstance(HashMap<String, String> details) {
        PersonalInfoDetailFragment fragment = new PersonalInfoDetailFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_personal_info_detail,
                container, false);
        initViews(root);
        initPresenter();
        return root;
    }

    private void initPresenter() {
        new PersonalInfoDetailPresenter(this);
    }

    private void initViews(View root) {
        mData = new ArrayList<Detail>();

        mDetailList = (RecyclerView) root.findViewById(R.id.detail_list);
        mDetailList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDetailList.setHasFixedSize(true);
        CommonAdapter<Detail> adapter = new PersonalInfoDetailAdapter(getActivity(),
                R.layout.item_personal_info_detail, mData);
        mDetailList.setAdapter(adapter);
    }


    @Override
    public void setPresenter(PersonalInfoDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setLoadingIndicator(final boolean active) {
        int time = getResources().getInteger(android.R.integer.config_shortAnimTime);
        mProgressView.setVisibility(active ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(time).alpha(active ? 1 : 0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mProgressView.setVisibility(active ? View.VISIBLE : View.GONE);
                    }
                });
    }

    @Override
    public void showDetails(List<Detail> details) {
        mData.addAll(details);
        mDetailList.getAdapter().notifyDataSetChanged();
    }
}
