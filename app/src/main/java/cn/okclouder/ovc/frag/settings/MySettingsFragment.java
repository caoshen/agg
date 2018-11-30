package cn.okclouder.ovc.frag.settings;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;

import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;
import com.tencent.bugly.beta.Beta;

import cn.okclouder.account.AccountManager;
import cn.okclouder.account.LoginConstants;
import cn.okclouder.library.util.LogUtils;
import cn.okclouder.ovc.R;
import cn.okclouder.ovc.base.BaseFragment;
import cn.okclouder.ovc.frag.about.AboutFragment;
import cn.okclouder.ovc.util.HttpUtils;
import cn.okclouder.ovc.util.VersionUtil;


public class MySettingsFragment extends BaseFragment {

    private static final String TAG = MySettingsFragment.class.getSimpleName();

    @Override
    protected View onCreateView() {
        View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_my_settings, null);
        initViews(rootView);
        return rootView;
    }

    private void initViews(View rootView) {
        initTopBar(rootView);
        initGroupView(rootView);
    }

    private void initGroupView(View rootView) {
        QMUIGroupListView groupListView = rootView.findViewById(R.id.groupListView);
        QMUICommonListItemView itemUpgrade = groupListView.createItemView(getString(R.string.version_upgrade));
        itemUpgrade.setDetailText(VersionUtil.getAppVersionName(getActivity()));

        QMUICommonListItemView itemAbout = groupListView.createItemView(getString(R.string.about));
        itemAbout.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        QMUICommonListItemView itemFeedback = groupListView.createItemView(getString(R.string.feedback));
        itemFeedback.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        QMUICommonListItemView itemAppMarket = groupListView.createItemView(getString(R.string.go_to_app_market));
        itemAppMarket.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        QMUIGroupListView.newSection(getActivity())
                .addItemView(itemUpgrade, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Beta.checkUpgrade();
                    }
                })
                .addItemView(itemFeedback, v -> sendFeedback())
                .addItemView(itemAppMarket, v -> gotoMarket())
                .addItemView(itemAbout, v -> startFragment(new AboutFragment()))
                .addTo(groupListView);

        if (AccountManager.hasLoginAccount(getActivity())) {
            addLogoutItem(groupListView);
        }
    }

    private void gotoMarket() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        FragmentActivity activity = getActivity();
        if (activity == null) {
            return;
        }
        String packageName = activity.getPackageName();
        intent.setData(Uri.parse("market://details?id=" + packageName));

        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            LogUtils.e(TAG, "no market, activity not found ex:" + e);
        }
    }

    private void sendFeedback() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        String mail = "pubtst@126.com";
        intent.setData(Uri.parse("mailto:" + mail));
        String appVersionName = VersionUtil.getAppVersionName(getActivity());
        String feedback = getString(R.string.feedback);
        intent.putExtra(Intent.EXTRA_SUBJECT, "#" + feedback + "#" + appVersionName);

        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            LogUtils.e(TAG, "activity not found ex:" + e);
        }
    }

    private void addLogoutItem(QMUIGroupListView groupListView) {
        QMUICommonListItemView itemLogout = groupListView.createItemView(getString(R.string.logout_current));
        itemLogout.getTextView().setTextColor(getResources().getColor(R.color.warning_text_color));
        QMUIGroupListView.newSection(getActivity())
                .addItemView(itemLogout, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AccountManager.clearAccount(getActivity());
                        HttpUtils.cleanCookie();
                        popBackStack();
                        Intent intent = new Intent(LoginConstants.ACTION_LOGOUT_ACCOUNT);
                        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                    }
                })
                .addTo(groupListView);
    }

    private void initTopBar(View rootView) {
        QMUITopBar topbar = rootView.findViewById(R.id.topbar);
        topbar.setTitle(R.string.settings);
        topbar.addLeftImageButton(cn.okclouder.account.R.drawable.ic_topbar_back_blue, com.qmuiteam.qmui.R.id.qmui_topbar_item_left_back)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popBackStack();
                    }
                });
    }

    @Override
    protected boolean canDragBack() {
        return true;
    }
}
