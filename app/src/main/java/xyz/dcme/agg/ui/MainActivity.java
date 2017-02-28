package xyz.dcme.agg.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import xyz.dcme.agg.R;
import xyz.dcme.agg.ui.post.PostFragment;
import xyz.dcme.agg.ui.post.PostPresenter;
import xyz.dcme.agg.util.ActivityUtils;

public class MainActivity extends AppCompatActivity {

    private PostPresenter mPresenter;
    private BottomNavigationView mBottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initFragment();
    }

    private void initViews() {
        mBottomNav = (BottomNavigationView) findViewById(R.id.navigation);
        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home_page: {
                        break;
                    }
                    case R.id.action_search: {
                        break;
                    }
                    case R.id.action_me: {
                        break;
                    }
                    default: {
                        return true;
                    }
                }
                return true;
            }
        });
    }

    private void initFragment() {
        FragmentManager fm = getSupportFragmentManager();
        PostFragment postFragment = (PostFragment) fm.findFragmentById(R.id.main_content);
        if (postFragment == null) {
            postFragment = PostFragment.newInstance();
            ActivityUtils.addFragmentToActivity(fm, postFragment, R.id.main_content);
        }
        mPresenter = new PostPresenter(postFragment);
    }
}
