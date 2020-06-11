## ExBanner

### BannerView 轮播实现

### Banner 使用

#### 1. 定义ViewHolder 继承自BannerViewHolder
```java
class ColorViewHolder extends BannerViewHolder {
  View colorView;
  TextView descView;

  public ColorViewHolder(View itemView) {
    super(itemView);
    colorView = itemView.findViewById(R.id.colorView);
    descView = itemView.findViewById(R.id.descView);
  }
}
```

#### 2. 定义Item 实现FlexibleBannerItem
```java
class ColorBannerItem implements FlexibleBannerItem {

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
```

#### 3. 更新Banner数据
```kotlin
val colorDataList = mutableListOf(
  ColorBannerItem(ColorBannerData(R.color.green_kuan, "酷安绿")),
  ColorBannerItem(ColorBannerData(R.color.pink_bilibili, "哔哩粉")),
  ColorBannerItem(ColorBannerData(R.color.blue_zhihu, "知乎蓝")),
  ColorBannerItem(ColorBannerData(R.color.blue_cyan, "水鸭青")),
  ColorBannerItem(ColorBannerData(R.color.black_gaoduan, "高端黑"))
)

bannerView.updateItemList(colorDataList)
```