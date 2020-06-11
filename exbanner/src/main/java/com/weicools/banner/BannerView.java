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
import java.util.ArrayList;
import java.util.List;

/**
 * @author weicools
 * @date 2020.06.08
 */
public class BannerView extends FrameLayout {

  //remove all callback msg on onDetachedFromWindow
  @SuppressLint("HandlerLeak")
  private class BannerHandler extends Handler {
    @Override
    public void handleMessage(@NonNull Message msg) {
      if (msg.what == MSG_BANNER_PLAY) {
        if (pagerAdapter.getCount() < MIN_LOOP_ITEM_COUNT) {
          stopBannerPlay();
          return;
        }
        int item = viewPager.getCurrentItem();
        viewPager.setCurrentItem(++item, true);
        bannerHandler.sendEmptyMessageDelayed(MSG_BANNER_PLAY, bannerConfig.playIntervalMills);
      }
    }
  }

  private static final int MSG_BANNER_PLAY = 100;
  private static final int MIN_LOOP_ITEM_COUNT = 2;

  private BannerConfig bannerConfig;

  private BannerViewPager viewPager;
  private BannerPagerAdapter pagerAdapter;

  private BannerHandler bannerHandler = new BannerHandler();

  private List<BannerViewHolder> viewHolderList = new ArrayList<>();
  private List<FlexibleBannerItem> bannerItemList = new ArrayList<>();

  //<editor-fold desc="Constructor">
  public BannerView(@NonNull Context context) {
    super(context);
    init(context, null);
  }

  public BannerView(@NonNull Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    init(context, attrs);
  }

  public BannerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context, attrs);
  }
  //</editor-fold>

  private void init(Context context, @Nullable AttributeSet attrs) {
    bannerConfig = new BannerConfig(context, attrs);

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

    pagerAdapter = new BannerPagerAdapter(viewHolderList);
    viewPager.setAdapter(pagerAdapter);

    viewPager.setScrollDuration(1000);
  }

  public void updateItemList(@NonNull List<? extends FlexibleBannerItem> itemList) {
    stopBannerPlay();
    int itemListSize = itemList.size();
    if (itemListSize == 0) {
      viewHolderList.clear();
      bannerItemList.clear();
      pagerAdapter.notifyDataSetChanged();
      return;
    }

    boolean isViewTypeSame = true;
    if (itemListSize == 1 && bannerItemList.size() == 1) {
      isViewTypeSame = itemList.get(0).getViewType() == bannerItemList.get(0).getViewType();

    } else if (itemListSize > 1 && itemListSize == bannerItemList.size() - 2) {
      for (int i = 0; i < itemListSize; i++) {
        if (itemList.get(i).getViewType() != bannerItemList.get(i + 1).getViewType()) {
          isViewTypeSame = false;
          break;
        }
      }
    } else {
      isViewTypeSame = false;
    }

    bannerItemList.clear();
    bannerItemList.addAll(itemList);
    if (itemListSize >= MIN_LOOP_ITEM_COUNT) {
      bannerItemList.add(0, itemList.get(itemListSize - 1));
      bannerItemList.add(itemList.get(0));
    }

    int bannerItemListSize = bannerItemList.size();
    if (isViewTypeSame && viewHolderList.size() == bannerItemListSize) {
      for (int i = 0; i < bannerItemListSize; i++) {
        bannerItemList.get(i).onBindViewHolder(getContext(), viewHolderList.get(i), i);
      }
      startBannerPlay();
      return;
    }

    viewHolderList.clear();
    for (int i = 0; i < bannerItemListSize; i++) {
      FlexibleBannerItem flexibleItem = bannerItemList.get(i);
      BannerViewHolder viewHolder = flexibleItem.onCreateViewHolder(getContext());
      flexibleItem.onBindViewHolder(getContext(), viewHolder, i);
      viewHolderList.add(viewHolder);
    }
    pagerAdapter.notifyDataSetChanged();

    viewPager.setCurrentItem(1, false);
    startBannerPlay();
  }

  public void startBannerPlay() {
    stopBannerPlay();
    if (pagerAdapter.getCount() < MIN_LOOP_ITEM_COUNT) {
      return;
    }
    bannerHandler.sendEmptyMessageDelayed(MSG_BANNER_PLAY, bannerConfig.playIntervalMills);
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
