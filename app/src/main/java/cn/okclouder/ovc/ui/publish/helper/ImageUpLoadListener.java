package cn.okclouder.ovc.ui.publish.helper;

public interface ImageUpLoadListener {
    void onError(String err);

    void onResponse(String imageName, String imageUrl);
}
