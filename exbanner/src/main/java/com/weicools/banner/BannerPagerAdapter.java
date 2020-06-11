package com.weicools.banner;

import android.content.Context;
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
class BannerPagerAdapter extends PagerAdapter {
  private List<BannerViewHolder> viewHolderList = new ArrayList<>();
  private List<FlexibleBannerItem> bannerItemList = new ArrayList<>();

  @NonNull
  private Context context;

  BannerPagerAdapter(@NonNull Context context) {
    this.context = context;
  }

  void updateData(@NonNull List<? extends FlexibleBannerItem> itemList) {
    int itemListSize = itemList.size();
    if (itemListSize == 0) {
      viewHolderList.clear();
      bannerItemList.clear();
      notifyDataSetChanged();
      return;
    }

    boolean isViewTypeSame = true;
    if (itemListSize == 1 && bannerItemList.size() == 1) {
      isViewTypeSame = itemList.get(0).getViewType() == bannerItemList.get(0).getViewType();

    } else if (itemListSize > 1 && itemListSize == bannerItemList.size() - 2) {
      for (int i = 0; i < itemListSize; i++) {
        if (itemList.get(i).getViewType() != bannerItemList.get(i + 1).getViewType()) {
          isViewTypeSame = false;
          break;
        }
      }

    } else {
      isViewTypeSame = false;
    }

    bannerItemList.clear();
    bannerItemList.addAll(itemList);
    if (itemListSize > 1) {
      bannerItemList.add(0, itemList.get(itemListSize - 1));
      bannerItemList.add(itemList.get(0));
    }

    int flexibleItemSize = bannerItemList.size();
    if (isViewTypeSame && viewHolderList.size() == flexibleItemSize) {
      for (int i = 0; i < bannerItemList.size(); i++) {
        bannerItemList.get(i).onBindViewHolder(context, viewHolderList.get(i), i);
      }
      return;
    }

    viewHolderList.clear();
    for (int i = 0; i < flexibleItemSize; i++) {
      FlexibleBannerItem flexibleItem = bannerItemList.get(i);
      BannerViewHolder viewHolder = flexibleItem.onCreateViewHolder(context);
      flexibleItem.onBindViewHolder(context, viewHolder, i);
      viewHolderList.add(viewHolder);
    }
    notifyDataSetChanged();
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
