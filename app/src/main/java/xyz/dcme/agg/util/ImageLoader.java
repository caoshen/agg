package xyz.dcme.agg.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.BitmapTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelCache;
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

public class ImageLoader {
    private static final String TAG = LogUtils.makeLogTag("ImageLoader");

    private static final ModelCache<String, GlideUrl> urlCache = new ModelCache<>(150);
    private final BitmapTypeRequest<String> mGlideModelRequest;
    private final CenterCrop mCenterCrop;

    private int mPlaceHolderResId = -1;

    public ImageLoader(Context context) {
        VariableWidthImageLoader loader = new VariableWidthImageLoader(context);
        mGlideModelRequest = Glide.with(context).using(loader).from(String.class).asBitmap();
        mCenterCrop = new CenterCrop(Glide.get(context).getBitmapPool());
    }

    public ImageLoader(Context context, int placeHolderResId) {
        this(context);
        mPlaceHolderResId = placeHolderResId;
    }

    public static void loadImage(Context context, int drawableResId, ImageView imageView) {
        Glide.with(context).load(drawableResId).into(imageView);
    }

    private static class VariableWidthImageLoader extends BaseGlideUrlLoader<String> {
        public VariableWidthImageLoader(Context context) {
            super(context, urlCache);
        }

        @Override
        protected String getUrl(String model, int width, int height) {
            return null;
        }
    }
}
