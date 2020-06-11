package com.weicools.banner.sample;

import androidx.annotation.ColorRes;

/**
 * @author weicools
 * @date 2020.06.11
 */
class ColorBannerData {
  @ColorRes
  int colorRes;
  String desc;

  ColorBannerData(@ColorRes int colorRes, String desc) {
    this.colorRes = colorRes;
    this.desc = desc;
  }
}
