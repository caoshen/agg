package xyz.dcme.agg.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.BitmapTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelCache;
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public void loadImage(String url, ImageView imageView) {
        loadImage(url, imageView, false);
    }

    public void loadImage(String url, ImageView imageView, boolean crop) {
        loadImage(url, imageView, null, null, crop);
    }

    public void loadImage(String url, ImageView imageView, RequestListener<String, Bitmap> requestListener) {
        loadImage(url, imageView, requestListener, null);
    }

    public void loadImage(String url, ImageView imageView, RequestListener<String, Bitmap> requestListener,
                          Drawable placeholderOverride) {
        loadImage(url, imageView, requestListener, placeholderOverride, false);
    }

    public void loadImage(String url, ImageView imageView, RequestListener<String, Bitmap> requestListener,
                          Drawable placeholderOverride, boolean crop) {
        BitmapRequestBuilder requestBuilder = beginImageLoad(url, requestListener, crop);
        if (placeholderOverride != null) {
            requestBuilder.placeholder(placeholderOverride);
        } else if (mPlaceHolderResId != -1){
            requestBuilder.placeholder(mPlaceHolderResId);
        }
        requestBuilder.into(imageView);
    }

    public static void loadImage(Context context, int drawableResId, ImageView imageView) {
        Glide.with(context).load(drawableResId).into(imageView);
    }

    private static class VariableWidthImageLoader extends BaseGlideUrlLoader<String> {
        private static final Pattern PATTERN = Pattern.compile("__w-((?:-?\\\\d+)+)__");

        public VariableWidthImageLoader(Context context) {
            super(context, urlCache);
        }

        @Override
        protected String getUrl(String model, int width, int height) {
            Matcher matcher = PATTERN.matcher(model);
            int bestBucket = 0;
            if (matcher.find()) {
                String[] found = matcher.group(1).split("-");
                for (String bucket : found) {
                    bestBucket = Integer.parseInt(bucket);
                    if (bestBucket >= width) {
                        break;
                    }
                }
                if (bestBucket > 0) {
                    model = matcher.replaceFirst("w" + bestBucket);
                }
            }
            return model;
        }
    }

    public BitmapRequestBuilder beginImageLoad(String url, RequestListener<String, Bitmap> requestListener,
                                               boolean crop) {
        if (crop) {
            return mGlideModelRequest.load(url).listener(requestListener).transform(mCenterCrop);
        } else {
            return mGlideModelRequest.load(url).listener(requestListener);
        }
    }
}
