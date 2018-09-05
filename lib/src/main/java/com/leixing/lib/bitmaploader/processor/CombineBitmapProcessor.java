package com.leixing.lib.bitmaploader.processor;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;


import com.leixing.lib.bitmaploader.BitmapProcessor;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * description : xxx
 *
 * @author : leixing
 * email : leixing@baidu.com
 * @date : 2018/9/5 11:05
 */
public class CombineBitmapProcessor implements BitmapProcessor {
    private LinkedList<Bitmap> mCombineBitmaps;
    private LinkedList<Rect> mSrcRects;
    private LinkedList<Rect> mDstRects;

    @Override
    public Bitmap process(Bitmap bitmap) {
        Canvas canvas = new Canvas(bitmap);

        if (mCombineBitmaps == null
                || mSrcRects == null
                || mDstRects == null) {
            return bitmap;
        }

        Iterator<Bitmap> bitmapIterator = mCombineBitmaps.iterator();
        Iterator<Rect> srcIterator = mSrcRects.iterator();
        Iterator<Rect> dstIterator = mDstRects.iterator();
        while (bitmapIterator.hasNext() &&
                srcIterator.hasNext() &&
                dstIterator.hasNext()) {
            Bitmap combineBitmap = bitmapIterator.next();
            Rect src = srcIterator.next();
            Rect dst = dstIterator.next();
            canvas.drawBitmap(combineBitmap, src, dst, null);
        }
        return bitmap;
    }


    public CombineBitmapProcessor addCombination(Bitmap bitmap, Rect src, Rect dst) {
        if (mCombineBitmaps == null) {
            mCombineBitmaps = new LinkedList<>();
        }
        mCombineBitmaps.add(bitmap);

        if (mSrcRects == null) {
            mSrcRects = new LinkedList<>();
        }
        mSrcRects.add(src);

        if (mDstRects == null) {
            mDstRects = new LinkedList<>();
        }
        mDstRects.add(dst);

        return this;
    }
}
