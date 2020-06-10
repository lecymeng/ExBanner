package com.weicools.banner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import java.util.List;

/**
 * @author weicools
 * @date 2020.06.08
 */
@SuppressWarnings("rawtypes")
public class BannerView<I extends BannerFlexibleItem<BannerViewHolder>> extends FrameLayout {

  //on onDetachedFromWindow remove all callback msg
  @SuppressLint("HandlerLeak")
  private class BannerHandler extends Handler {
    @Override
    public void handleMessage(@NonNull Message msg) {
      if (msg.what == MSG_BANNER_LOOP) {
        if (pagerAdapter == null || pagerAdapter.getCount() < 2) {
          stopBannerPlay();
          return;
        }
        int item = viewPager.getCurrentItem();
        viewPager.setCurrentItem(++item, true);
        bannerHandler.sendEmptyMessageDelayed(MSG_BANNER_LOOP, playIntervalTimeMills);
      }
    }
  }

  private static final int MSG_BANNER_LOOP = 1;

  private long playIntervalTimeMills = 3000L;

  private ExViewPager viewPager;
  private BannerPagerAdapter<I> pagerAdapter;

  private BannerHandler bannerHandler = new BannerHandler();

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

  private void init(Context context) {
    viewPager = new ExViewPager(context);
    addView(viewPager, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

    viewPager.setActionListener(new ExViewPager.ActionListener() {
      @Override public void handleDown() {
        stopBannerPlay();
      }

      @Override public void handleLeave() {
        startBannerPlay();
      }
    });

    viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
      @Override public void onPageSelected(int position) {
        super.onPageSelected(position);
      }

      @Override public void onPageScrollStateChanged(int state) {
        super.onPageScrollStateChanged(state);
      }
    });

    pagerAdapter = new BannerPagerAdapter<>(context);
    viewPager.setAdapter(pagerAdapter);
  }

  private void updateDataList(List<I> itemList) {
    pagerAdapter.updateData(itemList);
    startBannerPlay();
  }

  public void startBannerPlay() {
    stopBannerPlay();
    if (pagerAdapter == null || pagerAdapter.getCount() < 2) {
      return;
    }
    bannerHandler.sendEmptyMessageDelayed(MSG_BANNER_LOOP, playIntervalTimeMills);
  }

  public void stopBannerPlay() {
    if (bannerHandler.hasMessages(MSG_BANNER_LOOP)) {
      bannerHandler.removeMessages(MSG_BANNER_LOOP);
    }
  }

  @Override
  protected void onDetachedFromWindow() {
    stopBannerPlay();
    super.onDetachedFromWindow();
  }
}
