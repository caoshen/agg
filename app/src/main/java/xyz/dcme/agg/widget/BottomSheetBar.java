package xyz.dcme.agg.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import xyz.dcme.agg.R;
import xyz.dcme.library.util.StringUtils;
import xyz.dcme.library.util.DeviceUtils;

public class BottomSheetBar {

    private final Context mContext;
    private View mRootView;
    private EditText mComment;
    private ImageButton mSend;
    private BottomDialog mDialog;
    private ProgressBar mProgressBar;

    private BottomSheetBar(Context context) {
        mContext = context;
    }

    @SuppressLint("InflateParams")
    public static BottomSheetBar delegation(Context context) {
        BottomSheetBar bar = new BottomSheetBar(context);
        bar.mRootView = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_comment_bar, null);
        bar.initViews();
        return bar;
    }

    private void initViews() {
        if (mRootView == null) {
            return;
        }
        mComment = (EditText) mRootView.findViewById(R.id.comment);
        mSend = (ImageButton) mRootView.findViewById(R.id.send_button);
        mProgressBar = (ProgressBar) mRootView.findViewById(R.id.send_progress);

        mComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String comment = mComment.getText().toString();
                boolean isInputEmpty = StringUtils.isBlank(comment);
                mSend.setVisibility(isInputEmpty ? View.GONE : View.VISIBLE);
            }
        });
        mSend.setVisibility(View.GONE);

        mDialog = new BottomDialog(mContext, false);
        mDialog.setContentView(mRootView);
        mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                DeviceUtils.closeKeyboard(mComment);
            }
        });
    }

    public void show() {
        if (mDialog == null || mRootView == null) {
            return;
        }
        mDialog.show();
        mRootView.postDelayed(new Runnable() {
            @Override
            public void run() {
                DeviceUtils.showSoftKeyboard(mComment);
            }
        }, 50);
    }

    public void hide() {
        if (mDialog == null || mRootView == null) {
            return;
        }
        mDialog.hide();
        if (mProgressBar.getVisibility() == View.VISIBLE) {
            mProgressBar.setVisibility(View.GONE);
        }
        String comment = mComment.getText().toString();
        if (!TextUtils.isEmpty(comment)) {
            mComment.setText(R.string.empty_str);
        }
        mRootView.postDelayed(new Runnable() {
            @Override
            public void run() {
                DeviceUtils.hideSoftKeyboard(mComment);
            }
        }, 50);
    }

    public void setSendCommentListener(View.OnClickListener listener) {
        if (mSend != null && listener != null) {
            mSend.setOnClickListener(listener);
        }
    }

    public String getComment() {
        if (mComment != null) {
            return mComment.getText().toString();
        } else {
            return null;
        }
    }

    public void setComment(String comment) {
        if (mComment != null) {
            mComment.setText(comment);
            int length = mComment.getText().length();
            mComment.setSelection(length);
        }
    }

    public void sendingComment(boolean isSending) {
        if (isSending) {
            mSend.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mSend.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.GONE);
        }
    }
}
