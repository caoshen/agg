package xyz.dcme.agg;

import android.app.Application;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;

import java.util.concurrent.TimeUnit;

import okhttp3.CookieJar;
import okhttp3.OkHttpClient;
import xyz.dcme.agg.util.HttpConfig;


public class MyApplication extends Application {



    @Override
    public void onCreate() {
        super.onCreate();

        CookieJar cookieJar = new CookieJarImpl(new PersistentCookieStore(getApplicationContext()));
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(HttpConfig.CONN_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(HttpConfig.READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .cookieJar(cookieJar)
                .build();
        OkHttpUtils.initClient(client);
    }
}
