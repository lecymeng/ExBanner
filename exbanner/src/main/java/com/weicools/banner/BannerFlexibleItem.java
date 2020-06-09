package com.weicools.banner;

import android.content.Context;
import androidx.annotation.NonNull;

/**
 * @author weicools
 * @date 2020.06.08
 */
public interface BannerFlexibleItem<VH extends BannerViewHolder> {

  VH createView(@NonNull Context context);

  void onBindView(VH holder, int position, int size);

  int getViewType();
}
