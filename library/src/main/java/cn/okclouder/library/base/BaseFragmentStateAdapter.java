package cn.okclouder.library.base;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;


public class BaseFragmentStateAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mFragmentList = new ArrayList<>();
    private List<String> mTitles = new ArrayList<>();

    public BaseFragmentStateAdapter(FragmentManager fm) {
        super(fm);
    }

    public BaseFragmentStateAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        mFragmentList = fragmentList;
    }

    public BaseFragmentStateAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> titles) {
        super(fm);
        mFragmentList = fragmentList;
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    public void setFragments(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
        if (mFragmentList != null) {
            FragmentTransaction transaction = fm.beginTransaction();
            for (Fragment f : mFragmentList) {
                transaction.remove(f);
            }
            transaction.commitAllowingStateLoss();
            fm.executePendingTransactions();
        }
        mFragmentList = fragments;
        mTitles = titles;
        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}
