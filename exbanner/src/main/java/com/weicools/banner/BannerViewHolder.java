package com.weicools.banner;

import android.view.ViewGroup;

/**
 * @author weicools
 * @date 2020.06.08
 */
public interface BannerViewHolder<T, VH> {
  VH onCreateHolder(ViewGroup parent, int viewType);

  void onBindView(VH holder, T data, int position, int size);
}
