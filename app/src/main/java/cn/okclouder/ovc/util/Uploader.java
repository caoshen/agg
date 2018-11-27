package cn.okclouder.ovc.util;

import com.zhy.http.okhttp.OkHttpUtils;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import cn.okclouder.library.util.LogUtils;

public class Uploader {

    private static final String LOG_TAG = "Uploader";

    public static String upload(String imageType, String userPhone, File file) {
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), file);
        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", "upload_image", fileBody)
                .addFormDataPart("imagetype", imageType)
                .addFormDataPart("userphone", userPhone)
                .build();

        Request request = new Request.Builder()
                .url(Constants.UPLOAD_IMAGE_URL)
                .post(body)
                .build();

        Response response;
        try {
            response = OkHttpUtils.getInstance().getOkHttpClient()
                    .newCall(request).execute();
            String jsonString = response.body().toString();
            LogUtils.d(LOG_TAG, "upload -> jsonString: " + jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
