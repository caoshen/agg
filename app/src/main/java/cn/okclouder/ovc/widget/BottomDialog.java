package cn.okclouder.ovc.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;


public class BottomDialog extends BottomSheetDialog {
    private CoordinatorLayout.Behavior mBehavior;

    public BottomDialog(@NonNull Context context, boolean isTranslucentStatus) {
        super(context);
        Window window = getWindow();
        if (window != null) {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
                    | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            if (isTranslucentStatus) {
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        init(view);
    }

    private void init(View view) {
        ViewParent parent = view.getParent();
        if (!(parent instanceof ViewGroup)) {
            return;
        }

        ViewGroup.LayoutParams layoutParams = ((ViewGroup) parent).getLayoutParams();
        if (!(layoutParams instanceof CoordinatorLayout.LayoutParams)) {
            return;
        }

        mBehavior = ((CoordinatorLayout.LayoutParams) layoutParams).getBehavior();
        if (!(mBehavior instanceof BottomSheetBehavior)) {
            return;
        }

        ((BottomSheetBehavior) mBehavior).setBottomSheetCallback(
                new BottomSheetBehavior.BottomSheetCallback() {
                    @Override
                    public void onStateChanged(@NonNull View bottomSheet, int newState) {
                        if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                            dismiss();
                            ((BottomSheetBehavior) mBehavior).setState(BottomSheetBehavior.STATE_COLLAPSED);
                        }
                    }

                    @Override
                    public void onSlide(@NonNull View bottomSheet, float slideOffset) {

                    }
                });
    }
}
