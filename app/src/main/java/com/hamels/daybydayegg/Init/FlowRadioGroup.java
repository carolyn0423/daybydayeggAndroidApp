package com.hamels.daybydayegg.Init;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RadioGroup;

public class FlowRadioGroup extends RadioGroup {
    public FlowRadioGroup(Context context) {
        super(context);
    }

    public FlowRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        //呼叫ViewGroup的方法，測量子view
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        //最大的寬
        int maxWidth = 0;
        //累計的高
        int totalHeight = 0;

        //當前這一行的累計行寬
        int lineWidth = 0;
        //當前這行的最大行高
        int maxLineHeight = 0;
        //用於記錄換行前的行寬和行高
        int oldHeight;
        int oldWidth;

        int count = getChildCount();
        //假設 widthMode和heightMode都是AT_MOST
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            MarginLayoutParams params = (MarginLayoutParams) child.getLayoutParams();
            //得到這一行的最高
            oldHeight = maxLineHeight;
            //當前最大寬度
            oldWidth = maxWidth;

            int deltaX = child.getMeasuredWidth() + params.leftMargin + params.rightMargin;
            if (lineWidth + deltaX + getPaddingLeft() + getPaddingRight() > widthSize) {//如果折行,height增加
                //和目前最大的寬度比較,得到最寬。不能加上當前的child的寬,所以用的是oldWidth
                maxWidth = Math.max(lineWidth, oldWidth);
                //重置寬度
                lineWidth = deltaX;
                //累加高度
                totalHeight += oldHeight;
                //重置行高,當前這個View，屬於下一行，因此當前最大行高為這個child的高度加上margin
                maxLineHeight = child.getMeasuredHeight() + params.topMargin + params.bottomMargin;

            } else {
                //不換行，累加寬度
                lineWidth += deltaX;
                //不換行，計算行最高
                int deltaY = child.getMeasuredHeight() + params.topMargin + params.bottomMargin;
                maxLineHeight = Math.max(maxLineHeight, deltaY);
            }
            if (i == count - 1) {
                //前面沒有加上下一行的搞，如果是最後一行，還要再疊加上最後一行的最高的值
                totalHeight += maxLineHeight;
                //計算最後一行和前面的最寬的一行比較
                maxWidth = Math.max(lineWidth, oldWidth);
            }
        }
        //加上當前容器的padding值
        maxWidth += getPaddingLeft() + getPaddingRight();
        totalHeight += getPaddingTop() + getPaddingBottom();
        setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize : maxWidth,
                heightMode == MeasureSpec.EXACTLY ? heightSize : totalHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        //pre為前面所有的child的相加後的位置
        int preLeft = getPaddingLeft();
        int preTop = getPaddingTop();
        //記錄每一行的最高值
        int maxHeight = 0;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            MarginLayoutParams params = (MarginLayoutParams) child.getLayoutParams();
            //r-l為當前容器的寬度。如果子view的累積寬度大於容器寬度，就換行。
            if (preLeft + params.leftMargin + child.getMeasuredWidth() + params.rightMargin + getPaddingRight() > (r - l)) {
                //重置
                preLeft = getPaddingLeft();
                //要選擇child的height最大的作為設定
                preTop = preTop + maxHeight;
                maxHeight = getChildAt(i).getMeasuredHeight() + params.topMargin + params.bottomMargin;
            } else { //不換行,計算最大高度
                maxHeight = Math.max(maxHeight, child.getMeasuredHeight() + params.topMargin + params.bottomMargin);
            }
            //left座標
            int left = preLeft + params.leftMargin;
            //top座標
            int top = preTop + params.topMargin;
            int right = left + child.getMeasuredWidth();
            int bottom = top + child.getMeasuredHeight();
            //為子view佈局
            child.layout(left, top, right, bottom);
            //計算佈局結束後，preLeft的值
            preLeft += params.leftMargin + child.getMeasuredWidth() + params.rightMargin;
        }
    }
}
