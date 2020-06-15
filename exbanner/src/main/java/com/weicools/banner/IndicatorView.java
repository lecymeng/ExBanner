package com.weicools.banner;

import androidx.viewpager.widget.ViewPager;

/**
 * @author weicools
 * @date 2020.06.15
 */
public interface IndicatorView extends ViewPager.OnPageChangeListener {
  void setIndicatorCount(int indicatorCount);
}
