package com.weicools.banner;

import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import java.util.List;

/**
 * @author weicools
 * @date 2020.06.08
 */
class BannerPagerAdapter extends PagerAdapter {

  @NonNull
  private List<BannerViewHolder> viewHolderList;

  BannerPagerAdapter(@NonNull List<BannerViewHolder> viewHolderList) {
    this.viewHolderList = viewHolderList;
  }

  @Override
  public int getCount() {
    return viewHolderList.size();
  }

  @Override
  public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
    return view == object;
  }

  @NonNull
  @Override
  public Object instantiateItem(@NonNull ViewGroup container, int position) {
    View itemView = viewHolderList.get(position).itemView;
    container.addView(itemView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    return itemView;
  }

  @Override
  public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
    container.removeView((View) object);
  }
}
