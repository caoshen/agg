package xyz.dcme.agg.ui.publish.helper;

import android.text.TextUtils;

import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import okhttp3.Call;
import xyz.dcme.agg.util.HttpUtils;
import xyz.dcme.library.util.LogUtils;

public class ImageUploadHelper {
    private static final String LOG_TAG = "ImageUploadHelper";
    private static final String IMAGE_UPLOAD = "http://www.guanggoo.com/image_upload";
    private static final String FILES = "files";
    private static final String URL = "url";
    private static final String NAME = "name";

    /**
     * Upload image by posting website form
     * @param file image file
     * @param listener upload callback
     */
    public void uploadImage(File file, final ImageUpLoadListener listener) {
        String filePath = file.getAbsolutePath();
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);

        HttpUtils.post(IMAGE_UPLOAD, fileName, file, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                LogUtils.e(LOG_TAG, "uploadImage -> error: " + e);
                listener.onError("uploadImage -> error: " + e);
            }

            @Override
            public void onResponse(String response, int id) {
                LogUtils.d(LOG_TAG, "uploadImage -> response: " + response);
                String imageUrl = null;
                String imageName = null;
                try {
                    JSONObject resp = new JSONObject(response);
                    JSONArray files = resp.getJSONArray(FILES);
                    if (files != null && files.length() > 0) {
                        JSONObject file = files.getJSONObject(0);
                        imageUrl = file.getString(URL);
                        imageName = file.getString(NAME);
                    }
                } catch (JSONException e) {
                    LogUtils.e(LOG_TAG, "uploadImage -> json error: " + e);
                    listener.onError("uploadImage -> json error: " + e);
                }

                if (!TextUtils.isEmpty(imageUrl) && !TextUtils.isEmpty(imageName)) {
                    listener.onResponse(imageName, imageUrl);
                } else {
                    if (TextUtils.isEmpty(imageUrl)) {
                        String errorMessage = ImageUploadError.getErrorMessage(imageName);
                        LogUtils.e(LOG_TAG, errorMessage);
                        listener.onError(errorMessage);
                    }
                }
            }
        });
    }
}
