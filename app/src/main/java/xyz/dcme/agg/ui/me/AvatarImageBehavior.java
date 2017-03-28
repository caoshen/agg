package xyz.dcme.agg.ui.me;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

import de.hdodenhof.circleimageview.CircleImageView;
import xyz.dcme.agg.R;

import static xyz.dcme.agg.util.LogUtils.LOGE;
import static xyz.dcme.agg.util.LogUtils.makeLogTag;


public class AvatarImageBehavior extends CoordinatorLayout.Behavior<CircleImageView> {

    private static final String TAG = makeLogTag("AvatarImageBehavior");

    private float mFinalLeftAvatarPadding;
    private float mStartToolbarPosition;
    private float mStartHeight;
    private float mFinalHeight;
    private float mFinalXPosition;
    private float mFinalYPosition;
    private float mStartXPosition;
    private float mStartYPosition;
    private float mAvatarMaxSize;

    private Context mContext;
    private float mChangeBehaviorPoint;

    public AvatarImageBehavior(Context context, AttributeSet attrs) {
        mContext = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AvatarImageBehavior);
        try {
            mStartXPosition = a.getDimension(R.styleable.AvatarImageBehavior_startXPosition, 0);
            mFinalYPosition = a.getDimension(R.styleable.AvatarImageBehavior_finalYPosition, 0);
            mStartToolbarPosition = a.getDimension(R.styleable.AvatarImageBehavior_startToolbarPosition, 0);
            mStartHeight = a.getDimension(R.styleable.AvatarImageBehavior_startHeight, 0);
            mFinalHeight = a.getDimension(R.styleable.AvatarImageBehavior_finalHeight, 0);
        } catch (Exception e) {
            LOGE(TAG, e.getMessage());
        } finally {
            a.recycle();
        }
        init();

        mFinalLeftAvatarPadding = context.getResources().getDimension(R.dimen.spacing_normail);
    }

    private void init() {
        bindDimensions();
    }

    private void bindDimensions() {
        mAvatarMaxSize = mContext.getResources().getDimension(R.dimen.image_width);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, CircleImageView child, View dependency) {
        return dependency instanceof Toolbar;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, CircleImageView child, View dependency) {
        maybeInitProperties(child, dependency);
        final int maxScrollDistance = (int) mStartToolbarPosition;
        float expandPercentageFactor = dependency.getY() / maxScrollDistance;

        if (expandPercentageFactor < mChangeBehaviorPoint) {
            float factor = (mChangeBehaviorPoint - expandPercentageFactor) / mChangeBehaviorPoint;
            float distanceXToSubtract = (mStartXPosition - mFinalXPosition) * factor + child.getHeight() / 2;
            float distanceYToSubtract = (mStartYPosition - mFinalYPosition) * (1f - expandPercentageFactor)
                    + child.getHeight() / 2;
            child.setX(mStartXPosition - distanceXToSubtract);
            child.setY(mStartYPosition - distanceYToSubtract);

            float heightToSubtract = (mStartHeight - mFinalHeight) * factor;
            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
            params.width = (int) (mStartHeight - heightToSubtract);
            params.height = (int) (mStartHeight - heightToSubtract);
            child.setLayoutParams(params);
        } else {
            float distanceYToSubtract = (mStartYPosition - mFinalYPosition) * (1f - expandPercentageFactor)
                    + mStartHeight / 2;
            child.setX(mStartXPosition - child.getWidth() / 2);
            child.setY(mStartYPosition - distanceYToSubtract);

            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
            params.width = (int) (mStartHeight);
            params.height = (int) (mStartHeight);
            child.setLayoutParams(params);
        }
        return true;
    }

    private void maybeInitProperties(CircleImageView child, View dependency) {
        if (mStartYPosition == 0) {
            mStartYPosition = (int) dependency.getY();
        }

        if (mFinalYPosition == 0) {
            mFinalYPosition = (dependency.getHeight() / 2);
        }

        if (mStartHeight == 0) {
            mStartHeight = child.getHeight();
        }

        if (mStartXPosition == 0) {
            mStartXPosition = (int) (child.getX() + (child.getWidth() / 2));
        }

        if (mFinalXPosition == 0) {
            mFinalXPosition = mContext.getResources()
                    .getDimensionPixelOffset(R.dimen.abc_action_bar_content_inset_material)
                    + ((int) mFinalHeight / 2);
        }

        if (mStartToolbarPosition == 0) {
            mStartToolbarPosition = dependency.getY();
        }

        if (mChangeBehaviorPoint == 0) {
            mChangeBehaviorPoint = (child.getHeight() - mFinalHeight)
                    / (2f * (mStartYPosition - mFinalYPosition));
        }
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = mContext.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}

