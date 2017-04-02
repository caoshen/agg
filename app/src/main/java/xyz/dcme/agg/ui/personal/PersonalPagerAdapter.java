package xyz.dcme.agg.ui.personal;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import xyz.dcme.agg.R;

public class PersonalPagerAdapter extends FragmentStatePagerAdapter {

    public static final int PAGER_SIZE = 2;
    private static final int[] mTitles = {R.string.info_page, R.string.info_detail};
    private Context mContext;

    public PersonalPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    public PersonalPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new PersonalInfoPageFragment();
        } else {
            return new PersonalInfoDetailFragment();
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
