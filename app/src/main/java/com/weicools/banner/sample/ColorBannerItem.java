package com.weicools.banner.sample;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.weicools.banner.BannerViewHolder;
import com.weicools.banner.FlexibleBannerItem;

/**
 * @author weicools
 * @date 2020.06.11
 */
class ColorBannerItem implements FlexibleBannerItem {

  static class ColorViewHolder extends BannerViewHolder {
    View colorView;
    TextView descView;

    public ColorViewHolder(View itemView) {
      super(itemView);
      colorView = itemView.findViewById(R.id.colorView);
      descView = itemView.findViewById(R.id.descView);
    }
  }

  private ColorBannerData colorBannerData;

  public ColorBannerItem(ColorBannerData colorBannerData) {
    this.colorBannerData = colorBannerData;
  }

  private int getLayoutRes() {
    return R.layout.item_color_banner;
  }

  @Override
  public BannerViewHolder onCreateViewHolder(@NonNull Context context) {
    return new ColorViewHolder(View.inflate(context, getLayoutRes(), null));
  }

  @Override
  public void onBindViewHolder(@NonNull Context context, @NonNull BannerViewHolder holder, int position) {
    ColorViewHolder colorViewHolder = (ColorViewHolder) holder;
    colorViewHolder.colorView.setBackgroundResource(colorBannerData.colorRes);
    colorViewHolder.descView.setText(colorBannerData.desc);
  }

  @Override
  public int getViewType() {
    return getLayoutRes();
  }
}
