package cn.okclouder.ovc.frag.write;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.QMUITopBar;

import java.io.File;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import cn.okclouder.ovc.R;
import cn.okclouder.ovc.base.BaseFragment;
import cn.okclouder.ovc.frag.node.NodeSelectActivity;
import cn.okclouder.ovc.ui.node.Node;
import cn.okclouder.ovc.ui.publish.PreviewActivity;
import cn.okclouder.ovc.ui.publish.PublishContract;
import cn.okclouder.ovc.ui.publish.PublishPresenter;
import cn.okclouder.ovc.util.EditUtils;
import cn.okclouder.ovc.util.ImageUtils;
import cn.okclouder.library.util.LogUtils;


public class WriteFragment extends BaseFragment implements PublishContract.View, EasyPermissions.PermissionCallbacks {
    private static final String LOG_TAG = "WriteFragment";

    private static final int REQ_CODE_ALBUM = 100;
    private static final int REQ_CODE_LOGIN = 200;
    private static final String ARG_URL = "argument_comment_url";
    private static final String ARG_NODE = "argument_selected_node";
    private static final int REQ_CODE_TAG = 300;
    private static final int RC_READ_EXTERNAL_STORAGE_PERM = 1000;

    private PublishContract.Presenter mPresenter;
    private ImageButton mImgButton;
    //    private RichEditor mEditor;
    private EditText mTitle;
    private EditText mContent;
    private TextView mUploadResponse;
    private String mCommentUrl;
    private boolean isSendingComment = false;
    private TextView mNodeText;
    private Node mSelectedNode;

    public static Fragment newInstance(String commentUrl, Node node) {
        Fragment fragment = new WriteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_URL, commentUrl);
        args.putParcelable(ARG_NODE, node);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View onCreateView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_write, null);
        initView(view);
        initPresenter();
        return view;
    }

    @Override
    protected boolean canDragBack() {
        return true;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mCommentUrl = args.getString(ARG_URL);
            isSendingComment = !TextUtils.isEmpty(mCommentUrl);
            mSelectedNode = args.getParcelable(ARG_NODE);
        }
    }

    protected void initView(View view) {
        mImgButton = (ImageButton) view.findViewById(R.id.publish_image);
        mImgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImages();
            }
        });

        mTitle = (EditText) view.findViewById(R.id.publish_title);
        mContent = (EditText) view.findViewById(R.id.publish_content);
        mUploadResponse = (TextView) view.findViewById(R.id.upload_response);

        mNodeText = (TextView) view.findViewById(R.id.node_selection);
        if (mSelectedNode != null) {
            mNodeText.setText(mSelectedNode.getTitle());
        }
        mNodeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NodeSelectActivity.class);
                intent.putExtra(NodeSelectActivity.KEY_SELECTED_NODE, mSelectedNode);
                startActivityForResult(intent, REQ_CODE_TAG);
            }
        });

        if (isSendingComment) {
            mTitle.setVisibility(View.GONE);
            mNodeText.setVisibility(View.GONE);
        }

        QMUITopBar topBar = view.findViewById(R.id.topbar);
        int str = TextUtils.isEmpty(mCommentUrl) ?R.string.publish : R.string.comment;
        topBar.setTitle(str);
        topBar.addLeftImageButton(R.drawable.ic_topbar_back_blue, R.id.qmui_topbar_item_left_back)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popBackStack();
                    }
                });
        topBar.addRightImageButton(R.drawable.ic_send_blue, R.id.id_send)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startSend();
                    }
                });
    }

    @AfterPermissionGranted(RC_READ_EXTERNAL_STORAGE_PERM)
    private void selectImages() {
        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(getContext(), perms)) {
            // Have permission, do the thing!
            mUploadResponse.setText("");
            ImageUtils.getImageFromAlbum(this, REQ_CODE_ALBUM);
        } else {
            LogUtils.e(LOG_TAG, "selectImages -> storage permissions denied.");
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.upload_image_permission),
                    RC_READ_EXTERNAL_STORAGE_PERM, perms);
        }
    }

    protected void initPresenter() {
        mPresenter = new PublishPresenter(this);
    }

    @Override
    public void setPresenter(PublishContract.Presenter presenter) {
        mPresenter = presenter;
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
            isValid = EditUtils.checkContentValid(getContext(), mTitle, mContent)
                    && mSelectedNode != null;
            if (isValid) {
                String title = mTitle.getText().toString();
                String content = mContent.getText().toString();
                mPresenter.publishArticle(title, content, mSelectedNode.getName());
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
        if (requestCode == REQ_CODE_ALBUM) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getData();
                String imagePathFromURI = ImageUtils.getImagePathFromURI(getActivity(), uri);
                if (TextUtils.isEmpty(imagePathFromURI)) {
                    return;
                }
                File imageFile = new File(imagePathFromURI);
                uploadImage(imageFile);
            }
        } else if (requestCode == REQ_CODE_TAG && resultCode == Activity.RESULT_OK) {
            mSelectedNode = data.getParcelableExtra(NodeSelectActivity.KEY_SELECTED_NODE);
            if (null != mSelectedNode) {
                mNodeText.setText(mSelectedNode.getTitle());
            }
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

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        LogUtils.d(LOG_TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size());
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        LogUtils.d(LOG_TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size());
        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this)
                    .setTitle(R.string.need_storage_permission)
                    .setRationale(R.string.storage_permission_rational)
                    .build().show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
