package xyz.dcme.agg.frag.home;


import xyz.dcme.agg.base.BaseFragment;
import xyz.dcme.arch.QMUIFragment;

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
