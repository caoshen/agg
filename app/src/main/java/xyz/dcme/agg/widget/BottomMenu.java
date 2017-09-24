package xyz.dcme.agg.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import xyz.dcme.agg.R;

public class BottomMenu implements View.OnClickListener {

    private View mRootView;
    private Context mContext;
    private BottomDialog mBottomDialog;
    private OnMenuItemSelectCallback mListener;
    private LinearLayout mShareItem;
    private LinearLayout mFavItem;
    private LinearLayout mBrowserItem;
    private LinearLayout mCopyLinkItem;

    private BottomMenu(Context context) {
        mContext = context;
    }

    public static BottomMenu delegation(Context context) {
        BottomMenu menu = new BottomMenu(context);
        menu.mRootView = LayoutInflater.from(context).inflate(R.layout.bottom_menu_bar, null);
        menu.initViews();
        return menu;
    }

    public void setMenuListener(OnMenuItemSelectCallback listener) {
        mListener = listener;
    }

    private void initViews() {
        if (null == mRootView) {
            return;
        }
        mBottomDialog = new BottomDialog(mContext, false);
        mBottomDialog.setContentView(mRootView);

        mShareItem = (LinearLayout) mRootView.findViewById(R.id.item_share);
        mShareItem.setOnClickListener(this);
        mFavItem = (LinearLayout) mRootView.findViewById(R.id.item_fav);
        mFavItem.setOnClickListener(this);
        mBrowserItem = (LinearLayout) mRootView.findViewById(R.id.item_open_browser);
        mBrowserItem.setOnClickListener(this);
        mCopyLinkItem = (LinearLayout) mRootView.findViewById(R.id.item_copy_link);
        mCopyLinkItem.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_share: {
                if (null != mListener) {
                    mListener.onShare();
                    mBottomDialog.dismiss();
                }
                break;
            }
            case R.id.item_fav: {
                if (null != mListener) {
                    mListener.onFavourite();
                    mBottomDialog.dismiss();
                }
                break;
            }
            case R.id.item_open_browser: {
                if (null != mListener) {
                    mListener.onOpenInBrowser();
                    mBottomDialog.dismiss();
                }
                break;
            }
            case R.id.item_copy_link: {
                if (null != mListener) {
                    mListener.onCopyLink();
                    mBottomDialog.dismiss();
                }
                break;
            }
        }
    }

    public void show() {
        if (mBottomDialog == null || mRootView == null) {
            return;
        }
        mBottomDialog.show();
    }

    public interface OnMenuItemSelectCallback {
        void onShare();

        void onFavourite();

        void onOpenInBrowser();

        void onCopyLink();
    }
}
