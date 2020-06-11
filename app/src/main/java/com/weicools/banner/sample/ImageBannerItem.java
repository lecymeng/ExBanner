package com.weicools.banner.sample;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import com.weicools.banner.BannerViewHolder;
import com.weicools.banner.FlexibleBannerItem;

/**
 * @author weicools
 * @date 2020.06.11
 */
class ImageBannerItem implements FlexibleBannerItem {

  static class ImageViewHolder extends BannerViewHolder {
    ImageView imageView;
    TextView descView;

    public ImageViewHolder(View itemView) {
      super(itemView);
      imageView = itemView.findViewById(R.id.imageView);
      descView = itemView.findViewById(R.id.descView);
    }
  }

  @DrawableRes
  private int imgRes;
  private String desc;

  public ImageBannerItem(@DrawableRes int imgRes, String desc) {
    this.imgRes = imgRes;
    this.desc = desc;
  }

  private int getLayoutRes() {
    return R.layout.item_image_banner;
  }

  @Override
  public BannerViewHolder onCreateViewHolder(@NonNull Context context) {
    return new ImageViewHolder(View.inflate(context, getLayoutRes(), null));
  }

  @Override
  public void onBindViewHolder(@NonNull Context context, @NonNull BannerViewHolder holder, int position) {
    ImageViewHolder colorViewHolder = (ImageViewHolder) holder;
    colorViewHolder.imageView.setImageResource(imgRes);
    colorViewHolder.descView.setText(desc);
  }

  @Override
  public int getViewType() {
    return getLayoutRes();
  }
}
