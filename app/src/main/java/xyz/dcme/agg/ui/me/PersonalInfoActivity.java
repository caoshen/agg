package xyz.dcme.agg.ui.me;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import xyz.dcme.agg.R;
import xyz.dcme.agg.ui.BaseActivity;

public class PersonalInfoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
    }

    public static void showPersonalInfo(FragmentActivity activity) {

    }
}
