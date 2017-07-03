package xyz.dcme.agg.ui.publish;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;

import java.io.File;

import jp.wasabeef.richeditor.RichEditor;
import xyz.dcme.agg.R;
import xyz.dcme.agg.ui.BaseFragment;
import xyz.dcme.agg.util.ImageUtils;
import xyz.dcme.agg.util.LoginUtils;

public class PublishFragment extends BaseFragment
        implements PublishContract.View {
    private static final String LOG_TAG = "PublishFragment";

    private static final int REQ_CODE_ALBUM = 100;
    private static final int REQ_CODE_LOGIN = 200;

    private PublishContract.Presenter mPresenter;
    private ImageButton mImgButton;
    private RichEditor mEditor;
    private EditText mTitle;
    private AppCompatSpinner mNodeSpinner;

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

        mEditor = (RichEditor) mRootView.findViewById(R.id.editor);
        mEditor.setEditorFontSize(16);
        mEditor.setPadding(10, 10, 10, 10);
        mEditor.setPlaceholder(getString(R.string.please_input_content));
        mEditor.loadCSS("file:///android_asset/publish_image.css");

        mTitle = (EditText) mRootView.findViewById(R.id.publish_title);
        mNodeSpinner = (AppCompatSpinner) mRootView.findViewById(R.id.publish_nodes);
        mNodeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
                startPreview();
                break;
            }
            case R.id.item_publish_send: {
                startSend();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void startSend() {
        String title = mTitle.getText().toString();
        String content = mEditor.getHtml();
        mPresenter.publishArticle(title, content, "water");
    }

    private void startPreview() {

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
        if (mEditor != null && !TextUtils.isEmpty(imageUrl) && !TextUtils.isEmpty(imageName)) {
            mEditor.insertImage(imageUrl, imageName);
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
}
