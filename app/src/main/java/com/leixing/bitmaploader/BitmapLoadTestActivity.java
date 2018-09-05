package com.leixing.bitmaploader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.leixing.lib.bitmaploader.BitmapLoader;
import com.leixing.lib.bitmaploader.loader.CacheBitmapLoader;
import com.leixing.lib.bitmaploader.loader.ReuseBitmapLoader;
import com.leixing.lib.bitmaploader.processor.CombineBitmapProcessor;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * description : xxx
 *
 * @author : leixing
 * email : leixing@baidu.com
 * @date : 2018/9/5 17:13
 */
public class BitmapLoadTestActivity extends Activity {

    public static final String PARAM_LOAD_METHOD = "load_method";

    public static final int LOAD_METHOD_FRAME_ANIMATION = 0;
    public static final int LOAD_METHOD_SET_BY_DELAY = 1;
    public static final int LOAD_METHOD_CACHE = 2;
    public static final int LOAD_METHOD_REUSE = 3;
    public static final int LOAD_METHOD_CACHE_AND_REUSE = 4;

    @BindView(R.id.iv_img)
    ImageView ivImg;
    private int index;
    private int[] imgResIds;
    private Runnable mTask;
    private Context mContext;
    private AnimationDrawable mAnimationDrawable;

    public static void start(Context context, int loadMethod) {
        Intent intent = new Intent(context, BitmapLoadTestActivity.class);
        intent.putExtra(PARAM_LOAD_METHOD, loadMethod);
        if (!(context instanceof Activity)) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();

        Intent intent = getIntent();
        int loadMethod = intent.getIntExtra(PARAM_LOAD_METHOD, LOAD_METHOD_FRAME_ANIMATION);

        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);


        imgResIds = ResUtil.getArray(R.array.animation_bg);
        index = 0;

        switch (loadMethod) {
            case LOAD_METHOD_FRAME_ANIMATION:
                loadFrameAnimation();
                break;
            case LOAD_METHOD_SET_BY_DELAY:
                setByDelay();
                break;
            case LOAD_METHOD_CACHE:
                cache();
                break;
            case LOAD_METHOD_REUSE:
                reuse();
                break;
            case LOAD_METHOD_CACHE_AND_REUSE:
                cacheAndReuse();
                break;
            default:
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTask = null;
        if (mAnimationDrawable != null) {
            mAnimationDrawable.stop();
        }
    }

    private void loadFrameAnimation() {
        ivImg.setImageResource(R.mipmap.album);
        ivImg.setBackgroundResource(R.drawable.animation_play);
        mAnimationDrawable = (AnimationDrawable) ivImg.getBackground();
        mAnimationDrawable.start();
    }

    private void setByDelay() {
        ivImg.setImageResource(R.mipmap.album);
        mTask = new Runnable() {
            @Override
            public void run() {
                int resId = imgResIds[index];
                ivImg.setBackgroundResource(resId);
                index++;
                if (index == imgResIds.length) {
                    index = 0;
                }
                if (mTask != null) {
                    ivImg.postDelayed(mTask, 150);
                }
            }
        };
        ivImg.postDelayed(mTask, 150);
    }

    private void cache() {
        ivImg.setPadding(0, 0, 0, 0);
        Bitmap iconBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.album);

        final int width = (int) ViewUtil.dp2px(mContext, 250);
        final int height = (int) ViewUtil.dp2px(mContext, 250);
        final int padding = (int) ViewUtil.dp2px(mContext, 40);

        int left = padding;
        int top = padding;
        int right = width - padding;
        int bottom = height - padding;
        Rect dst = new Rect(left, top, right, bottom);

        CombineBitmapProcessor processor = new CombineBitmapProcessor()
                .addCombination(iconBitmap, null, dst);

        final BitmapLoader<Integer> loader = new CacheBitmapLoader(mContext, width, height)
                .addBitmapProcessor(processor);

        mTask = new Runnable() {
            @Override
            public void run() {
                int resId = imgResIds[index];
                ivImg.setImageBitmap(loader.loadBitmap(resId));

                index++;
                if (index == imgResIds.length) {
                    index = 0;
                }

                if (mTask != null) {
                    ivImg.postDelayed(mTask, 150);
                }
            }
        };
        ivImg.postDelayed(mTask, 150);
    }

    private void reuse() {
        ivImg.setPadding(0, 0, 0, 0);
        Bitmap iconBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.album);

        final int width = (int) ViewUtil.dp2px(mContext, 250);
        final int height = (int) ViewUtil.dp2px(mContext, 250);
        final int padding = (int) ViewUtil.dp2px(mContext, 40);

        int left = padding;
        int top = padding;
        int right = width - padding;
        int bottom = height - padding;
        Rect dst = new Rect(left, top, right, bottom);

        CombineBitmapProcessor processor = new CombineBitmapProcessor()
                .addCombination(iconBitmap, null, dst);

        final BitmapLoader<Integer> loader = new ReuseBitmapLoader(mContext, width, height)
                .addBitmapProcessor(processor);

        mTask = new Runnable() {
            @Override
            public void run() {
                int resId = imgResIds[index];
                ivImg.setImageBitmap(loader.loadBitmap(resId));

                index++;
                if (index == imgResIds.length) {
                    index = 0;
                }

                if (mTask != null) {
                    ivImg.postDelayed(mTask, 150);
                }
            }
        };
        ivImg.postDelayed(mTask, 150);
    }

    private void cacheAndReuse() {
        ivImg.setPadding(0, 0, 0, 0);
        Bitmap iconBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.album);

        final int width = (int) ViewUtil.dp2px(mContext, 250);
        final int height = (int) ViewUtil.dp2px(mContext, 250);
        final int padding = (int) ViewUtil.dp2px(mContext, 40);

        int left = padding;
        int top = padding;
        int right = width - padding;
        int bottom = height - padding;
        Rect dst = new Rect(left, top, right, bottom);

        CombineBitmapProcessor processor = new CombineBitmapProcessor()
                .addCombination(iconBitmap, null, dst);

        final BitmapLoader<Integer> cacheLoader = new CacheBitmapLoader(mContext, width, height)
                .addBitmapProcessor(processor);
        final BitmapLoader<Integer> reuseLoader = new ReuseBitmapLoader(mContext, width, height)
                .addBitmapProcessor(processor);

        mTask = new Runnable() {
            @Override
            public void run() {
                int resId = imgResIds[index];
                Bitmap bitmap;
                if ((index & 1) == 0) {
                    bitmap = cacheLoader.loadBitmap(resId);
                } else {
                    bitmap = reuseLoader.loadBitmap(resId);
                }
                ivImg.setImageBitmap(bitmap);

                index++;
                if (index == imgResIds.length) {
                    index = 0;
                }

                if (mTask != null) {
                    ivImg.postDelayed(mTask, 150);
                }
            }
        };
        ivImg.postDelayed(mTask, 150);
    }
}
