package xyz.dcme.agg.ui.me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import xyz.dcme.agg.R;
import xyz.dcme.agg.util.AnimationUtils;

public class MeFragment extends Fragment implements AppBarLayout.OnOffsetChangedListener {

    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS     = 0.3f;
    private static final long ALPHA_ANIMATIONS_DURATION = 200;

    private Toolbar mToolbar;
    private AppBarLayout mAppBar;
    private RelativeLayout mTitleContainer;
    private TextView mTitle;

    private boolean isTitleVisible = false;
    private boolean isTitleContainerVisible = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_me, container, false);
        initViews(root);
        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_me, menu);
    }

    private void initViews(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mAppBar = (AppBarLayout) view.findViewById(R.id.appbar);
        mTitleContainer = (RelativeLayout) view.findViewById(R.id.main_title);
        mTitle = (TextView) view.findViewById(R.id.toolbar_title);

        mToolbar.inflateMenu(R.menu.menu_me);
        mAppBar.addOnOffsetChangedListener(this);
        AnimationUtils.startAlphaAnimation(mToolbar, 0, View.INVISIBLE);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float offsetPercent = (float) Math.abs(verticalOffset) / (float) maxScroll;

        handleAlphaOnTitle(offsetPercent);
        handleTitleVisibility(offsetPercent);
    }

    private void handleTitleVisibility(float offsetPercent) {
        if (offsetPercent >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {
            showTitle();
        } else {
            hideTitle();
        }
    }

    private void showTitle() {
        if (!isTitleVisible) {
            AnimationUtils.startAlphaAnimation(mToolbar, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
            isTitleVisible = true;
        }
    }

    private void hideTitle() {
        if (isTitleVisible) {
            AnimationUtils.startAlphaAnimation(mToolbar, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
            isTitleVisible = false;
        }
    }

    private void handleAlphaOnTitle(float offsetPercent) {
        if (offsetPercent >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            hideTitleDetails();
        } else {
            showTitleDetails();
        }
    }

    private void hideTitleDetails() {
        if (isTitleContainerVisible) {
            AnimationUtils.startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
            isTitleContainerVisible = false;
        }
    }

    private void showTitleDetails() {
        if (!isTitleContainerVisible) {
            AnimationUtils.startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
            isTitleContainerVisible = true;
        }
    }
}
