package com.leixing.lib.bitmaploader.loader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;


import com.leixing.lib.bitmaploader.BitmapLoader;
import com.leixing.lib.bitmaploader.BitmapProcessor;

import java.util.LinkedList;

/**
 * description : xxx
 *
 * @author : leixing
 * email : leixing@baidu.com
 * @date : 2018/9/5 10:44
 */
public class ReuseBitmapLoader implements BitmapLoader<Integer> {
    private BitmapFactory.Options mBitmapOptions;
    private LinkedList<BitmapProcessor> mProcessors;
    private final Context mContext;
    private Bitmap mReuseBitmap;
    private Rect mDstRect;
    private Canvas mCanvas;

    public ReuseBitmapLoader(Context context, int width, int height) {
        mContext = context;

        mBitmapOptions = new BitmapFactory.Options();
        mBitmapOptions.inMutable = true;
        mBitmapOptions.outWidth = width;
        mBitmapOptions.outHeight = height;
        mBitmapOptions.inSampleSize = 2;
        mBitmapOptions.inBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

        mReuseBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mDstRect = new Rect(0, 0, width, height);
        mCanvas = new Canvas();
    }

    @Override
    public Bitmap loadBitmap(Integer integer) {
        int resId = integer == null ? 0 : integer;
        Bitmap resBitmap = BitmapFactory.decodeResource(mContext.getResources(), resId, mBitmapOptions);
        mReuseBitmap.eraseColor(0);

        mCanvas.setBitmap(mReuseBitmap);
        mCanvas.drawBitmap(resBitmap, null, mDstRect, null);

        if (mProcessors != null) {
            for (BitmapProcessor processor : mProcessors) {
                mReuseBitmap = processor.process(mReuseBitmap);
            }
        }

        return mReuseBitmap;
    }

    public ReuseBitmapLoader addBitmapProcessor(BitmapProcessor processor) {
        if (mProcessors == null) {
            mProcessors = new LinkedList<>();
        }
        mProcessors.add(processor);
        return this;
    }
}
