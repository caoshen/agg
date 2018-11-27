package cn.okclouder.library.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

public class BaseFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragments = new ArrayList<>();
    private List<String> mTitles = new ArrayList<>();

    public BaseFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public BaseFragmentAdapter(FragmentManager fm, List<Fragment> frags, List<String> titles) {
        super(fm);
        mFragments = frags;
        mTitles = titles;
        setFragments(fm, mFragments, mTitles);
    }

    public void setFragments(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
        if (mFragments != null) {
            FragmentTransaction transaction = fm.beginTransaction();
            for (Fragment f : mFragments) {
                transaction.remove(f);
            }
            transaction.commitAllowingStateLoss();
            fm.executePendingTransactions();
        }
        mFragments = fragments;
        mTitles = titles;
        notifyDataSetChanged();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
