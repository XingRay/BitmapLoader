package com.leixing.bitmaploader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.bt_frame_animation)
    public void onBtFrameAnimationClicked() {
        BitmapLoadTestActivity.start(this, BitmapLoadTestActivity.LOAD_METHOD_FRAME_ANIMATION);
    }

    @OnClick(R.id.bt_set)
    public void onBtSetClicked() {
        BitmapLoadTestActivity.start(this, BitmapLoadTestActivity.LOAD_METHOD_SET_BY_DELAY);
    }

    @OnClick(R.id.bt_cache)
    public void onBtCacheClicked() {
        BitmapLoadTestActivity.start(this, BitmapLoadTestActivity.LOAD_METHOD_CACHE);
    }

    @OnClick(R.id.bt_reuse)
    public void onBtReuseClicked() {
        BitmapLoadTestActivity.start(this, BitmapLoadTestActivity.LOAD_METHOD_REUSE);
    }

    @OnClick(R.id.bt_combine)
    public void onBtCombineClicked() {
        BitmapLoadTestActivity.start(this, BitmapLoadTestActivity.LOAD_METHOD_CACHE_AND_REUSE);
    }
}
