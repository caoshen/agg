package xyz.dcme.agg.http;

import android.content.Context;
import android.content.SharedPreferences;

import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PersistentCookieStore implements CookieStore {

    private static final String LOG_TAG = "PersistentCookieStore";
    private static final String PREF_COOKIE = "cookies";
    private HashMap<String, ConcurrentHashMap<String, HttpCookie>> mCookies;
    private SharedPreferences mPrefs;

    public PersistentCookieStore(Context context) {
        mPrefs = context.getSharedPreferences(PREF_COOKIE, Context.MODE_PRIVATE);
        mCookies = new HashMap<String, ConcurrentHashMap<String, HttpCookie>>();

        Map<String, ?> allPrefs = mPrefs.getAll();
        for (Map.Entry<String, ?> pref : allPrefs.entrySet()) {

        }
    }

    @Override
    public void add(URI uri, HttpCookie cookie) {

    }

    @Override
    public List<HttpCookie> get(URI uri) {
        return null;
    }

    @Override
    public List<HttpCookie> getCookies() {
        return null;
    }

    @Override
    public List<URI> getURIs() {
        return null;
    }

    @Override
    public boolean remove(URI uri, HttpCookie cookie) {
        return false;
    }

    @Override
    public boolean removeAll() {
        return false;
    }
}
