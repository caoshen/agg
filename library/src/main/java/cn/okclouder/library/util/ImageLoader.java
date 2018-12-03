package cn.okclouder.library.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;

import cn.okclouder.library.R;

public class ImageLoader {
    private static final String LOG_TAG = "ImageLoader";

    private ImageLoader() {

    }

    public static void display(Context context, ImageView imageView, String url) {
        Glide.with(context).load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .placeholder(R.drawable.ic_image_loading)
                .error(R.drawable.ic_empty_picture)
                .crossFade()
                .into(imageView);
    }

    public static void display(Context context, ImageView imageView, String url,
                               int placeholder, int error) {
        Glide.with(context).load(url).placeholder(placeholder)
            .error(error).crossFade().into(imageView);
    }

    public static void display(Context context, ImageView imageView, File file) {
        Glide.with(context).load(file)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .placeholder(R.drawable.ic_image_loading)
                .error(R.drawable.ic_empty_picture)
                .crossFade()
                .into(imageView);
    }

    public static void display(Context context, ImageView imageView, int resId) {
        Glide.with(context).load(resId)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .placeholder(R.drawable.ic_image_loading)
                .error(R.drawable.ic_empty_picture)
                .crossFade()
                .into(imageView);
    }

    public static void displayThumb(Context context, ImageView imageView, String url) {
        Glide.with(context).load(url).asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_image_loading)
                .error(R.drawable.ic_empty_picture)
                .thumbnail(0.5F)
                .into(imageView);
    }

    public static void displayLarge(Context context, ImageView imageView, String url) {
        Glide.with(context).load(url).asBitmap()
                .format(DecodeFormat.PREFER_ARGB_8888)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_image_loading)
                .error(R.drawable.ic_empty_picture)
                .into(imageView);
    }

    public static void displayCircle(Context context, ImageView imageView, String url) {
        if (context == null) {
            return;
        }
        if (imageView == null) {
            return;
        }
        if (url == null) {
            return;
        }
        Glide.with(context).load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.ic_empty_picture)
                .centerCrop()
                .transform(new CircleTransformation(context))
                .into(imageView);
    }

    public static void displayCircle(Context context, ImageView imageView, int resId) {
        Glide.with(context).load(resId)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.ic_empty_avatar)
                .centerCrop()
                .transform(new CircleTransformation(context))
                .into(imageView);
    }
}
