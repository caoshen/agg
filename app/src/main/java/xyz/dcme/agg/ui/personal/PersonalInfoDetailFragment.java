package xyz.dcme.agg.ui.personal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhy.adapter.recyclerview.CommonAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import xyz.dcme.agg.R;

public class PersonalInfoDetailFragment extends Fragment {

    private static final String EXTRA_DETAILS = "extra_details";
    private HashMap<String, String> mDetails;
    private RecyclerView mDetailList;

    public static PersonalInfoDetailFragment newInstance(HashMap<String, String> details) {
        PersonalInfoDetailFragment fragment = new PersonalInfoDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(PersonalInfoDetailFragment.EXTRA_DETAILS, details);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        mDetails = (HashMap<String, String>) args.getSerializable(EXTRA_DETAILS);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_personal_info_detail,
                container, false);
        initViews(root);
        return root;
    }

    private void initViews(View root) {
        List<Detail> data = new ArrayList<Detail>();
        if (mDetails == null || mDetails.isEmpty()) {
            return;
        }

        for (String title : mDetails.keySet()) {
            Detail d = new Detail();
            d.title = title;
            d.content = mDetails.get(title);
            data.add(d);
        }

        mDetailList = (RecyclerView) root.findViewById(R.id.detail_list);
        mDetailList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDetailList.setHasFixedSize(true);
        CommonAdapter<Detail> adapter = new PersonalInfoDetailAdapter(getActivity(),
                R.layout.item_personal_info_detail, data);
        mDetailList.setAdapter(adapter);
    }
}
