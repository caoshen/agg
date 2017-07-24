package xyz.dcme.agg.ui.personal.detail;

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
import xyz.dcme.agg.util.AnimationUtils;

public class PersonalInfoDetailFragment extends Fragment
        implements PersonalInfoDetailContract.View {

    private static final String EXTRA_NAME = "extra_username";
    private RecyclerView mDetailList;
    private ArrayList<Detail> mData;
    private PersonalInfoDetailContract.Presenter mPresenter;
    private ProgressBar mProgressView;
    private String mUserName;

    public static PersonalInfoDetailFragment newInstance(String userName) {
        PersonalInfoDetailFragment fragment = new PersonalInfoDetailFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_NAME, userName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mUserName = args.getString(EXTRA_NAME);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (isAdded()) {
            mPresenter.load(mUserName);
        }
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

        mProgressView = (ProgressBar) root.findViewById(R.id.progress);
    }


    @Override
    public void setPresenter(PersonalInfoDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setLoadingIndicator(final boolean active) {
        AnimationUtils.showProgress(mProgressView, mDetailList, active);
    }

    @Override
    public void showDetails(List<Detail> details) {
        mData.clear();
        mData.addAll(details);
        mDetailList.getAdapter().notifyDataSetChanged();
    }
}
