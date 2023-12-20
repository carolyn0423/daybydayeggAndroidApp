package com.hamels.daybydayegg.Donate.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hamels.daybydayegg.Base.BaseFragment;
import com.hamels.daybydayegg.Donate.Adapter.OftenMobileAdapter;
import com.hamels.daybydayegg.Donate.Presenter.DonateDetailPresenter;
import com.hamels.daybydayegg.EOrderApplication;
import com.hamels.daybydayegg.Login.VIew.LoginActivity;
import com.hamels.daybydayegg.Main.View.MainActivity;
import com.hamels.daybydayegg.Donate.Contract.DonateDetailContract;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.Donate;
import com.hamels.daybydayegg.Repository.Model.OftenMobile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DonateDetail2Fragment extends BaseFragment implements DonateDetailContract.View{
    public static final String TAG = DonateDetailFragment.class.getSimpleName();
    private static final String UID = "uid";
    private static DonateDetail2Fragment fragment;
    private DonateDetailContract.Presenter presenter;

    protected final int REQUEST_ADDRESSBOOK = 11;

    private ScrollView sv_scrollview;
    private ImageView img_donate;
    private TextView tv_product_name, tv_type_name_spec_name, tv_eticket_due_date;
    private EditText edit_num, edit_phone, edit_nick;
    private Button btn_close, btn_minus, btn_plus, btn_submit;
    private Spinner spinner;
    private int uid = 0;
    private int left_number = 0;
    private int quantity = 0;
    private String SpnnerText = "從常用資料選擇";

    public static DonateDetail2Fragment getInstance(int uid) {
        if (fragment == null) {
            fragment = new DonateDetail2Fragment();
        }
        Bundle bundle = new Bundle();
        bundle.putInt(UID, uid);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_donate_detail2, container, false);
        if (getArguments() != null) {
            uid = getArguments().getInt(UID, 0);
            Log.e(TAG, uid +"");
            initView(view);
        }
        initView(view);
        return view;
    }

    private void initView(View view) {
        ((MainActivity) getActivity()).setTopBarVisibility(false);
        ((MainActivity) getActivity()).setAppToolbarVisibility(true);

        ((MainActivity) getActivity()).setAppTitle(R.string.tab_donate);

        ((MainActivity) getActivity()).setBackButtonVisibility(true);
        ((MainActivity) getActivity()).setSortButtonVisibility(false);
        ((MainActivity) getActivity()).setMessageButtonVisibility(false);
        ((MainActivity) getActivity()).setMailButtonVisibility(false);
        ((MainActivity) getActivity()).setMainIndexMessageUnreadVisibility(false);
        ((MainActivity) getActivity()).setCartBadgeVisibility(true);
        sv_scrollview = view.findViewById(R.id.ScrollView1);

        img_donate = view.findViewById(R.id.img_donate);
        tv_product_name = view.findViewById(R.id.tv_product_name);
        tv_type_name_spec_name = view.findViewById(R.id.tv_type_name_spec_name);
        tv_eticket_due_date = view.findViewById(R.id.tv_eticket_due_date);
        //tv_spec_name = view.findViewById(R.id.tv_spec_name);
        btn_close = view.findViewById(R.id.btn_close);
        btn_minus = view.findViewById(R.id.btn_minus);
        btn_plus = view.findViewById(R.id.btn_plus);
        edit_num = view.findViewById(R.id.edit_num);
        edit_phone = view.findViewById(R.id.edit_phone);
        edit_nick = view.findViewById(R.id.edit_nick);
        //  btn_addressbook = view.findViewById(R.id.btn_addressbook);
        btn_submit = view.findViewById(R.id.btn_submit);
        spinner = view.findViewById(R.id.spinnerDropdown);

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity = Integer.parseInt(edit_num.getText().toString());
                if(quantity > 0){
                    quantity--;
                }
                edit_num.setText(Integer.toString(quantity));
            }
        });

        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity = Integer.parseInt(edit_num.getText().toString());
                if (quantity < left_number) {
                    quantity++;
                }
                edit_num.setText(Integer.toString(quantity));
            }
        });

//        btn_addressbook.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setAction("android.intent.action.PICK");
//                intent.addCategory("android.intent.category.DEFAULT");
//                intent.setType("vnd.android.cursor.dir/phone_v2");
//                startActivityForResult(intent, REQUEST_ADDRESSBOOK);
//            }
//        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.SaveTicketData(edit_phone.getText().toString(), Integer.toString(uid), edit_num.getText().toString(), edit_nick.getText().toString());
            }
        });

        presenter = new DonateDetailPresenter(this, getRepositoryManager(getContext()));
        HashMap<String, String> oftenMobileMap = new HashMap<>();
        oftenMobileMap = presenter.getOftenMobile();

        List<OftenMobile> oftenMobileList = new ArrayList<>();
        oftenMobileList.add(0, new OftenMobile("", SpnnerText));
        // 从您的数据源中获取 OftenMobile 对象并添加到 oftenMobileList 中
        for (Map.Entry<String, String> entry : oftenMobileMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            OftenMobile oftenMobile = new OftenMobile(key, value);
            oftenMobileList.add(oftenMobile);
        }

        OftenMobileAdapter adapter = new OftenMobileAdapter(getContext(), android.R.layout.simple_spinner_item, oftenMobileList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 當選項被選中時呼叫此方法
                String selectedItem = parent.getItemAtPosition(position).toString();
                // 使用 selectedItem 進行您想要的操作，比如顯示在日誌中或者更新 UI 等
                if(!selectedItem.equals(SpnnerText)){
                    String[] vItem = selectedItem.split("-");
                    edit_phone.setText(vItem[0].trim());
                    if(vItem.length > 1) edit_nick.setText(vItem[1].trim());
                    else edit_nick.setText("");
                }else{
                    edit_phone.setText("");
                    edit_nick.setText("");
                }
                Log.d("Selected Item", selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 當沒有選項被選中時呼叫此方法
            }
        });
    }

    @Override
    public void setDonateDetail(List<Donate> productDetail) {
        Log.e(TAG, Integer.toString(uid));
        String sPictureUrl = productDetail.get(0).getPictureUrl() == null || productDetail.get(0).getPictureUrl().equals("") ? EOrderApplication.DEFAULT_PICTURE_URL : productDetail.get(0).getPictureUrl();

        Glide.with(DonateDetail2Fragment.getInstance(uid)).load(EOrderApplication.sApiUrl + sPictureUrl).into(img_donate);
        tv_product_name.setText(productDetail.get(0).getProductName());
        tv_type_name_spec_name.setText(" ( " + productDetail.get(0).getTypeName() + " - " + productDetail.get(0).getSpecName() + " ) ");
        tv_eticket_due_date.setText(productDetail.get(0).getEticketDueDate());
        //tv_spec_name.setText(productDetail.get(0).getSpecName());
        edit_num.setText("1");
        edit_phone.setText("");
        edit_nick.setText("");

        spinner.setSelection(0);

        left_number = Integer.parseInt(productDetail.get(0).getLeftNumber());
    }

    @Override
    public void intentToLogin(int requestCode) {
        Intent intent = new Intent(fragment.getActivity(), LoginActivity.class);
        fragment.getActivity().startActivityForResult(intent, requestCode);
    }

    @Override
    public void showErrorAlert(String message) {
        new AlertDialog.Builder(fragment.getActivity()).setTitle(R.string.dialog_hint).setMessage(message).setPositiveButton(android.R.string.ok, null).show();
        edit_num.setText("1");
        edit_phone.setText("");
        ((MainActivity) getActivity()).refreshBadge();
    }

    @Override
    public void goBack() {
        getActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new DonateDetailPresenter(this, getRepositoryManager(getContext()));
        presenter.getDonateDetailByID(Integer.toString(uid));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case (REQUEST_ADDRESSBOOK) :
                if (resultCode == Activity.RESULT_OK) {
                    Uri uri = data.getData();
                    String phoneNum = null;
                    ContentResolver contentResolver = getActivity().getContentResolver();
                    Cursor cursor = null;
                    if (uri != null) {
                        cursor = contentResolver.query(uri, new String[]{"display_name", "data1"}, null, null, null);
                    }
                    while (cursor.moveToNext()) {
                        phoneNum = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        edit_phone.setText(phoneNum);
                    }
                }
                break;
        }
    }
}

