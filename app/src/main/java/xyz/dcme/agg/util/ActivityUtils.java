package xyz.dcme.agg.util;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class ActivityUtils {
    public static void addFragmentToActivity(@NonNull FragmentManager fm,
                                             @NonNull Fragment fragment, int frameId) {
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }
}
