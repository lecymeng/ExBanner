package com.weicools.banner;

import android.content.Context;
import androidx.annotation.NonNull;

/**
 * @author weicools
 * @date 2020.06.08
 */
public interface FlexibleBannerItem {

  BannerViewHolder onCreateViewHolder(@NonNull Context context);

  void onBindViewHolder(@NonNull Context context, @NonNull BannerViewHolder holder, int position);

  int getViewType();
}
