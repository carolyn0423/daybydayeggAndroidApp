package com.hamels.daybydayegg.MemberCenter.Holder;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.MemberMessage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MailFileHolder extends RecyclerView.ViewHolder {
    public static final String TAG = MailFileHolder.class.getSimpleName();
    public SwipeLayout swipeLayout;
    public ConstraintLayout mailLayout;
    public ImageView imgMailStatus;
    public ImageButton btnDelete;
    public TextView tvMailTitle, tvMailContent, tvMailDate;

    public MailFileHolder(@NonNull View itemView) {
        super(itemView);

        swipeLayout = itemView.findViewById(R.id.layout_swipe);
        mailLayout = itemView.findViewById(R.id.layout_lot);
        imgMailStatus = itemView.findViewById(R.id.img_mail_status);
        btnDelete = itemView.findViewById(R.id.btn_delete);
        tvMailTitle = itemView.findViewById(R.id.tv_mail_title);
        tvMailContent = itemView.findViewById(R.id.tv_mail_content);
        tvMailDate = itemView.findViewById(R.id.tv_mail_date);
    }

    public void setMessage(MemberMessage message, boolean isRead) {
        imgMailStatus.setImageResource(isRead ? R.drawable.ic_mail_read : R.drawable.ic_mail_unread);
        swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);

        tvMailTitle.setText(message.getTitle());
        // 使用 HtmlCompat.fromHtml
        CharSequence formattedText = HtmlCompat.fromHtml(message.getContent(), HtmlCompat.FROM_HTML_MODE_LEGACY);
        tvMailContent.setText(formattedText);
        //tvMailContent.setText(Html.fromHtml(message.getContent()));
        Log.e(TAG,"Time : "+message.getCompletedAt());
//        tvMailDate.setText(getFormatDate(message.getCompletedAt()));
        tvMailDate.setText(message.getCompletedAt());
    }

    private String getFormatDate(String itemDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy.MM.dd");
        try {
            Date date = format.parse(itemDate);
            return transFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }
}
