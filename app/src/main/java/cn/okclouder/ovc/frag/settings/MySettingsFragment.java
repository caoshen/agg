package cn.okclouder.ovc.frag.settings;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;

import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;
import com.tencent.bugly.beta.Beta;

import cn.okclouder.account.AccountManager;
import cn.okclouder.account.LoginConstants;
import cn.okclouder.ovc.R;
import cn.okclouder.ovc.base.BaseFragment;
import cn.okclouder.ovc.frag.about.AboutFragment;
import cn.okclouder.ovc.util.HttpUtils;
import cn.okclouder.ovc.util.VersionUtil;


public class MySettingsFragment extends BaseFragment {

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

        QMUIGroupListView.newSection(getActivity())
                .addItemView(itemUpgrade, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Beta.checkUpgrade();
                    }
                })
                .addItemView(itemAbout, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startFragment(new AboutFragment());
                    }
                })
                .addTo(groupListView);

        if (AccountManager.hasLoginAccount(getActivity())) {
            addLogoutItem(groupListView);
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
