package cn.okclouder.ovc.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.UUID;

import cn.okclouder.ovc.ui.postdetail.PostDetailActivity;

public class NotificationFactory {

    public static void sendNotification(Context context, String title, String content, int iconResId, String url) {
        PendingIntent pi = buildPendingIntent(context, url);
        Notification notification = new Notification.Builder(context)
                .setSmallIcon(iconResId)
                .setContentTitle(title)
                .setContentText(content)
                .setContentIntent(pi)
                .setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .build();
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        int id = UUID.randomUUID().hashCode();
        manager.notify(id, notification);
    }

    private static PendingIntent buildPendingIntent(Context context, String url) {
        Intent intent = new Intent(context, PostDetailActivity.class);
        intent.putExtra(PostDetailActivity.KEY_POST_DETAIL_URL, url);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pi;
    }
}
