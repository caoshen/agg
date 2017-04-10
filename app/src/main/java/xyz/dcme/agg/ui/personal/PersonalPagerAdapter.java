package xyz.dcme.agg.ui.personal;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import xyz.dcme.agg.R;
import xyz.dcme.agg.ui.personal.detail.PersonalInfoDetailFragment;
import xyz.dcme.agg.ui.personal.page.PersonalInfoPageFragment;

public class PersonalPagerAdapter extends FragmentStatePagerAdapter {

    public static final int PAGER_SIZE = 2;
    private static final int[] mTitles = {R.string.info_page, R.string.info_detail};
    private Context mContext;
    private String mUserName;

    public PersonalPagerAdapter(Context context, FragmentManager fm, String userName) {
        super(fm);
        mContext = context;
        mUserName = userName;
    }

    public PersonalPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new PersonalInfoPageFragment();
        } else {
            return PersonalInfoDetailFragment.newInstance(mUserName);
        }
    }

    @Override
    public int getCount() {
        return PAGER_SIZE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getString(mTitles[position]);
    }
}
