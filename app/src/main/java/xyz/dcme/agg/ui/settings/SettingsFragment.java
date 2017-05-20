package xyz.dcme.agg.ui.settings;

import android.app.Activity;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.support.annotation.Nullable;

import xyz.dcme.agg.R;
import xyz.dcme.agg.util.AccountUtils;


public class SettingsFragment extends PreferenceFragment {

    public static final String LOG_TAG = "SettingsFragment";
    private static final String PREF_LOGOUT = "logout";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        String key = preference.getKey();
        switch (key) {
            case PREF_LOGOUT: {
                Activity activity = getActivity();
                if (activity != null && !activity.isFinishing()) {
                    AccountUtils.clearAccount(activity);
                    activity.finish();
                }
                break;
            }
            default:
                break;
        }
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }
}
