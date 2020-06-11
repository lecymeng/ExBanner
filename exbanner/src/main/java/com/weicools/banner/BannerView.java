package com.weicools.banner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import java.util.List;

/**
 * @author weicools
 * @date 2020.06.08
 */
public class BannerView extends FrameLayout {

  //on onDetachedFromWindow remove all callback msg
  @SuppressLint("HandlerLeak")
  private class BannerHandler extends Handler {
    @Override
    public void handleMessage(@NonNull Message msg) {
      if (msg.what == MSG_BANNER_PLAY) {
        if (pagerAdapter.getCount() < 2) {
          stopBannerPlay();
          return;
        }
        int item = viewPager.getCurrentItem();
        viewPager.setCurrentItem(++item, true);
        bannerHandler.sendEmptyMessageDelayed(MSG_BANNER_PLAY, playIntervalMills);
      }
    }
  }

  private static final int MSG_BANNER_PLAY = 1;

  private long playIntervalMills = 3000L;

  private BannerViewPager viewPager;
  private BannerPagerAdapter pagerAdapter;
  private BannerHandler bannerHandler = new BannerHandler();

  //<editor-fold desc="Constructor">
  public BannerView(@NonNull Context context) {
    super(context);
    init(context);
  }

  public BannerView(@NonNull Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    init(context);
  }

  public BannerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context);
  }
  //</editor-fold>

  private void init(Context context) {
    viewPager = new BannerViewPager(context);
    addView(viewPager, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

    viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
      @Override
      public void onPageSelected(int position) {
        super.onPageSelected(position);
      }

      @Override
      public void onPageScrollStateChanged(int state) {
        if (state == ViewPager.SCROLL_STATE_IDLE || state == ViewPager.SCROLL_STATE_DRAGGING) {
          if (viewPager.getCurrentItem() == pagerAdapter.getCount() - 1) {
            viewPager.setCurrentItem(1, false);
          } else if (viewPager.getCurrentItem() == 0) {
            viewPager.setCurrentItem(pagerAdapter.getCount() - 2, false);
          }
        }
      }
    });

    pagerAdapter = new BannerPagerAdapter(context);
    viewPager.setAdapter(pagerAdapter);

    viewPager.setScrollDuration(1000);
  }

  public void updateDataList(@NonNull List<? extends FlexibleBannerItem> itemList) {
    pagerAdapter.updateData(itemList);
    startBannerPlay();
  }

  public void startBannerPlay() {
    stopBannerPlay();
    if (pagerAdapter.getCount() < 2) {
      return;
    }
    bannerHandler.sendEmptyMessageDelayed(MSG_BANNER_PLAY, playIntervalMills);
  }

  public void stopBannerPlay() {
    if (bannerHandler.hasMessages(MSG_BANNER_PLAY)) {
      bannerHandler.removeMessages(MSG_BANNER_PLAY);
    }
  }

  @Override
  protected void onDetachedFromWindow() {
    stopBannerPlay();
    bannerHandler.removeCallbacksAndMessages(null);
    super.onDetachedFromWindow();
  }

  @Override
  public boolean dispatchTouchEvent(MotionEvent ev) {
    switch (ev.getAction()) {
      case MotionEvent.ACTION_DOWN:
        stopBannerPlay();
        break;
      case MotionEvent.ACTION_UP:
      case MotionEvent.ACTION_CANCEL:
      case MotionEvent.ACTION_OUTSIDE:
        startBannerPlay();
        break;
      default:
        break;
    }
    return super.dispatchTouchEvent(ev);
  }
}
