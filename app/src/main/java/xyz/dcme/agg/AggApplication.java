package xyz.dcme.agg;

import android.app.Application;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.tencent.bugly.crashreport.CrashReport;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import xyz.dcme.agg.util.HttpConfig;


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
    }

    private void initBugly() {
        CrashReport.initCrashReport(getApplicationContext());
    }
}
