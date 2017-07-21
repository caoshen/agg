package xyz.dcme.agg.ui.publish.helper;

import android.text.TextUtils;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import okhttp3.Call;
import xyz.dcme.agg.ui.publish.ImageUploadError;
import xyz.dcme.agg.util.LogUtils;

public class ImageUploadHelper {
    private static final String LOG_TAG = "ImageUploadHelper";

    /**
     * TODO: upload procedure
     * @param file
     * @param listener
     */
    public void uploadImage(File file, final ImageUpLoadListener listener) {
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
                        LogUtils.e(LOG_TAG, "uploadImage -> error: " + e);
                        listener.onError(e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.d(LOG_TAG, "uploadImage -> response: " + response);
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
                            LogUtils.e(LOG_TAG, "uploadImage -> error: " + e);
                            listener.onError(e.getMessage());
                        }

                        if (!TextUtils.isEmpty(imageUrl) && !TextUtils.isEmpty(imageName)) {
                            listener.onResponse(response);
                        } else {
                            if (TextUtils.isEmpty(imageUrl)) {
                                String errorMessage = ImageUploadError.getErrorMessage(imageName);
                                LogUtils.e(LOG_TAG, errorMessage);
                            }
                        }
                    }
                });
    }
}
