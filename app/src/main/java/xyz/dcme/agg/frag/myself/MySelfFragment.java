package xyz.dcme.agg.frag.myself;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import xyz.dcme.account.AccountInfo;
import xyz.dcme.account.AccountManager;
import xyz.dcme.account.ErrorStatus;
import xyz.dcme.account.LoginHandler;
import xyz.dcme.agg.R;
import xyz.dcme.agg.base.BaseFragment;
import xyz.dcme.library.util.ImageLoader;


public class MySelfFragment extends BaseFragment implements View.OnClickListener {
    private static final int REQ_CODE_LOGIN = 1;
    private ImageView mUserImageView;
    private TextView mUserNameText;

    @Override
    protected View onCreateView() {
        View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_myself, null);
        initViews(rootView);
        return rootView;
    }

    private void initViews(View rootView) {
        QMUITopBar topbar = rootView.findViewById(R.id.topbar);
        topbar.setTitle(R.string.me);

        View itemAccount = rootView.findViewById(R.id.item_my_account);
        itemAccount.setOnClickListener(this);
        mUserImageView = itemAccount.findViewById(R.id.user_avatar);
        mUserNameText = itemAccount.findViewById(R.id.user_name);

        QMUIGroupListView groupList = rootView.findViewById(R.id.groupListView);
        QMUICommonListItemView itemMessage = groupList.createItemView(getActivity().getDrawable(R.drawable.ic_item_notifications),
                getString(R.string.message), null,
                QMUICommonListItemView.HORIZONTAL, QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        QMUICommonListItemView itemWithChevron = groupList.createItemView(getActivity().getDrawable(R.drawable.ic_item_star),
                getString(R.string.favourite), null,
                QMUICommonListItemView.HORIZONTAL, QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        QMUICommonListItemView itemHistory = groupList.createItemView(getActivity().getDrawable(R.drawable.ic_item_history),
                getString(R.string.history), null,
                QMUICommonListItemView.HORIZONTAL, QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        QMUIGroupListView.newSection(getActivity())
                .addItemView(itemMessage, null)
                .addItemView(itemWithChevron, null)
                .addItemView(itemHistory, null).addTo(groupList);

        QMUICommonListItemView itemSettings = groupList.createItemView(getActivity().getDrawable(R.drawable.ic_item_settings),
                getString(R.string.settings), null,
                QMUICommonListItemView.HORIZONTAL, QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        QMUIGroupListView.newSection(getActivity())
                .addItemView(itemSettings, null).addTo(groupList);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.item_my_account) {
//            Intent intent = new Intent(getActivity(), LoginActivity.class);
//            startActivityForResult(intent, REQ_CODE_LOGIN);
            AccountManager.getAccount(getActivity(), new LoginHandler() {
                @Override
                public void onLogin(AccountInfo account) {
                    updateAccountView(account);
                }

                @Override
                public void onError(ErrorStatus status) {

                }
            });
        }
    }

    private void updateAccountView(AccountInfo account) {
        ImageLoader.display(getActivity(), mUserImageView, account.getAvatarUrl());
        mUserNameText.setText(account.getUserName());
    }
}
