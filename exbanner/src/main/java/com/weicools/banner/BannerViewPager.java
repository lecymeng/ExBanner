package com.weicools.banner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Interpolator;
import android.widget.Scroller;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import java.lang.reflect.Field;

/**
 * @author weicools
 * @date 2020.06.10
 */
class BannerViewPager extends ViewPager {
  private static class BannerScroller extends Scroller {
    private int scrollDuration = 1000;

    public BannerScroller(Context context) {
      super(context);
    }

    public BannerScroller(Context context, Interpolator interpolator) {
      super(context, interpolator);
    }

    public BannerScroller(Context context, Interpolator interpolator, boolean flywheel) {
      super(context, interpolator, flywheel);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
      super.startScroll(startX, startY, dx, dy, scrollDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
      super.startScroll(startX, startY, dx, dy, scrollDuration);
    }

    public void setDuration(int duration) {
      this.scrollDuration = duration;
    }
  }

  @Nullable
  private BannerScroller bannerScroller;

  private boolean disableTouchScroll;

  public BannerViewPager(@NonNull Context context) {
    super(context);
  }

  public BannerViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public void setDisableTouchScroll(boolean disableTouchScroll) {
    this.disableTouchScroll = disableTouchScroll;
  }

  public void setScrollDuration(int duration) {
    try {
      if (bannerScroller != null) {
        bannerScroller.setDuration(duration);
        return;
      }

      Field mField = ViewPager.class.getDeclaredField("mScroller");
      mField.setAccessible(true);
      bannerScroller = new BannerScroller(getContext());
      bannerScroller.setDuration(duration);
      mField.set(this, bannerScroller);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent ev) {
    try {
      if (disableTouchScroll) {
        return false;
      }
      return super.onInterceptTouchEvent(ev);
    } catch (Exception ignore) {
      return false;
    }
  }

  @SuppressLint("ClickableViewAccessibility")
  @Override
  public boolean onTouchEvent(MotionEvent ev) {
    if (disableTouchScroll) {
      return false;
    }
    return super.onTouchEvent(ev);
  }
}
