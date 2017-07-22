package xyz.dcme.agg.ui.publish.helper;

public interface ImageUpLoadListener {
    void onError(String err);

    void onResponse(String imageName, String imageUrl);
}
