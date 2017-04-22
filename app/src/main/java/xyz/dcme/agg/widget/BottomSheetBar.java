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

import xyz.dcme.agg.R;
import xyz.dcme.agg.util.TDevice;

public class BottomSheetBar {

    private final Context mContext;
    private View mRootView;
    private EditText mComment;
    private ImageButton mSend;
    private BottomDialog mDialog;

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
                boolean isInputEmpty = TextUtils.isEmpty(comment);
                mSend.setVisibility(isInputEmpty ? View.GONE : View.VISIBLE);
            }
        });
        mSend.setVisibility(View.GONE);

        mDialog = new BottomDialog(mContext, false);
        mDialog.setContentView(mRootView);
        mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                TDevice.closeKeyboard(mComment);
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
                TDevice.showSoftKeyboard(mComment);
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
}
