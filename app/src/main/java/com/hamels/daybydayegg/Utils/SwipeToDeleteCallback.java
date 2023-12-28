package com.hamels.daybydayegg.Utils;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.hamels.daybydayegg.MemberCenter.Adapter.OftenAdapter;
import com.hamels.daybydayegg.R;

public class SwipeToDeleteCallback extends ItemTouchHelper.Callback {

    private static final float SWIPE_THRESHOLD = 10;
    private final OftenAdapter adapter;
    private boolean swipeButtonVisible = false;
    private float buttonWidth = 0;

    public SwipeToDeleteCallback(OftenAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        // 设置支持的滑动方向为左滑
        return makeMovementFlags(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        // 不在此处执行删除操作，而是在用户点击删除按钮时执行
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View itemView = viewHolder.itemView;

        ConstraintLayout btn_save = viewHolder.itemView.findViewById(R.id.btn_save);
        ConstraintLayout btn_delete = viewHolder.itemView.findViewById(R.id.btn_delete);

        // 设置画笔颜色和样式
        Paint paint = new Paint();
        paint.setColor(Color.RED); // 设置颜色
        paint.setStyle(Paint.Style.FILL); // 设置样式为填充
        RectF background = null;

        if (dX < 0) {
            background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
            c.drawRect(background, paint);

            switch (btn_delete.getVisibility()){
                case View.VISIBLE:
                    break;
                case View.GONE:
                    btn_save.setVisibility(View.GONE);
                    btn_delete.setVisibility(View.VISIBLE);
                    break;
            }
        } else {
            btn_save.setVisibility(View.VISIBLE);
            btn_delete.setVisibility(View.GONE);
            btn_save.bringToFront();
            background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX, (float) itemView.getBottom());
            c.drawRect(background, paint);
        }

        //super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public float getSwipeThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
        // 返回阈值为一个较大的值，避免滑动即触发删除操作
        return 1000f;
    }

//    @Override
//    public float getSwipeEscapeVelocity(float defaultValue) {
//        // 返回一个较小的值，以降低触发滑动操作的速度
//        return 2f * defaultValue;
//    }
}
