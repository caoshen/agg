package cn.okclouder.ovc.frag.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import java.util.List;

import cn.okclouder.ovc.R;
import cn.okclouder.ovc.base.BaseFragment;
import cn.okclouder.ovc.ui.personal.detail.Detail;
import cn.okclouder.ovc.ui.personal.page.Count;
import cn.okclouder.library.util.ImageLoader;


public class UserHomePageFragment extends BaseFragment implements UserHomeContract.View {
    public static final String TAG = UserHomePageFragment.class.getSimpleName();
    private static final String ARGS_KEY_NAME = "args_key_name";
    private String mUserName;
    private ImageView mHeader;
    private UserHomeContract.Presenter mPresenter;
    private QMUIGroupListView mGroupListView;
    private QMUICommonListItemView mItemTopic;
    private QMUICommonListItemView mItemReply;
    private QMUICommonListItemView mItemFav;

    public static Fragment newInstance(String name) {
        Fragment fragment = new UserHomePageFragment();
        Bundle args = new Bundle();
        args.putString(ARGS_KEY_NAME, name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View onCreateView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_user_home_page, null);
        initViews(view);
        initPresenter();
        return view;
    }

    private void initPresenter() {
        mPresenter = new UserHomePresenter(this);
        mPresenter.query(mUserName);
    }

    private void initViews(View rootView) {
        QMUITopBar topbar = rootView.findViewById(R.id.topbar);
        topbar.setTitle(mUserName);
        topbar.addLeftImageButton(R.drawable.ic_topbar_back_blue, R.id.qmui_topbar_item_left_back)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popBackStack();
                    }
                });

        View itemInfo = rootView.findViewById(R.id.item_info);
        mHeader = itemInfo.findViewById(R.id.user_avatar);
        TextView name = itemInfo.findViewById(R.id.user_name);
        name.setText(mUserName);

        mGroupListView = rootView.findViewById(R.id.groupListView);
        mItemTopic = mGroupListView.createItemView(getString(R.string.count_topic));
        mItemTopic.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        mItemReply = mGroupListView.createItemView(getString(R.string.count_reply));
        mItemReply.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        mItemFav = mGroupListView.createItemView(getString(R.string.fav));
        mItemFav.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        QMUIGroupListView.newSection(getActivity())
                .addItemView(mItemTopic, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startFragment(UserCommonRecyclerFragment.newInstance(mUserName, 1));
                    }
                })
                .addItemView(mItemReply, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startFragment(UserCommonRecyclerFragment.newInstance(mUserName, 2));
                    }
                })
                .addItemView(mItemFav, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startFragment(UserCommonRecyclerFragment.newInstance(mUserName, 3));
                    }
                })
                .addTo(mGroupListView);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mUserName = arguments.getString(ARGS_KEY_NAME);
        }
    }

    @Override
    protected boolean canDragBack() {
        return true;
    }

    @Override
    public void showImage(String imageUrl) {
        ImageLoader.displayCircle(getActivity(), mHeader, imageUrl);
    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void showDetails(List<Detail> details) {
        if (mGroupListView == null) {
            return;
        }
        QMUIGroupListView.Section section = QMUIGroupListView.newSection(getActivity());
        for (Detail detail : details) {
            QMUICommonListItemView itemView = mGroupListView.createItemView(detail.title);
            itemView.setOrientation(QMUICommonListItemView.VERTICAL);
            itemView.setDetailText(detail.content);
            section.addItemView(itemView, null);
        }
        section.addTo(mGroupListView);
    }

    @Override
    public void showCount(Count count) {
        mItemTopic.setDetailText(String.valueOf(count.topicCount));
        mItemReply.setDetailText(String.valueOf(count.replyCount));
        mItemFav.setDetailText(String.valueOf(count.favCount));
    }

    @Override
    public void setPresenter(UserHomeContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
