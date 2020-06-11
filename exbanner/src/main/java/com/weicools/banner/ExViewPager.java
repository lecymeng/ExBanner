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
class ExViewPager extends ViewPager {
  private static class ExScroller extends Scroller {
    private int scrollDuration = 1000;

    public ExScroller(Context context) {
      super(context);
    }

    public ExScroller(Context context, Interpolator interpolator) {
      super(context, interpolator);
    }

    public ExScroller(Context context, Interpolator interpolator, boolean flywheel) {
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
  private ExScroller exScroller;

  private boolean disableTouchScroll;

  public ExViewPager(@NonNull Context context) {
    super(context);
  }

  public ExViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public void setDisableTouchScroll(boolean disableTouchScroll) {
    this.disableTouchScroll = disableTouchScroll;
  }

  public void setScrollDuration(int duration) {
    try {
      if (exScroller != null) {
        exScroller.setDuration(duration);
        return;
      }

      Field mField = ViewPager.class.getDeclaredField("mScroller");
      mField.setAccessible(true);
      exScroller = new ExScroller(getContext());
      exScroller.setDuration(duration);
      mField.set(this, exScroller);
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
