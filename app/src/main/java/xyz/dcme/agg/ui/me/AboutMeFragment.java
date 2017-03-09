package xyz.dcme.agg.ui.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import xyz.dcme.agg.R;
import xyz.dcme.agg.ui.login.LoginActivity;


public class AboutMeFragment extends Fragment implements View.OnClickListener {

    private static final int REQUEST_ABOUT_ME = 10;
    private LinearLayout mAboutMe;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView  = inflater.inflate(R.layout.fragment_about_me, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    private void initViews(View view) {
        mAboutMe = (LinearLayout) view.findViewById(R.id.about_me);
        mAboutMe.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.about_me : {
                startActivityForResult(new Intent(getActivity(), LoginActivity.class), REQUEST_ABOUT_ME);
                break;
            }
        }
    }
}
