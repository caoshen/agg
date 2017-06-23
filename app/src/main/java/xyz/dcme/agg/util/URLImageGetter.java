package xyz.dcme.agg.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.widget.TextView;

import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;

import java.io.File;

public class URLImageGetter implements Html.ImageGetter {
    private final TextView mTextView;
    private final int mPicWidth;
    private final String mContent;
    private final int mImageCount;
    private final Context mContext;
    private final String mFilePath;
    private int mCurImage;

    public URLImageGetter(TextView textView, String content, int imageCount) {
        mTextView = textView;
        mContext = textView.getContext();
        mPicWidth = textView.getWidth();
        mContent = content;
        mImageCount = imageCount;
        mFilePath = mContext.getCacheDir().getAbsolutePath();
    }

    @Override
    public Drawable getDrawable(String source) {
        Drawable drawable;
        File file = new File(mFilePath, source.hashCode() + "");
        if (file.exists()) {
            mCurImage++;
            drawable = getDrawableFromDisk(file);
        } else {
            drawable = getDrawableFromNet(source);
        }
        return drawable;
    }

    private Drawable getDrawableFromNet(String url) {
        final UrlDrawable urlDrawable = new UrlDrawable();
        final GenericRequestBuilder load;
        final Target target;
        if (isGif(url)) {
            load = Glide.with(mContext).load(url).asGif();
            target = new SimpleTarget<GifDrawable>() {

                @Override
                public void onResourceReady(GifDrawable resource, GlideAnimation<? super GifDrawable> glideAnimation) {
                    int w = mPicWidth;
                    int hh = resource.getIntrinsicHeight();
                    int ww = resource.getIntrinsicWidth();
                    int high = hh * (w - 50) / ww;
                    Rect rect = new Rect(20, 20, w - 30, high);
                    resource.setBounds(rect);
                    urlDrawable.setBounds(rect);
                    urlDrawable.setDrawable(resource);
                    resource.setCallback(mTextView);
                    resource.start();
                    resource.setLoopCount(GlideDrawable.LOOP_FOREVER);
                    mTextView.setText(mTextView.getText());
                    mTextView.invalidate();
                }
            };
        } else {
            load = Glide.with(mContext).load(url).asBitmap();
            target = new SimpleTarget<Bitmap>() {

                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    Drawable drawable = new BitmapDrawable(mContext.getResources(), resource);

                    int w = mPicWidth;
                    int hh = drawable.getIntrinsicHeight();
                    int ww = drawable.getIntrinsicWidth();
                    int high = hh * (w - 50) / ww;
                    Rect rect = new Rect(20, 20, w - 30, high);
                    drawable.setBounds(rect);
                    urlDrawable.setBounds(rect);
                    urlDrawable.setDrawable(drawable);
                    mTextView.setText(mTextView.getText());
                    mTextView.invalidate();
                }
            };
        }
        load.into(target);
        return urlDrawable;
    }

    private boolean isGif(String url) {
        return url.toUpperCase().endsWith(".GIF");
    }

    private Drawable getDrawableFromDisk(File file) {
        Drawable drawable = Drawable.createFromPath(file.getAbsolutePath());
        setDrawableBounds(drawable);
        return drawable;
    }

    private void setDrawableBounds(Drawable drawable) {
        if (drawable != null) {
            int height = getHeight(drawable);
            drawable.setBounds(0, 0, mPicWidth, height);
        }
    }

    private int getHeight(Drawable drawable) {
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        float ratio = h * 1.0F / w;
        return (int) (ratio * mPicWidth);
    }
}
