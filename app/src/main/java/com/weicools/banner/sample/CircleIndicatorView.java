package com.weicools.banner.sample;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.weicools.banner.IndicatorView;

/**
 * @author weicools
 * @date 2020.06.15
 */
public class CircleIndicatorView extends View implements IndicatorView {
  private int indicatorCount;
  private int indicatorItemSize;
  private int indicatorItemMargin;

  private int selectedPosition;

  private float radius;
  private float centerY;
  private float startCenterX;

  private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

  private int selectedColor;
  private int unselectedColor;

  public CircleIndicatorView(Context context) {
    super(context);
    init(context, null);
  }

  public CircleIndicatorView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    init(context, attrs);
  }

  public CircleIndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context, attrs);
  }

  private void init(Context context, @Nullable AttributeSet attrs) {
    indicatorItemSize = (int) (Resources.getSystem().getDisplayMetrics().density * 8);
    indicatorItemMargin = (int) (Resources.getSystem().getDisplayMetrics().density * 4);

    radius = indicatorItemSize / 2f;
    centerY = indicatorItemSize / 2f;
    startCenterX = indicatorItemSize / 2f;

    selectedColor = ContextCompat.getColor(context, R.color.colorPrimary);
    unselectedColor = ContextCompat.getColor(context, R.color.colorAccent);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    if (indicatorCount == 0) {
      setVisibility(INVISIBLE);
      return;
    }
    setVisibility(VISIBLE);
    for (int i = 0; i < indicatorCount; i++) {
      float centerX = startCenterX + i * indicatorItemSize + i * indicatorItemMargin;
      paint.setColor(selectedPosition == i ? selectedColor : unselectedColor);
      canvas.drawCircle(centerX, centerY, radius, paint);
    }
  }

  @Override
  public void setIndicatorCount(int indicatorCount) {
    if (this.indicatorCount == indicatorCount) {
      return;
    }

    this.indicatorCount = indicatorCount;
    if (indicatorCount > 0) {
      ViewGroup.LayoutParams layoutParams = getLayoutParams();
      layoutParams.width = indicatorItemSize * indicatorCount + indicatorItemMargin * (indicatorCount - 1);
      layoutParams.height = indicatorItemSize;
    }

    invalidate();
  }

  @Override
  public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
  }

  @Override
  public void onPageSelected(int position) {
    selectedPosition = position;
    invalidate();
  }

  @Override
  public void onPageScrollStateChanged(int state) {
  }
}
