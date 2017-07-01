package xyz.dcme.agg.ui.publish;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import jp.wasabeef.richeditor.RichEditor;
import xyz.dcme.agg.R;
import xyz.dcme.agg.ui.BaseFragment;

public class PublishFragment extends BaseFragment
        implements PublishContract.View {

    private static final int REQ_CODE_ALBUM = 100;
    private static final String LOG_TAG = "PublishFragment";
    private PublishContract.Presenter mPresenter;
    private TextView mResponse;
    private ImageButton mImgButton;
    private RichEditor mEditor;
    private EditText mTitle;

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
                getImageFromAlbum();
            }
        });

        mEditor = (RichEditor) mRootView.findViewById(R.id.editor);
        mEditor.setEditorFontSize(16);
        mEditor.setPadding(10, 10, 10, 10);
        mEditor.setPlaceholder(getString(R.string.please_input_content));
        mEditor.loadCSS("file:///android_asset/publish_image.css");

        mTitle = (EditText) mRootView.findViewById(R.id.publish_title);
    }

    private void getImageFromAlbum() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQ_CODE_ALBUM);
    }

    @Override
    protected void initPresenter() {
        mPresenter = new PublishPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_publish;
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

    }

    private void startPreview() {

    }
}
