package cn.okclouder.ovc.util;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import cn.okclouder.ovc.ui.login.LoginActivity;

public class LoginUtils {

    public static boolean needLogin(String response) {
        Document doc = Jsoup.parse(response);
        Elements contents = doc.select("input[type=password]");
        return contents != null && !contents.isEmpty();
    }

    public static void startLogin(Activity activity, int requestCode) {
        if (activity == null) {
            return;
        }
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void startLogin(Fragment fragment, int requestCode) {
        if (fragment == null) {
            return;
        }
        Intent intent = new Intent(fragment.getActivity(), LoginActivity.class);
        fragment.startActivityForResult(intent, requestCode);
    }

    public static boolean checkLogin(Fragment fragment, int requestCode) {
        if (fragment == null) {
            return false;
        }
        if (!AccountUtils.hasActiveAccount(fragment.getContext())) {
            startLogin(fragment, requestCode);
            return false;
        }
        return true;
    }
}
