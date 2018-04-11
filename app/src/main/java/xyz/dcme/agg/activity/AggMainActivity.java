package xyz.dcme.agg.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import xyz.dcme.agg.R;
import xyz.dcme.agg.base.BaseFragmentActivity;
import xyz.dcme.agg.frag.home.HomeFragment;


public class AggMainActivity extends BaseFragmentActivity {

    @Override
    public int getContextViewId() {
        return R.id.agg_main_activity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            HomeFragment homeFragment = new HomeFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(getContextViewId(), homeFragment, homeFragment.getClass().getSimpleName())
                    .addToBackStack(homeFragment.getClass().getSimpleName())
                    .commit();
        }
    }
}
