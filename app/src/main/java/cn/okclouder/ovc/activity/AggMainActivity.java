package cn.okclouder.ovc.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import cn.okclouder.ovc.R;
import cn.okclouder.ovc.base.BaseFragmentActivity;
import cn.okclouder.ovc.frag.home.HomeFragment;


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
