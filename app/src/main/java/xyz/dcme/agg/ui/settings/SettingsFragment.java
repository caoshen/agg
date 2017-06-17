package xyz.dcme.agg.ui.settings;

import android.app.Activity;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.support.annotation.Nullable;

import xyz.dcme.agg.R;
import xyz.dcme.agg.util.AccountUtils;
import xyz.dcme.agg.util.HttpUtils;


public class SettingsFragment extends PreferenceFragment {

    public static final String LOG_TAG = "SettingsFragment";
    private static final String PREF_LOGOUT = "logout";
    private static final String PREF_CAT_ACCOUNT = "account";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        initPreference();
    }

    private void initPreference() {
        boolean hasActiveAccount = AccountUtils.hasActiveAccount(getActivity());
        if (!hasActiveAccount) {
            PreferenceCategory prefCatAccount = (PreferenceCategory) findPreference(PREF_CAT_ACCOUNT);
            getPreferenceScreen().removePreference(prefCatAccount);
        }
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
                    cleanCookie();
                    activity.setResult(Activity.RESULT_OK);
                    activity.finish();
                }
                break;
            }
            default:
                break;
        }
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }

    private void cleanCookie() {
        HttpUtils.cleanCookie();
    }
}
