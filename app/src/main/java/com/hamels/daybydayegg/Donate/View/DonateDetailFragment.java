package com.hamels.daybydayegg.Donate.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.hamels.daybydayegg.Base.BaseFragment;
import com.hamels.daybydayegg.Donate.Presenter.DonateDetailPresenter;
import com.hamels.daybydayegg.EOrderApplication;
import com.hamels.daybydayegg.Login.VIew.LoginActivity;
import com.hamels.daybydayegg.Main.View.MainActivity;
import com.hamels.daybydayegg.Donate.Contract.DonateDetailContract;
//import com.hamels.daybydayegg.Donate.Presenter.DonateDetailPresenter;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.Donate;
//import com.hamels.daybydayegg.Repository.Model.DonatePicture;
//import com.hamels.daybydayegg.Repository.Model.DonateSpec;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DonateDetailFragment extends BaseFragment implements DonateDetailContract.View {
    public static final String TAG = DonateDetailFragment.class.getSimpleName();
    private static final String UID = "uid";
    private static DonateDetailFragment fragment;
    private DonateDetailContract.Presenter presenter;

    public ImageView img_donate, img_barcode;
    private TextView tv_barcode_number, tv_product_name, tv_limit_product_name, tv_type_name_spec_name, tv_RowNo, tv_TotalNumber, tv_eticket_due_date;
    private ImageButton btn_close, btn_left_arrow, btn_right_arrow;
    private Button btn_donatedetail2, btn_deliver;
    private TextView tv_left_arrow, tv_right_arrow;
    private ConstraintLayout layout_left_arrow, layout_right_arrow;

    private int uid = 0;
    private List<Donate> productDetail = null;
    private String ticket_code = "", sProductID = "", sSpecID = "", sGiveDate = "", sCode = "",eticket_shipping = "";
    private int brightnessNow = 0;
    private boolean donateflag = true;

    public static DonateDetailFragment getInstance(int uid) {
        if (fragment == null) {
            fragment = new DonateDetailFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putInt(UID, uid);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_donate_detail, container, false);
        if (getArguments() != null) {
            uid = getArguments().getInt(UID, 0);
            Log.e(TAG, uid + "");
            initView(view);
        }
        initView(view);
        return view;
    }

    private void initView(View view) {
        ((MainActivity) getActivity()).setTopBarVisibility(false);
        ((MainActivity) getActivity()).setAppToolbarVisibility(false);

        ((MainActivity) getActivity()).setAppTitle(R.string.tab_donate);

        ((MainActivity) getActivity()).setBackButtonVisibility(false);
        ((MainActivity) getActivity()).setSortButtonVisibility(false);
        ((MainActivity) getActivity()).setMessageButtonVisibility(false);
        ((MainActivity) getActivity()).setMailButtonVisibility(false);
        ((MainActivity) getActivity()).setMainIndexMailUnreadVisibility(false);
        ((MainActivity) getActivity()).setCartBadgeVisibility(true);
        // 亮度
        brightnessNow = getSystemBrightness();
        changeAppBrightness(255);

        img_donate = view.findViewById(R.id.img_donate);
        //img_barcode = view.findViewById(R.id.img_barcode);
        tv_barcode_number = view.findViewById(R.id.tv_barcode_number);
        tv_product_name = view.findViewById(R.id.tv_product_name);
        tv_limit_product_name = view.findViewById(R.id.tv_limit_product_name);
        //tv_type_name_spec_name = view.findViewById(R.id.tv_type_name_spec_name);
        //tv_spec_name = view.findViewById(R.id.tv_spec_name);
        tv_RowNo = view.findViewById(R.id.tv_RowNo);
        tv_TotalNumber = view.findViewById(R.id.tv_TotalNumber);
        btn_close = view.findViewById(R.id.btn_close);
        btn_donatedetail2 = view.findViewById(R.id.btn_donatedetail2);
        btn_deliver = view.findViewById(R.id.btn_deliver);
        layout_left_arrow = view.findViewById(R.id.layout_left_arrow);
        layout_right_arrow = view.findViewById(R.id.layout_right_arrow);
        tv_eticket_due_date = view.findViewById(R.id.tv_eticket_due_date);

        btn_left_arrow = view.findViewById(R.id.btn_left_arrow);
        btn_right_arrow = view.findViewById(R.id.btn_right_arrow);
        tv_left_arrow = view.findViewById(R.id.tv_left_arrow);
        tv_right_arrow = view.findViewById(R.id.tv_right_arrow);

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        btn_donatedetail2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(donateflag) {
                    getActivity().getSupportFragmentManager().popBackStack();

                    ((MainActivity) getActivity()).addFragment(DonateDetail2Fragment.getInstance(uid));
                }else{
                    final FragmentManager manager = getActivity().getSupportFragmentManager();
                    new androidx.appcompat.app.AlertDialog.Builder(fragment.getActivity()).setTitle(R.string.dialog_hint).setMessage("受贈提貨券不可再做轉贈")
                            .setPositiveButton(R.string.verify, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                }
            }
         });

        btn_deliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if(eticket_shipping != null && eticket_shipping.equals("Y")){
                    goWebViewCart();
                }else{
                     new androidx.appcompat.app.AlertDialog.Builder(fragment.getActivity()).setTitle(R.string.dialog_hint).setMessage("此商品尚未開放轉出貨")
                             .setPositiveButton(R.string.verify, new DialogInterface.OnClickListener() {
                                 @Override
                                 public void onClick(DialogInterface dialog, int which) {

                                 }
                             }).show();
                }
            }
        });

        layout_left_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sNewUID = v.getTag(v.getId()).toString();
                if (!sNewUID.isEmpty()) {
                    int iNewUID = Integer.parseInt(sNewUID);
                    uid = iNewUID;
                    getInstance(iNewUID);
                    presenter.getDonateDetailByID(sNewUID);
//                    final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//                    ft.replace(R.id.fragment_location, fragment);
//                    ft.commit();
//                    getActivity().getSupportFragmentManager().executePendingTransactions();

                }
            }
        });

        layout_right_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sNewUID = v.getTag(v.getId()).toString();
                if (!sNewUID.isEmpty()) {
                    int iNewUID = Integer.parseInt(sNewUID);
                    uid = iNewUID;
                    getInstance(iNewUID);
                    presenter.getDonateDetailByID(sNewUID);
//                    final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//                    ft.detach(fragment);
//                    ft.attach(fragment);
//                    ft.commitNow();
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();

        changeAppBrightness(brightnessNow);
    }

    @Override
    public void setDonateDetail(List<Donate> productDetail) {
        this.productDetail = productDetail;
        //Glide.with(DonateDetailFragment.getInstance(uid)).load(EOrderApplication.sApiUrl + productDetail.get(0).getPictureUrl()).into(img_donate);

        ticket_code = productDetail.get(0).getTicketCode();
        sProductID = productDetail.get(0).getproduct_id();
        sSpecID = productDetail.get(0).getspec_id();
        sGiveDate = productDetail.get(0).getgive_date();
        sCode = productDetail.get(0).getTicketCode();
        eticket_shipping = productDetail.get(0).getTicketShipping();

        if (ticket_code != null && !ticket_code.equals("")) {
            img_donate.setBackgroundColor(Color.WHITE);

            generateQRCode(ticket_code);
        }

//        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
//        try {
//            BitMatrix bitMatrix = multiFormatWriter.encode(ticket_code, BarcodeFormat.CODE_39, 400, 100);
//            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
//            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
//            img_barcode.setImageBitmap(bitmap);
//        } catch (WriterException e) {
//            e.printStackTrace();
//        }

        if (productDetail.get(0).getTicketStatus().equals("R")) {
            donateflag = false;
            btn_donatedetail2.setBackgroundResource(R.drawable.donate_btnbg_4);
        }else{
            donateflag = true;
            btn_donatedetail2.setBackgroundResource(R.drawable.donate_btnbg_1);
        }
        if(eticket_shipping != null && eticket_shipping.equals("Y")){
            btn_deliver.setBackgroundResource(R.drawable.donate_btnbg_3);
        }else{
            btn_deliver.setBackgroundResource(R.drawable.donate_btnbg_4);
        }


        tv_barcode_number.setText(ticket_code);
        tv_product_name.setText(productDetail.get(0).getProductName());
        tv_limit_product_name.setText(productDetail.get(0).getLimitProductName());
        //tv_type_name_spec_name.setText(" ( " + productDetail.get(0).getTypeName() + " - " + productDetail.get(0).getSpecName() + " ) ");
        //tv_spec_name.setText(productDetail.get(0).getSpecName());
        tv_RowNo.setText(productDetail.get(0).getRowNo());
        tv_TotalNumber.setText(productDetail.get(0).getTotalNumber());
        tv_eticket_due_date.setText(productDetail.get(0).getEticketDueDate());
        if(productDetail.get(0).getLastUid().equals("")){
            btn_left_arrow.setVisibility(View.INVISIBLE);
            tv_left_arrow.setVisibility(View.INVISIBLE);
        }else{
            btn_left_arrow.setVisibility(View.VISIBLE);
            tv_left_arrow.setVisibility(View.VISIBLE);
            layout_left_arrow.setTag(R.id.layout_left_arrow, productDetail.get(0).getLastUid());
        }
        if(productDetail.get(0).getNextUid().equals("")){
            btn_right_arrow.setVisibility(View.INVISIBLE);
            tv_right_arrow.setVisibility(View.INVISIBLE);
        }else{
            btn_right_arrow.setVisibility(View.VISIBLE);
            tv_right_arrow.setVisibility(View.VISIBLE);
            layout_right_arrow.setTag(R.id.layout_right_arrow, productDetail.get(0).getNextUid());
        }

        btn_donatedetail2.setEnabled(true);
    }

    @Override
    public void intentToLogin(int requestCode) {
        Intent intent = new Intent(fragment.getActivity(), LoginActivity.class);
        fragment.getActivity().startActivityForResult(intent, requestCode);
    }

    @Override
    public void showErrorAlert(String message) {
        new AlertDialog.Builder(fragment.getActivity()).setTitle(R.string.dialog_hint).setMessage(message).setPositiveButton(android.R.string.ok, null).show();
        ((MainActivity) getActivity()).refreshBadge();
    }

    @Override
    public void goBack() {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new DonateDetailPresenter(this, getRepositoryManager(getContext()));
        presenter.getDonateDetailByID(Integer.toString(uid));
    }

    private int getSystemBrightness() {
        int systemBrightness = 0;
        try {
            systemBrightness = Settings.System.getInt(getActivity().getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return systemBrightness;
    }

    public void changeAppBrightness(int brightness) {
        Window window = getActivity().getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        if (brightness == -1) {
            lp.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
        } else {
            lp.screenBrightness = (brightness <= 0 ? 1 : brightness) / 255f;
        }
        window.setAttributes(lp);
    }

    private void generateQRCode(String content) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H); // 设置纠错级别为最高
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 300, 300, hints);

            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();

            Bitmap qrBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    qrBitmap.setPixel(x, y, bitMatrix.get(x, y) ? getResources().getColor(R.color.Black) : getResources().getColor(R.color.white));
                }
            }

            img_donate.setImageBitmap(qrBitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    public void goWebViewCart(){
        EOrderApplication.DeliverCodeUid = uid + "";
        ((MainActivity) getActivity()).addFragment(DeliverCartFragment.getInstance(sCode));
    }
}

