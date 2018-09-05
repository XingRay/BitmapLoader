# BitmapLoader
Bitmap loader for android

# How to
To get a Git project into your build:

## Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

```groovy
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

## Step 2. Add the dependency
```groovy
dependencies {
	implementation 'com.github.XingRay:BitmapLoader:0.0.4'
}
```

# sample

see BitmapLoadTestActivity in project

```java

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
```
