package cn.okclouder.ovc.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.text.Html;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.okclouder.library.util.LogUtils;

public class NetWorkImageGetter implements Html.ImageGetter {
    private final TextView mTextView;
    private final int mPicWidth;
    private final String mContent;
    private final int mImageCount;
    private final Context mContext;
    private final String mFilePath;
    private int mCurImage;
    private String mPicName;

    public NetWorkImageGetter(TextView textView, String content, int imageCount) {
        mTextView = textView;
        mContext = textView.getContext();
        mPicWidth = textView.getWidth();
        mContent = content;
        mImageCount = imageCount;
        mFilePath = mContext.getCacheDir().getAbsolutePath();
    }

    /**
     * 网络图片
     *
     * @author Susie
     */
    @Override
    public Drawable getDrawable(String source) {

        Drawable drawable = null;
        // 封装路径
        mPicName = source.hashCode() + "";
        File file = new File(Environment.getExternalStorageDirectory(), mPicName);
        // 判断是否以http开头
        if (source.startsWith("http")) {
            // 判断路径是否存在
            if (file.exists()) {
                // 存在即获取drawable
                drawable = Drawable.createFromPath(file.getAbsolutePath());
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            } else {
                // 不存在即开启异步任务加载网络图片
                AsyncLoadNetworkPic networkPic = new AsyncLoadNetworkPic();
                networkPic.execute(source);
            }
        }
        return drawable;
    }

    /**
     * 加载网络图片异步类
     *
     * @author Susie
     */
    private final class AsyncLoadNetworkPic extends AsyncTask<String, Integer, Void> {

        private static final String LOG_TAG = "AsyncLoadNetworkPic";

        @Override
        protected Void doInBackground(String... params) {
            // 加载网络图片
            loadNetPic(params);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // 当执行完成后再次为其设置一次
            mTextView.setText(Html.fromHtml(mContent, NetWorkImageGetter.this, null));
        }

        /**
         * 加载网络图片
         */
        private void loadNetPic(String... params) {
            String path = params[0];

            File file = new File(Environment.getExternalStorageDirectory(), mPicName);

            InputStream in = null;

            FileOutputStream out = null;

            try {
                URL url = new URL(path);

                HttpURLConnection connUrl = (HttpURLConnection) url.openConnection();

                connUrl.setConnectTimeout(5000);

                connUrl.setRequestMethod("GET");

                if (connUrl.getResponseCode() == 200) {

                    in = connUrl.getInputStream();

                    out = new FileOutputStream(file);

                    byte[] buffer = new byte[1024];

                    int len;

                    while ((len = in.read(buffer)) != -1) {
                        out.write(buffer, 0, len);
                    }
                } else {
                    LogUtils.i(LOG_TAG, connUrl.getResponseCode() + "");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {

                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
