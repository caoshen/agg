package xyz.dcme.agg.ui.publish;

import android.app.Activity;
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
import android.widget.TextView;

import java.io.File;

import xyz.dcme.agg.R;
import xyz.dcme.library.base.BaseFragment;
import xyz.dcme.agg.util.EditUtils;
import xyz.dcme.agg.util.ImageUtils;
import xyz.dcme.agg.util.LoginUtils;

public class PublishFragment extends BaseFragment
        implements PublishContract.View {
    private static final String LOG_TAG = "PublishFragment";

    private static final int REQ_CODE_ALBUM = 100;
    private static final int REQ_CODE_LOGIN = 200;
    private static final String ARG_URL = "argument_comment_url";

    private PublishContract.Presenter mPresenter;
    private ImageButton mImgButton;
    //    private RichEditor mEditor;
    private EditText mTitle;
    private EditText mContent;
    private TextView mUploadResponse;
    private String mCommentUrl;
    private boolean isSendingComment = false;

    public static Fragment newInstance(String commentUrl) {
        Fragment fragment = new PublishFragment();
        Bundle args = new Bundle();
        args.putString(ARG_URL, commentUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    protected void getArgs() {
        super.getArgs();
        Bundle args = getArguments();
        if (args != null) {
            mCommentUrl = args.getString(ARG_URL);
            isSendingComment = !TextUtils.isEmpty(mCommentUrl);
        }
    }

    @Override
    protected void initView() {
        mImgButton = (ImageButton) mRootView.findViewById(R.id.publish_image);
        mImgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUploadResponse.setText("");
                ImageUtils.getImageFromAlbum(PublishFragment.this, REQ_CODE_ALBUM);
            }
        });

        mTitle = (EditText) mRootView.findViewById(R.id.publish_title);
        mContent = (EditText) mRootView.findViewById(R.id.publish_content);
        mUploadResponse = (TextView) mRootView.findViewById(R.id.upload_response);

        if (isSendingComment) {
            mTitle.setVisibility(View.GONE);
            getActivity().setTitle(R.string.reply_to);
        }
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
                if (!LoginUtils.checkLogin(this, REQ_CODE_LOGIN)) {
                    break;
                }
                startSend();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void startSend() {
        boolean isValid;
        if (isSendingComment) {
            isValid = EditUtils.checkContentValid(getContext(), mContent);
            if (isValid) {
                String content = mContent.getText().toString();
                mPresenter.publishComment(content, mCommentUrl);
            }
        } else {
            isValid = EditUtils.checkContentValid(getContext(), mTitle, mContent);
            if (isValid) {
                String title = mTitle.getText().toString();
                String content = mContent.getText().toString();
                mPresenter.publishArticle(title, content, "IT");
            }
        }
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
            int len = mContent.getText().length();
            mContent.setSelection(len);
        }
    }

    @Override
    public void showUploadImageError(String response) {
        mUploadResponse.setText(response);
    }

    @Override
    public void showUploadTips(boolean active) {
        if (active) {
            mUploadResponse.setText(R.string.uploading_image);
        } else {
            mUploadResponse.setText("");
        }
    }

    @Override
    public void sendArticleSuccess() {
        getActivity().finish();
    }

    @Override
    public void sendArticleFail() {

    }

    @Override
    public void sendCommentSuccess() {
        getActivity().finish();
    }

    @Override
    public void sendCommentFail() {

    }
}
