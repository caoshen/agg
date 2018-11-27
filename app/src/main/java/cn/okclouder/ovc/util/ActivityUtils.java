package cn.okclouder.ovc.util;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class ActivityUtils {

    public static void addFragmentToActivity(FragmentActivity fragmentActivity, Fragment fragment, int containerId) {
        FragmentManager fm = fragmentActivity.getSupportFragmentManager();
        Fragment curFragment = fm.findFragmentById(containerId);
        if (curFragment == null) {
            curFragment = fragment;
            ActivityUtils.addFragmentToActivity(fm, curFragment, containerId);
        }
    }

    public static void addFragmentToActivity(@NonNull FragmentManager fm, @NonNull Fragment fragment, int frameId) {
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }
}
