package cn.okclouder.ovc.frag.write;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.QMUITopBar;

import cn.okclouder.ovc.R;
import cn.okclouder.ovc.base.BaseFragment;
import cn.okclouder.ovc.frag.node.NodeSelectActivity;
import cn.okclouder.ovc.ui.node.Node;
import cn.okclouder.ovc.ui.publish.PublishContract;
import cn.okclouder.ovc.ui.publish.PublishPresenter;
import cn.okclouder.ovc.util.EditUtils;


public class WriteFragment extends BaseFragment implements PublishContract.View {
    private static final String LOG_TAG = "WriteFragment";

    private static final String ARG_URL = "argument_comment_url";
    private static final String ARG_NODE = "argument_selected_node";
    private static final int REQ_CODE_TAG = 300;

    private PublishContract.Presenter mPresenter;
    private EditText mTitle;
    private EditText mContent;
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
        mTitle = view.findViewById(R.id.publish_title);
        mContent = view.findViewById(R.id.publish_content);
        mNodeText = view.findViewById(R.id.node_selection);
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
        int str = TextUtils.isEmpty(mCommentUrl) ? R.string.publish : R.string.comment;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE_TAG && resultCode == Activity.RESULT_OK) {
            mSelectedNode = data.getParcelableExtra(NodeSelectActivity.KEY_SELECTED_NODE);
            if (null != mSelectedNode) {
                mNodeText.setText(mSelectedNode.getTitle());
            }
        }
    }

    @Override
    public void sendArticleSuccess() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.finish();
        }
    }

    @Override
    public void sendArticleFail() {

    }

    @Override
    public void sendCommentSuccess() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.finish();
        }
    }

    @Override
    public void sendCommentFail() {

    }

}
