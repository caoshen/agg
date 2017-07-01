package xyz.dcme.agg.ui.publish;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import jp.wasabeef.richeditor.RichEditor;
import okhttp3.Call;
import xyz.dcme.agg.R;
import xyz.dcme.agg.ui.BaseActivity;
import xyz.dcme.agg.util.ImageUtils;
import xyz.dcme.agg.util.LogUtils;


public class PublishActivity extends BaseActivity {

    private static final int REQ_CODE_ALBUM = 100;
    private static final String LOG_TAG = "PublishActivity";
    private TextView mResponse;
    private ImageButton mImgButton;
    private RichEditor mEditor;
    private EditText mTitle;

    public static void startPublish(Context context) {
        Intent intent = new Intent(context, PublishActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_publish;
    }

    @Override
    public void initView() {
        mResponse = (TextView) findViewById(R.id.publish_response);
        mImgButton = (ImageButton) findViewById(R.id.publish_image);

        mImgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageUtils.getImageFromAlbum(PublishActivity.this, REQ_CODE_ALBUM);
            }
        });
        mEditor = (RichEditor) findViewById(R.id.editor);
        mEditor.setEditorFontSize(16);
        mEditor.setPadding(10, 10, 10, 10);
        mEditor.setPlaceholder(getString(R.string.please_input_content));
        mEditor.loadCSS("file:///android_asset/publish_image.css");

        mTitle = (EditText) findViewById(R.id.publish_title);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE_ALBUM && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            File file = new File(ImageUtils.getImagePathFromURI(this, uri));
            uploadImage(file);
        }
    }

    private void uploadImage(File file) {
        String filePath = file.getAbsolutePath();
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
        String url = "http://www.guanggoo.com/image_upload";
        OkHttpUtils.post()
                .addFile("files", fileName, file)
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        mResponse.setText("error: " + e.toString());
                        LogUtils.e(LOG_TAG, "error: " + e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        mResponse.setText("response: " + response);
                        LogUtils.d(LOG_TAG, "response: " + response);
                        String imageUrl = null;
                        String imageName = null;
                        try {
                            JSONObject resp = new JSONObject(response);
                            JSONArray files = resp.getJSONArray("files");
                            if (files != null && files.length() > 0) {
                                JSONObject file = files.getJSONObject(0);
                                imageUrl = file.getString("url");
                                imageName = file.getString("name");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        mEditor.insertImage(imageUrl, imageName);
                    }
                });
    }


}
