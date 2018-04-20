package xyz.dcme.agg.frag.user;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;

import xyz.dcme.agg.R;
import xyz.dcme.agg.base.BaseFragment;


public class UserHomePageFragment extends BaseFragment {
    public static final String TAG = UserHomePageFragment.class.getSimpleName();

    @Override
    protected View onCreateView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_user_home_page, null);
        return view;
    }

    public static Fragment newInstance() {
        Fragment fragment = new UserHomePageFragment();
        return fragment;
    }
}
