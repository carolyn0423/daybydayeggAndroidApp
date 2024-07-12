package com.hamels.daybydayegg.Donate.View;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.hamels.daybydayegg.Base.BaseFragment;
import com.hamels.daybydayegg.Donate.Adapter.DonateCartAdapter;
import com.hamels.daybydayegg.Donate.Contract.DonateCartContract;
import com.hamels.daybydayegg.Donate.Presenter.DonateCartPresenter;
import com.hamels.daybydayegg.EOrderApplication;
import com.hamels.daybydayegg.Login.VIew.LoginActivity;
import com.hamels.daybydayegg.Main.View.MainActivity;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.DonateCart;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.List;
import java.util.UUID;

public class DonateCartFragment extends BaseFragment implements DonateCartContract.View {
    public static final String TAG = DonateCartFragment.class.getSimpleName();

    private static DonateCartFragment fragment;
    private DonateCartContract.Presenter donateCartPresenter;
    private RecyclerView recyclerView;
    private DonateCartAdapter donateCartAdapter;
    private TabLayout tabLayout;
    private Button btn_alldonate, btn_deliver;
    private TextView tv_product_cnt;

    private String type_id = "0";

    // qrcode
    private PopupWindow popupWindow;
    private ImageView dialog_img_qrcode;
    private int barcodeWidth = 350;
    private int barcodeHeight = 350;
    private int brightnessNow = 0;
    private boolean donateflag = true;
    private boolean isAllowToShipping = false;

    public static DonateCartFragment getInstance() {
        if (fragment == null) {
            fragment = new DonateCartFragment();
        }
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView");

        View view = inflater.inflate(R.layout.fragment_donate_cart1, container, false);

        initView(view);

        initData(view);

        return view;
    }

    private void initView(final View view) {
        Log.e(TAG, "initView");

        ((MainActivity) getActivity()).setTopBarVisibility(false);
        ((MainActivity) getActivity()).setAppToolbarVisibility(true);

        ((MainActivity) getActivity()).setAppTitle(R.string.tab_donate);

        ((MainActivity) getActivity()).setBackButtonVisibility(true);
        ((MainActivity) getActivity()).setSortButtonVisibility(false);
        ((MainActivity) getActivity()).setMessageButtonVisibility(true);
        ((MainActivity) getActivity()).setMailButtonVisibility(true);
        ((MainActivity) getActivity()).setMainIndexMessageUnreadVisibility(false);
        ((MainActivity) getActivity()).setCartBadgeVisibility(true);
        tabLayout = view.findViewById(R.id.tab_layout);
        recyclerView = view.findViewById(R.id.donate_recycler_view);
        btn_alldonate = view.findViewById(R.id.btn_alldonate);
        btn_deliver = view.findViewById(R.id.btn_deliver);
        //btn_submit = view.findViewById(R.id.btn_submit);
        tv_product_cnt = view.findViewById(R.id.tv_product_cnt);

        btn_alldonate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!donateflag) {
                    final FragmentManager manager = getActivity().getSupportFragmentManager();
                    new androidx.appcompat.app.AlertDialog.Builder(fragment.getActivity()).setTitle("受贈提貨券不可再做轉贈").setMessage("請先移除受贈的提貨券")
                            .setPositiveButton(R.string.verify, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                }else{
                    ((MainActivity) getActivity()).addFragment(DonateCart2Fragment.getInstance());
                }
            }
        });

        btn_deliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EOrderApplication.DeliverCodeUid = "";
                if(donateflag){
                    ((MainActivity) getActivity()).addFragment(DeliverCartFragment.getInstance(""));
                }else{
                    new androidx.appcompat.app.AlertDialog.Builder(fragment.getActivity()).setTitle("部份商品不開放轉出貨").setMessage("請先移除尚未開放轉出貨的提貨券商品")
                            .setPositiveButton(R.string.verify, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                }

            }
        });

//        btn_submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new AlertDialog.Builder(fragment.getActivity()).setTitle(R.string.dialog_hint).setMessage("確定要一次兌換" + tv_product_cnt.getText() + "個商品嗎？")
//                        .setPositiveButton(R.string.verify, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                                String cart_ticket_code = UUID.randomUUID().toString();
//                                donateCartPresenter.updateTicketCartCode(cart_ticket_code);
//
//                                View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_qrcode, null);
//
//                                popupWindow = new PopupWindow(view);
//                                popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
//                                popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
//
//                                dialog_img_qrcode = view.findViewById(R.id.dialog_img_qrcode);
//
//                                Log.e(TAG, "----------" + cart_ticket_code + "----------");
//                                createBarcodeImage(cart_ticket_code);
//
//                                Button dialog_btn_close = view.findViewById(R.id.dialog_btn_close);
//                                dialog_btn_close.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View view) {
//                                        popupWindow.dismiss();
//                                        changeAppBrightness(brightnessNow);
//                                    }
//                                });
//
//                                popupWindow.showAtLocation(view, Gravity.CENTER_HORIZONTAL, 0, 0);
//
//                            }
//                        })
//                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                // User cancelled the dialog
//                            }
//                        }).show();
//            }
//        });
    }

    private void initData(View view) {
        Log.e(TAG, "initData");

        donateCartPresenter = new DonateCartPresenter(this, getRepositoryManager(getContext()));
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setStackFromEnd(false);

        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);

        donateCartAdapter = new DonateCartAdapter(this, donateCartPresenter, R.layout.item_donatecart1);
        recyclerView.setAdapter(donateCartAdapter);

        donateCartPresenter.getDonateCart();
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onViewCreated");

        super.onViewCreated(view, savedInstanceState);

        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                type_id = Integer.toString(tab.getPosition());

                switch (type_id) {
                    case "0":
                        initData(view);
                        break;
                    case "1":
                        DonateFragment.getInstance().type_idMode("1");
                        ((MainActivity) getActivity()).addFragment(DonateFragment.getInstance());
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    @Override
    public void setDonateCart(List<DonateCart> donateList) {
//        for (int i = 0; i < donateList.size(); i++) {
//            Log.e(TAG, donateList.get(i).getProductName());
//        }

        donateCartAdapter.setDonate(donateList);
        int iCartShippingAllowCount = 0;
        int total_cart_count = 0;
        donateflag = true;
        for (int i = 0; i < donateList.size(); i++) {
            total_cart_count += Integer.parseInt(donateList.get(i).getcart_count());
            if (donateList.get(i).getTicketStatus().equals("R")) {
                donateflag = false;
            }
            if (donateList.get(i).getTicketShipping().equals("Y")){
                iCartShippingAllowCount +=1;
            }
        }
        tv_product_cnt.setText(Integer.toString(total_cart_count));

        recyclerView.scrollToPosition(0);

        if (0 >= donateCartAdapter.getItemCount()) {
            btn_alldonate.setEnabled(false);
            btn_alldonate.setAlpha(.5f);
            //btn_submit.setEnabled(false);
            //btn_submit.setAlpha(.5f);
        } else {
            btn_alldonate.setEnabled(true);
            btn_alldonate.setAlpha(1f);
            //btn_submit.setEnabled(true);
            btn_alldonate.setAlpha(1f);
        }

        if(!donateflag){
            btn_alldonate.setBackground(new ColorDrawable(getResources().getColor(R.color.darkGreyLine)));
        }else{
            btn_alldonate.setBackground(new ColorDrawable(getResources().getColor(R.color.colorYunlinhn)));
        }

        if(iCartShippingAllowCount == donateList.size()){
            isAllowToShipping = true;
            btn_deliver.setBackground(new ColorDrawable(getResources().getColor(R.color.orangeText)));
            btn_deliver.setEnabled(true);
        }else{
            isAllowToShipping = false;
            btn_deliver.setBackground(new ColorDrawable(getResources().getColor(R.color.darkGreyLine)));
            btn_deliver.setEnabled(false);
        }
    }

    @Override
    public void goBack() {

    }

    public void intentToLogin(int requestCode) {
        Intent intent = new Intent(fragment.getActivity(), LoginActivity.class);
        fragment.getActivity().startActivityForResult(intent, requestCode);
    }

    @Override
    public void showErrorAlert(String message) {

    }

    private void createBarcodeImage(String barcodeNum) {
        if (barcodeNum != null && !barcodeNum.equals("")) {
            dialog_img_qrcode.setBackgroundColor(Color.WHITE);
            if (isAdded()) {
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try {
                    BitMatrix bitMatrix = multiFormatWriter.encode(barcodeNum, BarcodeFormat.QR_CODE, barcodeWidth, barcodeHeight);
                    int height = bitMatrix.getHeight();
                    int width = bitMatrix.getWidth();
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    for (int x = 0; x < width; x++) {
                        for (int y = 0; y < height; y++) {
                            bitmap.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                        }
                    }
                    dialog_img_qrcode.setImageBitmap(bitmap);

                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }

            brightnessNow = getSystemBrightness();
            changeAppBrightness(255);
        }
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
}