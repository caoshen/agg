package xyz.dcme.agg;

import android.app.Application;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.tencent.bugly.Bugly;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import xyz.dcme.agg.util.HttpConfig;
import xyz.dcme.library.loading.LoadingManager;


public class AggApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ClearableCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(),
                new SharedPrefsCookiePersistor(getApplicationContext()));
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(HttpConfig.CONN_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(HttpConfig.READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .cookieJar(cookieJar)
                .build();
        OkHttpUtils.initClient(client);

        initBugly();
        initBaseLayout();
    }

    private void initBaseLayout() {
        LoadingManager.NO_EMPTY_ID = R.layout.base_empty;
    }

    private void initBugly() {
        Bugly.init(getApplicationContext(), "2d0424a759", false);
    }
}
