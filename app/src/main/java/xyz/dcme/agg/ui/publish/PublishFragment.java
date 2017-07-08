package xyz.dcme.agg.ui.publish;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.io.File;

import xyz.dcme.agg.R;
import xyz.dcme.agg.ui.BaseFragment;
import xyz.dcme.agg.util.ImageUtils;
import xyz.dcme.agg.util.LogUtils;
import xyz.dcme.agg.util.LoginUtils;
import xyz.dcme.agg.util.StringUtils;

public class PublishFragment extends BaseFragment
        implements PublishContract.View {
    private static final String LOG_TAG = "PublishFragment";

    private static final int REQ_CODE_ALBUM = 100;
    private static final int REQ_CODE_LOGIN = 200;

    private PublishContract.Presenter mPresenter;
    private ImageButton mImgButton;
//    private RichEditor mEditor;
    private EditText mTitle;
    private EditText mContent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    protected void initView() {
        mImgButton = (ImageButton) mRootView.findViewById(R.id.publish_image);
        mImgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageUtils.getImageFromAlbum(PublishFragment.this, REQ_CODE_ALBUM);
            }
        });

        mTitle = (EditText) mRootView.findViewById(R.id.publish_title);
        mContent = (EditText) mRootView.findViewById(R.id.publish_content);
    }

    @Override
    protected void initPresenter() {
        mPresenter = new PublishPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_publish;
    }

    @Override
    public void setPresenter(PublishContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_publish, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_publish_preview: {
                if (checkValid()) {
                    startPreview();
                }
                break;
            }
            case R.id.item_publish_send: {
                if (checkValid()) {
                    startSend();
                }
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean checkValid() {
        String title = mTitle.getText().toString();
        String content = mContent.getText().toString();
        if (StringUtils.isBlank(title)) {
            mTitle.setError(getString(R.string.tips_title_is_empty));
            return false;
        }
        if (StringUtils.isBlank(content)) {
            mContent.setError(getString(R.string.tips_content_is_empty));
            return false;
        }
        return true;
    }

    private void startSend() {
        String title = mTitle.getText().toString();
        String content = mContent.getText().toString();
        LogUtils.d(LOG_TAG, "startSend -> title: " + title + " content: " + content);
        mPresenter.publishArticle(title, content, "IT");
    }

    private void startPreview() {
        String title = mTitle.getText().toString();
        String content = mContent.getText().toString();
        PreviewActivity.startPreview(getActivity(), title, content);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE_ALBUM && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            File file = new File(ImageUtils.getImagePathFromURI(getActivity(), uri));
            uploadImage(file);
        }
    }

    private void uploadImage(File file) {
        mPresenter.uploadImage(file);
    }

    @Override
    public void insertImage(String imageUrl, String imageName) {
        if (mContent != null && !TextUtils.isEmpty(imageUrl) && !TextUtils.isEmpty(imageName)) {
            mContent.append(imageUrl);
        }
    }

    @Override
    public Context getViewContext() {
        return getContext();
    }

    @Override
    public void startLogin() {
        LoginUtils.startLogin(this, REQ_CODE_LOGIN);
    }

    public static Fragment newInstance() {
        return new PublishFragment();
    }
}
