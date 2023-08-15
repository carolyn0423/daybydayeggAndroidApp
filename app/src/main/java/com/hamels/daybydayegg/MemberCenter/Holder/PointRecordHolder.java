package com.hamels.daybydayegg.MemberCenter.Holder;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.PointHistory;

public class PointRecordHolder extends RecyclerView.ViewHolder {
    public static final String TAG = PointRecordHolder.class.getSimpleName();
    public TextView tvDate, tvPoint, tvRemark;
    private Context context;

    public PointRecordHolder(@NonNull View itemView) {
        super(itemView);
        context = itemView.getContext();
        tvDate = itemView.findViewById(R.id.tv_date);
        tvPoint = itemView.findViewById(R.id.btn_point);
        tvRemark = itemView.findViewById(R.id.tv_remark);
    }

    public void setPointLog(PointHistory pointHistory) {
        tvDate.setText(pointHistory.modified_date);
        tvPoint.setText(String.format(context.getString(R.string.point_add), pointHistory.bonus_points));
        tvRemark.setText(pointHistory.remark);
    }
}
