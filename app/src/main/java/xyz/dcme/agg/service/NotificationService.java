package xyz.dcme.agg.service;


import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;
import xyz.dcme.agg.R;
import xyz.dcme.agg.ui.notify.Message;
import xyz.dcme.agg.ui.notify.MessageParser;
import xyz.dcme.agg.util.Constants;
import xyz.dcme.agg.util.HttpUtils;
import xyz.dcme.agg.util.NotificationFactory;

public class NotificationService extends IntentService {
    private static final String LOG_TAG = "NotificationService";
    private static final int MAX_NOTIFY = 3;

    public NotificationService() {
        super(LOG_TAG);
    }

    private void sendNotification(String title, String content, String url) {
        NotificationFactory.sendNotification(this, title, content, R.mipmap.ic_launcher, url);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        HttpUtils.get(Constants.NOTIFICATION_URL, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(String response, int id) {
                List<Message> messages = MessageParser.parseResponse(response);
                if (null != messages && !messages.isEmpty()) {
                    int count = messages.size() > MAX_NOTIFY ? MAX_NOTIFY : messages.size();
                    for (int i = count - 1; i >= 0; --i) {
                        Message m = messages.get(i);
                        sendNotification(m.getTitle(), m.getContent(), m.getLink());
                    }
                }
            }
        });
    }
}
