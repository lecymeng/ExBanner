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
  public interface ActionListener {
    void handleDown();

    void handleLeave();
  }

  private static class ExScroller extends Scroller {
    private int duration = 1000;

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
      super.startScroll(startX, startY, dx, dy, duration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
      super.startScroll(startX, startY, dx, dy, duration);
    }

    public void setDuration(int duration) {
      this.duration = duration;
    }
  }

  @Nullable
  private ExScroller exScroller;

  @Nullable
  private ActionListener actionListener;

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

      exScroller = new ExScroller(getContext());
      exScroller.setDuration(duration);
      Field mField = ViewPager.class.getDeclaredField("mScroller");
      mField.setAccessible(true);
      mField.set(this, exScroller);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void setActionListener(@Nullable ActionListener actionListener) {
    this.actionListener = actionListener;
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

  @Override
  @SuppressLint("ClickableViewAccessibility")
  public boolean onTouchEvent(MotionEvent ev) {
    if (disableTouchScroll) {
      return false;
    }

    switch (ev.getAction()) {
      case MotionEvent.ACTION_DOWN:
        if (actionListener != null) {
          actionListener.handleDown();
        }
        break;
      case MotionEvent.ACTION_UP:
      case MotionEvent.ACTION_CANCEL:
      case MotionEvent.ACTION_OUTSIDE:
        if (actionListener != null) {
          actionListener.handleLeave();
        }
        break;
      default:
        break;
    }
    return super.onTouchEvent(ev);
  }
}
