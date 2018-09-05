package com.leixing.bitmaploader;

import android.graphics.Bitmap;

/**
 * description : xxx
 *
 * @author : leixing
 * email : leixing@baidu.com
 * @date : 2018/9/5 10:46
 */
public interface BitmapLoader<K> {
    Bitmap loadBitmap(K k);
}
