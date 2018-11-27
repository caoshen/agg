package cn.okclouder.ovc.frag.home;


import cn.okclouder.ovc.base.BaseFragment;
import cn.okclouder.arch.QMUIFragment;

public abstract class HomeControllerFragment extends BaseFragment {
    private HomeControllerListener mListener;

    public void setHomeControllerListener(HomeControllerListener listener) {
        mListener = listener;
    }

    @Override
    protected void startFragment(QMUIFragment fragment) {
        if (mListener != null) {
            mListener.startFragment(fragment);
        }
    }

    public interface HomeControllerListener {
        void startFragment(QMUIFragment fragment);
    }
}
