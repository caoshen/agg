package xyz.dcme.agg.ui.favorite;

import android.os.AsyncTask;
import android.text.TextUtils;

import java.util.List;

import xyz.dcme.agg.model.Post;
import xyz.dcme.agg.util.Constants;

public class FavoritePresenter implements FavoriteContract.Presenter {

    private FavoriteContract.View mView;

    public FavoritePresenter(FavoriteContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void load(String name) {
        mView.setLoadingIndicator(true);
        new AsyncTask<String, Void, List<Post>>() {
            @Override
            protected List<Post> doInBackground(String... names) {
                if (TextUtils.isEmpty(names[0])) {
                    return null;
                }
                return FavoriteParser.parseList(Constants.USER_PROFILE_URL + names[0]
                        + "/favorites");
            }

            @Override
            protected void onPostExecute(List<Post> posts) {
                super.onPostExecute(posts);
                if (posts != null && !posts.isEmpty()) {
                    mView.showFav(posts);
                }
                mView.setLoadingIndicator(false);
            }
        }.execute(name);
    }
}
