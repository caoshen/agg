package xyz.dcme.agg.frag.about;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.QMUITopBar;

import xyz.dcme.agg.R;
import xyz.dcme.agg.base.BaseFragment;
import xyz.dcme.agg.util.Constants;
import xyz.dcme.agg.util.VersionUtil;

public class AboutFragment extends BaseFragment {
    @Override
    protected View onCreateView() {
        View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_about, null);
        initViews(rootView);
        return rootView;
    }

    private void initViews(View rootView) {
        TextView appName = (TextView) rootView.findViewById(R.id.app_name);
        appName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(Constants.HOME_URL));
                startActivity(intent);
            }
        });

        TextView version = (TextView) rootView.findViewById(R.id.app_version);
        String versionName = VersionUtil.getAppVersionName(getActivity());
        version.setText(versionName);

        QMUITopBar topbar = rootView.findViewById(R.id.topbar);
        topbar.setTitle(R.string.about);
        topbar.addLeftImageButton(xyz.dcme.account.R.drawable.ic_topbar_back_blue, com.qmuiteam.qmui.R.id.qmui_topbar_item_left_back)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popBackStack();
                    }
                });
    }

    @Override
    protected boolean canDragBack() {
        return true;
    }
}
