package com.hamels.daybydayegg.Donate.Holder;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hamels.daybydayegg.Donate.View.DonateFragment;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.Donate;
import com.hamels.daybydayegg.EOrderApplication;

public class DonateHolder extends RecyclerView.ViewHolder {
    public static final String TAG = DonateHolder.class.getSimpleName();
    // donatelist
    public ImageView img_donate_left, img_donate_right, tv_cart_left, tv_cart_right;
    public TextView tv_type_name_left_title, tv_type_name_right_title, tv_spec_name_left_title, tv_spec_name_right_title, tv_give_quantity_left_title, tv_give_quantity_right_title, tv_buy_quantity_left_title, tv_buy_quantity_right_title;
    public TextView tv_product_name_left, tv_spec_name2_left, tv_eticket_due_date_left, tv_type_name_left, tv_spec_name_left, tv_buy_quantity_left, tv_give_quantity_left, tv_left_number_left;
    public TextView tv_product_name_right, tv_spec_name2_right, tv_eticket_due_date_right, tv_type_name_right, tv_spec_name_right, tv_buy_quantity_right, tv_give_quantity_right, tv_left_number_right;
    public TextView tv_giveflag_left, tv_giveflag_right;
    public ConstraintLayout layout_left, layout_right, donatehistory_constraintLayout;

    // donatehistorylist no meal_no
    public TextView tv_meal_no_title, tv_meal_no, tv_tickets_count_title, tv_tickets_count, tv_ref_content, tv_writeoff_due_date, tv_expiredflag_left;
    public TextView tv_product_name, tv_spec_name;

    // meal_no
    public ConstraintLayout donatehistory_constraintLayout2;
    public ImageView img_product;
    private TextView tv_title2, tv_meal_no_title2, tv_meal_no2, tv_tickets_count_title2, tv_tickets_count2, tv_pickup_date2, tv_order_no2, tv_order_date2, tv_order_status2, tv_writeoff_due_date2;

    public DonateHolder(@NonNull View itemView) {
        super(itemView);

        // donatelist
        img_donate_left = itemView.findViewById(R.id.img_donate_left);
        img_donate_right = itemView.findViewById(R.id.img_donate_right);

        tv_product_name_left = itemView.findViewById(R.id.tv_product_name_left);
        tv_product_name_right = itemView.findViewById(R.id.tv_product_name_right);
        //tv_spec_name2_left = itemView.findViewById(R.id.tv_spec_name2_left);
        //tv_spec_name2_right = itemView.findViewById(R.id.tv_spec_name2_right);
        tv_eticket_due_date_left = itemView.findViewById(R.id.tv_eticket_due_date_left);
        tv_eticket_due_date_right = itemView.findViewById(R.id.tv_eticket_due_date_right);
        tv_type_name_left_title = itemView.findViewById(R.id.tv_type_name_left_title);
        tv_type_name_left = itemView.findViewById(R.id.tv_type_name_left);
        tv_type_name_right_title = itemView.findViewById(R.id.tv_type_name_right_title);
        tv_type_name_right = itemView.findViewById(R.id.tv_type_name_right);
        tv_spec_name_left_title = itemView.findViewById(R.id.tv_spec_name_left_title);
        tv_spec_name_left = itemView.findViewById(R.id.tv_spec_name_left);
        tv_spec_name_right_title = itemView.findViewById(R.id.tv_spec_name_right_title);
        tv_spec_name_right = itemView.findViewById(R.id.tv_spec_name_right);
        tv_buy_quantity_left_title = itemView.findViewById(R.id.tv_buy_quantity_left_title);
        tv_buy_quantity_left = itemView.findViewById(R.id.tv_buy_quantity_left);
        tv_buy_quantity_right_title = itemView.findViewById(R.id.tv_buy_quantity_right_title);
        tv_buy_quantity_right = itemView.findViewById(R.id.tv_buy_quantity_right);
        tv_give_quantity_left_title = itemView.findViewById(R.id.tv_give_quantity_left_title);
        tv_give_quantity_left = itemView.findViewById(R.id.tv_give_quantity_left);
        tv_give_quantity_right_title = itemView.findViewById(R.id.tv_give_quantity_right_title);
        tv_give_quantity_right = itemView.findViewById(R.id.tv_give_quantity_right);
        tv_left_number_left = itemView.findViewById(R.id.tv_left_number_left);
        tv_left_number_right = itemView.findViewById(R.id.tv_left_number_right);
        tv_giveflag_left = itemView.findViewById(R.id.tv_giveflag_left);
        tv_giveflag_right = itemView.findViewById(R.id.tv_giveflag_right);
        tv_cart_left = itemView.findViewById(R.id.tv_cart_left);
        tv_cart_right = itemView.findViewById(R.id.tv_cart_right);

        layout_left = itemView.findViewById(R.id.donate_constraintLayout_left);
        layout_right = itemView.findViewById(R.id.donate_constraintLayout_right);

        // donatehistorylist
        tv_meal_no_title = itemView.findViewById(R.id.tv_meal_no_title);
        tv_meal_no = itemView.findViewById(R.id.tv_meal_no);
        tv_tickets_count_title = itemView.findViewById(R.id.tv_tickets_count_title);
        tv_tickets_count = itemView.findViewById(R.id.tv_tickets_count);
        tv_ref_content = itemView.findViewById(R.id.tv_ref_content);
        tv_writeoff_due_date = itemView.findViewById(R.id.tv_writeoff_due_date);
        tv_expiredflag_left = itemView.findViewById(R.id.tv_expiredflag_left);

        donatehistory_constraintLayout = itemView.findViewById(R.id.donatehistory_constraintLayout);

        tv_product_name = itemView.findViewById(R.id.tv_product_name);
        tv_spec_name = itemView.findViewById(R.id.tv_spec_name);

        donatehistory_constraintLayout2 = itemView.findViewById(R.id.donatehistory_constraintLayout2);
        tv_title2 = itemView.findViewById(R.id.tv_title2);
        img_product = itemView.findViewById(R.id.img_product);
        tv_meal_no_title2 = itemView.findViewById(R.id.tv_meal_no_title2);
        tv_meal_no2 = itemView.findViewById(R.id.tv_meal_no2);
        tv_tickets_count_title2 = itemView.findViewById(R.id.tv_tickets_count_title2);
        tv_tickets_count2 = itemView.findViewById(R.id.tv_tickets_count2);
        tv_pickup_date2 = itemView.findViewById(R.id.tv_pickup_date2);
        tv_order_no2 = itemView.findViewById(R.id.tv_order_no2);
        tv_order_date2 = itemView.findViewById(R.id.tv_order_date2);
        tv_order_status2 = itemView.findViewById(R.id.tv_order_status2);
        tv_writeoff_due_date2 = itemView.findViewById(R.id.tv_writeoff_due_date2);
    }

    public void setImg_product_two(Donate productleft, Donate productright) {
        img_donate_left.setTag(R.id.img_donate_left, productleft.getUid());
        img_donate_right.setTag(R.id.img_donate_right, productright.getUid());
        String sLeftPictureUrl = productleft.getPictureUrl().equals("") ? EOrderApplication.DEFAULT_PICTURE_URL : productleft.getPictureUrl();
        String sRightPictureUrl = productright.getPictureUrl().equals("") ? EOrderApplication.DEFAULT_PICTURE_URL : productright.getPictureUrl();

        Glide.with(DonateFragment.getInstance()).load(EOrderApplication.sApiUrl + sLeftPictureUrl).into(img_donate_left);
        Glide.with(DonateFragment.getInstance()).load(EOrderApplication.sApiUrl + sRightPictureUrl).into(img_donate_right);

        tv_eticket_due_date_left.setText(productleft.getEticketDueDate());
        tv_eticket_due_date_right.setText(productright.getEticketDueDate());

        tv_product_name_left.setText(productleft.getProductName());
        tv_product_name_right.setText(productright.getProductName());

        //tv_spec_name2_left.setText(" (" + productleft.getTypeName() + "-" + productleft.getSpecName() + ")");
        //tv_spec_name2_right.setText(" (" + productright.getTypeName() + "-" + productright.getSpecName() + ")");

        if (productleft.getTicketStatus().equals("B")) {
            tv_give_quantity_left_title.setVisibility(View.GONE);
            tv_give_quantity_left.setVisibility(View.GONE);

            tv_buy_quantity_left.setText(productleft.getBuy_quantity());
        } else if (productleft.getTicketStatus().equals("R")) {
            tv_buy_quantity_left_title.setVisibility(View.GONE);
            tv_buy_quantity_left.setVisibility(View.GONE);

            tv_give_quantity_left.setText(productleft.getGive_quantity());

            tv_giveflag_left.setVisibility(View.VISIBLE);
        }

        if (productright.getTicketStatus().equals("B")) {
            tv_give_quantity_right_title.setVisibility(View.GONE);
            tv_give_quantity_right.setVisibility(View.GONE);

            tv_buy_quantity_right.setText(productright.getBuy_quantity());
        } else if (productright.getTicketStatus().equals("R")) {
            tv_buy_quantity_right_title.setVisibility(View.GONE);
            tv_buy_quantity_right.setVisibility(View.GONE);

            tv_give_quantity_right.setText(productright.getGive_quantity());

            tv_giveflag_right.setVisibility(View.VISIBLE);
        }

        tv_left_number_left.setText(productleft.getLeftNumber());
        tv_left_number_right.setText(productright.getLeftNumber());

        if (productleft.getcart_flag().equals("Y")) {
            tv_cart_left.setImageResource(R.drawable.sge);
        } else {
            tv_cart_left.setImageResource(R.drawable.sga);
        }

        if (productright.getcart_flag().equals("Y")) {
            tv_cart_right.setImageResource(R.drawable.sge);
        } else {
            tv_cart_right.setImageResource(R.drawable.sga);
        }

        layout_left.setTag(R.id.donate_constraintLayout_left, productleft.getUid());
        layout_right.setTag(R.id.donate_constraintLayout_right, productright.getUid());

        chkImgDrawable(img_donate_left, productleft);
        chkImgDrawable(img_donate_right, productright);
    }

    public void setImg_product_one(Donate productleft) {
        img_donate_right.setVisibility(View.INVISIBLE);
        img_donate_left.setTag(R.id.img_donate_left, productleft.getUid());
        String sLeftPictureUrl = productleft.getPictureUrl().equals("") ? EOrderApplication.DEFAULT_PICTURE_URL : productleft.getPictureUrl();

        Glide.with(DonateFragment.getInstance()).load(EOrderApplication.sApiUrl + sLeftPictureUrl).into(img_donate_left);

        tv_product_name_left.setText(productleft.getProductName());
        tv_product_name_right.setVisibility(View.INVISIBLE);

        //tv_spec_name2_left.setText(" (" + productleft.getTypeName() + "-" + productleft.getSpecName() + ")");

        tv_eticket_due_date_left.setText(productleft.getEticketDueDate());
        tv_eticket_due_date_right.setVisibility(View.INVISIBLE);

        if (productleft.getTicketStatus().equals("B")) {
            tv_give_quantity_left_title.setVisibility(View.GONE);
            tv_give_quantity_left.setVisibility(View.GONE);

            tv_buy_quantity_left.setText(productleft.getBuy_quantity());
        } else if (productleft.getTicketStatus().equals("R")) {
            tv_buy_quantity_left_title.setVisibility(View.GONE);
            tv_buy_quantity_left.setVisibility(View.GONE);

            tv_give_quantity_left.setText(productleft.getGive_quantity());

            tv_giveflag_left.setVisibility(View.VISIBLE);
        }

        tv_give_quantity_right.setVisibility(View.INVISIBLE);

        tv_buy_quantity_left.setText(productleft.getBuy_quantity());
        tv_buy_quantity_right.setVisibility(View.INVISIBLE);

        tv_left_number_left.setText(productleft.getLeftNumber());
        tv_left_number_right.setVisibility(View.INVISIBLE);

        if (productleft.getcart_flag().equals("Y")) {
            tv_cart_left.setImageResource(R.drawable.sge);
        } else {
            tv_cart_left.setImageResource(R.drawable.sga);
        }
        tv_cart_right.setVisibility(View.INVISIBLE);

        layout_left.setTag(R.id.donate_constraintLayout_left, productleft.getUid());
        layout_right.setVisibility(View.INVISIBLE);

        chkImgDrawable(img_donate_left, productleft);
    }

    public void setImg_product_history(Donate history) {
        if (history.getmeal_no().equals("")) {
            donatehistory_constraintLayout.setVisibility(View.VISIBLE);
            donatehistory_constraintLayout2.setVisibility(View.GONE);

            tv_meal_no_title.setText("轉贈提貨券");
            //tv_meal_no_title.setTextSize(13);
            tv_meal_no.setText("");
        }
        else {
            donatehistory_constraintLayout.setVisibility(View.GONE);
            donatehistory_constraintLayout2.setVisibility(View.VISIBLE);

            // pickupway
            switch (history.getpickup_way()) {
                case "1":
                case "DO":
                case "FP":
                case "UE":
                    tv_title2.setText(history.getLocationName() + "配送");
                    img_product.setImageResource(R.drawable.delivery_service);
                    break;
                case "2":
                case "TO":
                    tv_title2.setText(history.getLocationName());
                    img_product.setImageResource(R.drawable.takeout_service);
                    break;
            }
            tv_meal_no_title2.setText(history.getpickup_way_name());

            tv_meal_no2.setText(history.getmeal_no());
            tv_tickets_count2.setText(history.getquantity());
            tv_pickup_date2.setText(history.getbooking_pickup_datetime());
            tv_order_no2.setText(history.getWriteoff_order_id().substring(0, 8) + "-" + history.getWriteoff_order_id().substring(8, 14));
            tv_order_date2.setText(history.getorder_end_datetime());
            tv_order_status2.setText(history.getorder_status_name());
            tv_writeoff_due_date2.setText(history.getwriteoff_due_date());
        }

        tv_tickets_count_title.setText(history.getshowText() + "：");
        tv_tickets_count.setText(history.getquantity());
        tv_writeoff_due_date.setText(history.getwriteoff_due_date());

        switch (history.getTicketStatus()){
            case "B": //購買
                tv_giveflag_left.setVisibility(View.INVISIBLE);
                break;
            case "G": //贈出
                tv_giveflag_left.setVisibility(View.INVISIBLE);
                tv_ref_content.setText("受贈者：" + history.getGive_member_name() + " " + history.getGive_member_mobile());
                break;
            case "R": //接收
                tv_giveflag_left.setVisibility(View.VISIBLE);
                break;
            case "BU": //自購自用
            case "RU": //接收贈送兌換
                tv_giveflag_left.setVisibility(View.INVISIBLE);
                if(history.getWriteoff_order_id().equals("")){ //線下店鋪核銷
                    tv_ref_content.setText("店家核銷");
                }else{ //線上APP訂單核銷
                    tv_ref_content.setText("訂單編號："+ history.getWriteoff_order_id());
                }
                break;
        }

//        if (history.getTicketStatus().equals("R")) {
//            tv_giveflag_left.setVisibility(View.VISIBLE);
//        } else {
//            tv_giveflag_left.setVisibility(View.INVISIBLE);
//        }

        if (history.getdue_flag().equals("Y")) {
            tv_expiredflag_left.setVisibility(View.VISIBLE);
        } else {
            tv_expiredflag_left.setVisibility(View.INVISIBLE);
        }
    }

    public void sethistorydetail(Donate history) {
        tv_meal_no.setText(history.getmeal_no());
        tv_tickets_count_title.setText(history.getshowText() + "：");
        tv_tickets_count.setText(history.getquantity());
        tv_writeoff_due_date.setText(history.getwriteoff_due_date());

        switch (history.getTicketStatus()){
            case "B": //購買
                tv_giveflag_left.setVisibility(View.INVISIBLE);
                break;
            case "G": //贈出
                tv_giveflag_left.setVisibility(View.INVISIBLE);
                tv_ref_content.setText("受贈者：" + history.getGive_member_name() + " " + history.getGive_member_mobile());
                break;
            case "R": //接收
                tv_giveflag_left.setVisibility(View.VISIBLE);
                break;
            case "BU": //自購自用
            case "RU": //接收贈送兌換
                tv_giveflag_left.setVisibility(View.INVISIBLE);
                if(history.getWriteoff_order_id().equals("")){ //線下店鋪核銷
                    tv_ref_content.setText("店家核銷");
                }else{ //線上APP訂單核銷
                    tv_ref_content.setText("訂單編號："+ history.getWriteoff_order_id());
                }
                break;
        }

//        if (history.getTicketStatus().equals("R")) {
//            tv_giveflag_left.setVisibility(View.VISIBLE);
//        } else {
//            tv_giveflag_left.setVisibility(View.INVISIBLE);
//        }

        if (history.getdue_flag().equals("Y")) {
            tv_expiredflag_left.setVisibility(View.VISIBLE);
        } else {
            tv_expiredflag_left.setVisibility(View.INVISIBLE);
        }
    }

    public void sethistorydetail2(Donate history) {
        tv_product_name.setText(history.getProductName());
        //tv_product_name.setText(history.getProductName() + " (" + history.getTypeName() + "-" + history.getSpecName() + ")");
        //tv_spec_name.setText(" (" + history.getTypeName() + "-" + history.getSpecName() + ")");
        tv_tickets_count.setText(history.getquantity());
    }

    private void chkImgDrawable(ImageView imageView, Donate donate){
        if(donate.getTicketEnabled().equals("N")){
            //  已下架，加上浮水印
            setImgDrawable(imageView, "已下架");
        } else if(donate.getTicketDue().equals("Y")){
            //  已過期，加上浮水印
            setImgDrawable(imageView, "已過期");
        }else{
            setImgDrawable(imageView, "");
        }
    }

    private void setImgDrawable(ImageView imageView, String sShowName){

        imageView.post(new Runnable() {
            @Override
            public void run() {

                ViewTreeObserver viewTreeObserver = imageView.getViewTreeObserver();
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {

                        if(!sShowName.equals("")) {

                            BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
                            if(drawable != null) {
                                Bitmap bitmap = drawable.getBitmap();

                                // 縮小圖像為原來的1/2
                                Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() / 2, bitmap.getHeight() / 2, false);

                                // 複製縮小後的圖像，以便在其上繪製浮水印
                                Bitmap mutableBitmap = scaledBitmap.copy(Bitmap.Config.ARGB_8888, true);

                                Canvas canvas = new Canvas(mutableBitmap);

                                // 設置浮水印的圓形形狀，透明度為50%
                                Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                                paint.setColor(Color.parseColor("#80000000")); // 設置透明深灰色底色
                                paint.setAlpha(128);
                                float diameter = Math.min(scaledBitmap.getWidth(), scaledBitmap.getHeight()) / 4f; // 直徑縮小為原來的一半
                                float centerX = scaledBitmap.getWidth() / 2f;
                                float centerY = scaledBitmap.getHeight() / 2f;
                                RectF rect = new RectF(centerX - diameter, centerY - diameter, centerX + diameter, centerY + diameter);
                                canvas.drawOval(rect, paint);

                                // 設置浮水印的文字內容和格式
                                String watermarkText = sShowName;
                                Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                                textPaint.setColor(Color.WHITE); // 設置白色字體
                                textPaint.setTextSize(36);

                                // 計算文字內容的寬度和高度
                                float textWidth = textPaint.measureText(watermarkText);
                                float textHeight = textPaint.getFontMetrics().bottom - textPaint.getFontMetrics().top;

                                // 計算浮水印的位置，使其置中於圖像中央
                                float textX = centerX - textWidth / 2f;
                                float textY = centerY + textHeight / 2f;

                                // 在圖像上繪製浮水印
                                canvas.drawText(watermarkText, textX, textY, textPaint);

                                // 將包含浮水印的Bitmap對象設置為ImageView的圖像
                                imageView.setImageBitmap(mutableBitmap);

                                //移除viewTreeObserver，避免重複調用
                                ViewTreeObserver viewTreeObserver = imageView.getViewTreeObserver();
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                    viewTreeObserver.removeOnGlobalLayoutListener(this);
                                } else {
                                    viewTreeObserver.removeGlobalOnLayoutListener(this);
                                }
                            }
                        }else{
                            // 從ImageView中獲取Drawable對象
                            Drawable drawable = imageView.getDrawable();

                            // 如果Drawable對象是BitmapDrawable類型，可以直接從中獲取Bitmap對象
                            if (drawable instanceof BitmapDrawable) {
                                Bitmap originalBitmap = ((BitmapDrawable) drawable).getBitmap();
                                imageView.setImageBitmap(originalBitmap);
                            }
                        }
                    }
                });
            }
        });
    }
}