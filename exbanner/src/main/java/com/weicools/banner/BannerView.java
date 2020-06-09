package com.weicools.banner;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;

/**
 * @author weicools
 * @date 2020.06.08
 */
@SuppressWarnings("rawtypes")
public class BannerView<T, VH extends BannerFlexibleItem> extends FrameLayout {
  public BannerView(@NonNull Context context) {
    super(context);
  }

  public BannerView(@NonNull Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public BannerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  private List<T> dataList;
}
