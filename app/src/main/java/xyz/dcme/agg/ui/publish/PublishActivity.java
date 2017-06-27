package xyz.dcme.agg.ui.publish;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;

import okhttp3.Call;
import xyz.dcme.agg.R;
import xyz.dcme.agg.ui.BaseActivity;
import xyz.dcme.agg.util.LogUtils;


public class PublishActivity extends BaseActivity {

    private static final int REQ_CODE_ALBUM = 100;
    private static final String LOG_TAG = "PublishActivity";
    private TextView mResponse;
    private Button mBtn;

    @Override
    public int getLayoutId() {
        return R.layout.activity_publish;
    }

    @Override
    public void initView() {
        mResponse = (TextView) findViewById(R.id.publish_response);
        mBtn = (Button) findViewById(R.id.publish_image);

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImageFromAlbum();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE_ALBUM && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            File file = new File(getRealPathFromURI(uri));
            uploadImage(file);
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if(cursor.moveToFirst()){;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    private void uploadImage(File file) {
        String filePath = file.getAbsolutePath();
        String fileName = filePath.substring(filePath.lastIndexOf("/")+1);
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
                    }
                });
    }

    private void getImageFromAlbum() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQ_CODE_ALBUM);
    }
}
