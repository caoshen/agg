package cn.okclouder.ovc.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import cn.okclouder.ovc.R;

public class LoadingTip extends LinearLayout {
    private ProgressBar mProgressBar;
    private TextView mTips;
    private Button mReloadBtn;
    private OnReloadListener mReloadListener;

    public LoadingTip(Context context) {
        super(context);
        initView(context);
    }

    public LoadingTip(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public LoadingTip(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View.inflate(context, R.layout.loading_tip, this);
        mProgressBar = (ProgressBar) findViewById(R.id.progress);
        mTips = (TextView) findViewById(R.id.text_tips);
        mReloadBtn = (Button) findViewById(R.id.reload_btn);
        mReloadBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mReloadListener) {
                    mReloadListener.onReload();
                }
            }
        });
        setVisibility(View.GONE);
    }

    public enum LoadStatus {
        SERVER_ERR,
        ERROR,
        EMPTY,
        LOADING,
        FINISH,
        NEED_LOGIN
    }

    public interface OnReloadListener {
        void onReload();
    }

    public void setOnReloadListener(OnReloadListener listener) {
        mReloadListener = listener;
    }

    public void setTips(String tips) {
        if (null != mTips) {
            mTips.setText(tips);
        }
    }

    public void setLoadingTip(LoadStatus status) {
        switch (status) {
            case SERVER_ERR: {
                setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.GONE);
                mTips.setText(R.string.server_error);
                break;
            }
            case ERROR: {
                setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.GONE);
                mTips.setText(R.string.network_error);
                break;
            }
            case EMPTY: {
                setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.GONE);
                mTips.setText(R.string.no_data);
                break;
            }
            case LOADING: {
                setVisibility(View.VISIBLE);
                break;
            }
            case NEED_LOGIN: {
                setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.GONE);
                mReloadBtn.setVisibility(View.VISIBLE);
                mTips.setText(R.string.login_tips);
                mReloadBtn.setText(R.string.login);
                break;
            }
            case FINISH: {
                setVisibility(View.GONE);
                break;
            }
            default:
                break;
        }
    }
}
