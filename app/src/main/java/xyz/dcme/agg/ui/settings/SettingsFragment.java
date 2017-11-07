package xyz.dcme.agg.ui.settings;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.support.annotation.Nullable;

import com.tencent.bugly.beta.Beta;

import xyz.dcme.agg.R;
import xyz.dcme.agg.ui.about.AboutActivity;
import xyz.dcme.agg.util.AccountUtils;
import xyz.dcme.agg.util.HttpUtils;


public class SettingsFragment extends PreferenceFragment {

    public static final String LOG_TAG = "SettingsFragment";
    private static final String PREF_LOGOUT = "logout";
    private static final String PREF_ABOUT = "about";
    private static final String PREF_CAT_ACCOUNT = "account";
    private static final String PREF_VERSION = "version";
    private static final String PREF_ADVICE = "advice";
    private static final String SEND_MAIL = "mailto:cshenn@163.com";


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
        Activity activity = getActivity();
        if (activity == null) {
            return false;
        }
        switch (key) {
            case PREF_LOGOUT: {
                if (!activity.isFinishing()) {
                    AccountUtils.clearAccount(activity);
                    cleanCookie();
                    activity.setResult(Activity.RESULT_OK);
                    activity.finish();
                }
                break;
            }
            case PREF_ADVICE: {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse(SEND_MAIL));
                intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.advice_title));
                startActivity(intent);
                break;
            }
            case PREF_VERSION: {
                Beta.checkUpgrade();
                break;
            }
            case PREF_ABOUT: {
                Intent intent = new Intent(activity, AboutActivity.class);
                startActivity(intent);
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
