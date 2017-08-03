package xyz.dcme.agg.ui.topic;

import android.app.Activity;

import java.util.Stack;

public class AppManager {
    private static AppManager mInstance;
    private Stack<Activity> mActivityStack = new Stack<>();

    private AppManager() {

    }

    public static AppManager getInstance() {
        if (null == mInstance) {
            synchronized (AppManager.class) {
                if (null == mInstance) {
                    mInstance = new AppManager();
                }
            }
        }
        return mInstance;
    }

    public void addActivity(Activity activity) {
        if (null != activity) {
            mActivityStack.add(activity);
        }
    }

    public void removeActivity(Activity activity) {
        if (null != activity) {
            mActivityStack.remove(activity);
        }
    }

    public void finishActivity(Activity activity) {
        if (null != activity) {
            mActivityStack.remove(activity);
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
