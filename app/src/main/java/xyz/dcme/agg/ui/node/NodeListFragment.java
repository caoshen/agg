package xyz.dcme.agg.ui.node;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import xyz.dcme.agg.util.Constants;

public class NodeListFragment extends Fragment {

    public static Fragment newInstance(Node node) {
        NodeListFragment fragment = new NodeListFragment();
        Bundle args = new Bundle();
        args.putString(Constants.NODE_NAME, node.getName());
        fragment.setArguments(args);
        return fragment;
    }
}
