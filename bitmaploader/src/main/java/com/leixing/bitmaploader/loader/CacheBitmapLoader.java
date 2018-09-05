package com.leixing.bitmaploader.loader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.SparseArray;


import com.leixing.bitmaploader.BitmapLoader;
import com.leixing.bitmaploader.BitmapProcessor;

import java.util.LinkedList;

/**
 * description : xxx
 *
 * @author : leixing
 * email : leixing@baidu.com
 * @date : 2018/9/5 10:44
 */
public class CacheBitmapLoader implements BitmapLoader<Integer> {
    private SparseArray<Bitmap> mDrawableCache;
    private BitmapFactory.Options mBitmapOptions;
    private final Context mContext;
    private LinkedList<BitmapProcessor> mProcessors;
    private final int mWidth;
    private final int mHeight;
    private final Rect mDstRect;
    private Canvas canvas;

    public CacheBitmapLoader(Context context, int width, int height) {
        mDrawableCache = new SparseArray<>();

        mBitmapOptions = new BitmapFactory.Options();
        mBitmapOptions.inMutable = true;
        mBitmapOptions.outWidth = width;
        mBitmapOptions.outHeight = height;
        mBitmapOptions.inSampleSize = 2;
        mBitmapOptions.inBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

        mContext = context;
        mWidth = width;
        mHeight = height;

        mDstRect = new Rect(0, 0, mWidth, mHeight);
        canvas = new Canvas();
    }

    @Override
    public Bitmap loadBitmap(Integer k) {
        int resId = k == null ? 0 : k;
        Bitmap resultBitmap = mDrawableCache.get(resId);
        if (resultBitmap != null) {
            return resultBitmap;
        }

        Bitmap resBitmap = BitmapFactory.decodeResource(
                mContext.getResources(),
                resId,
                mBitmapOptions);

        resultBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        canvas.setBitmap(resultBitmap);
        canvas.drawBitmap(resBitmap, null, mDstRect, null);

        if (mProcessors != null) {
            for (BitmapProcessor processor : mProcessors) {
                resultBitmap = processor.process(resultBitmap);
            }
        }

        mDrawableCache.put(resId, resultBitmap);
        return resultBitmap;
    }


    public CacheBitmapLoader addBitmapProcessor(BitmapProcessor processor) {
        if (mProcessors == null) {
            mProcessors = new LinkedList<>();
        }
        mProcessors.add(processor);
        return this;
    }
}
