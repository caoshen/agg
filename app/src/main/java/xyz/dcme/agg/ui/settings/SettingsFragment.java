package xyz.dcme.agg.ui.settings;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

import xyz.dcme.agg.R;


public class SettingsFragment extends PreferenceFragment {

    public static final String LOG_TAG = "SettingsFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }
}
