package com.weicools.banner;

import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author weicools
 * @date 2020.06.08
 */
@SuppressWarnings("rawtypes")
public class BannerPagerAdapter<T, VH extends BannerViewHolder> extends PagerAdapter {
  private List<T> dataList = new ArrayList<>();
  private List<VH> viewHoldList = new ArrayList<>();

  public void updateData(@NonNull List<T> dataList) {
    if (dataList.isEmpty()) {
      this.dataList.clear();
      this.viewHoldList.clear();
      notifyDataSetChanged();
      return;
    }
    if (this.dataList.isEmpty() || this.dataList.size() != dataList.size()) {
      this.dataList.clear();
      for (int i = 0; i < dataList.size(); i++) {

      }
    }
  }

  @Override public int getCount() {
    return viewHoldList.size();
  }

  @Override public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
    return view == object;
  }

  @NonNull @Override public Object instantiateItem(@NonNull ViewGroup container, int position) {
    return super.instantiateItem(container, position);
  }

  @Override public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
    container.removeView((View) object);
  }
}
