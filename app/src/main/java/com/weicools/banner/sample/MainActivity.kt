package com.weicools.banner.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val imageDataList = mutableListOf(
      ColorBannerItem(ColorBannerData(R.color.green_kuan, "酷安绿")),
      ColorBannerItem(ColorBannerData(R.color.pink_bilibili, "哔哩粉")),
      ColorBannerItem(ColorBannerData(R.color.blue_zhihu, "知乎蓝")),
      ColorBannerItem(ColorBannerData(R.color.blue_cyan, "水鸭青")),
      ColorBannerItem(ColorBannerData(R.color.black_gaoduan, "高端黑"))
    )

    bannerView.updateDataList(imageDataList)
  }
}