package xyz.dcme.agg.ui;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PostPresenter implements PostContract.Presenter {
    private static final String TAG = "PostPresenter";
    private PostContract.View mView;

    public PostPresenter(PostContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        new AsyncTask<Void, Void, List<String>>() {

            @Override
            protected List<String> doInBackground(Void... voids) {
                Document doc = null;
                List<String> data = new ArrayList<String>();
                try {
                    doc = Jsoup.connect("http://www.guanggoo.com/?p=1").get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Elements newsHeadlines = doc.select("h3.title");
                for (Element element : newsHeadlines) {
                    String text = element.text();
                    data.add(text);
                    Log.d(TAG, text);
                }
                return data;
            }

            @Override
            protected void onPostExecute(List<String> data) {
                super.onPostExecute(data);
                mView.refreshPosts(data);
                Log.d(TAG, "" + data.size());
            }
        }.execute();

    }
}
