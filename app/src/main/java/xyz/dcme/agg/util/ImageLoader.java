package xyz.dcme.agg.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ImageLoader {

    public static void loadImage(Context context, int drawableResId, ImageView imageView) {
        Glide.with(context).load(drawableResId).into(imageView);
    }
}
