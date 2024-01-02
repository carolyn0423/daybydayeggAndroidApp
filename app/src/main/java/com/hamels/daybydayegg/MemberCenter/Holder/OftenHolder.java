package com.hamels.daybydayegg.MemberCenter.Holder;

import android.graphics.Paint;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.hamels.daybydayegg.Donate.Adapter.OftenMobileAdapter;
import com.hamels.daybydayegg.MemberCenter.Adapter.AddressAdapter;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.Address;
import com.hamels.daybydayegg.Repository.Model.Often;
import com.hamels.daybydayegg.Repository.Model.OftenMobile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OftenHolder extends RecyclerView.ViewHolder {
    public ConstraintLayout clItemStoreList, btn_save, btn_delete;

    //  常用手機號
    public TextView tv_nick_name, tv_mobile;
    public TextInputLayout layout_nick_name, layout_mobile;
    public EditText et_nick_name, et_mobile;
    //  常用地址
    public Spinner spinnerCity, spinnerArea;
    public TextInputLayout layout_addr_name, layout_address;
    public EditText et_addr_name, et_address;
    //  常用統編
    public TextView tv_title, tv_vat_number;
    public TextInputLayout layout_title, layout_vat_number;
    public EditText et_title, et_vat_number;

    public OftenHolder(@NonNull View itemView) {
        super(itemView);
        clItemStoreList = itemView.findViewById(R.id.item_often);
        btn_save = itemView.findViewById(R.id.btn_save);
        btn_delete = itemView.findViewById(R.id.btn_delete);

        tv_nick_name = itemView.findViewById(R.id.tv_nick_name);
        tv_mobile = itemView.findViewById(R.id.tv_mobile);
        layout_nick_name = itemView.findViewById(R.id.layout_nick_name);
        layout_mobile = itemView.findViewById(R.id.layout_mobile);
        et_nick_name = itemView.findViewById(R.id.et_nick_name);
        et_mobile = itemView.findViewById(R.id.et_mobile);

        layout_addr_name = itemView.findViewById(R.id.layout_addr_name);
        et_addr_name = itemView.findViewById(R.id.et_addr_name);
        spinnerCity = itemView.findViewById(R.id.spinnerCity);
        spinnerArea = itemView.findViewById(R.id.spinnerArea);
        layout_address = itemView.findViewById(R.id.layout_address);
        et_address = itemView.findViewById(R.id.et_address);

        tv_title = itemView.findViewById(R.id.tv_title);
        tv_vat_number = itemView.findViewById(R.id.tv_vat_number);
        layout_title = itemView.findViewById(R.id.layout_title);
        layout_vat_number = itemView.findViewById(R.id.layout_vat_number);
        et_title = itemView.findViewById(R.id.et_title);
        et_vat_number = itemView.findViewById(R.id.et_vat_number);
    }

    public void setMobile(Often often) {
        tv_nick_name.setVisibility(View.VISIBLE);
        tv_mobile.setVisibility(View.VISIBLE);
        layout_nick_name.setVisibility(View.VISIBLE);
        layout_mobile.setVisibility(View.VISIBLE);
        et_nick_name.setVisibility(View.VISIBLE);
        et_mobile.setVisibility(View.VISIBLE);

        layout_addr_name.setVisibility(View.GONE);
        et_addr_name.setVisibility(View.GONE);
        spinnerCity.setVisibility(View.GONE);
        spinnerArea.setVisibility(View.GONE);
        layout_address.setVisibility(View.GONE);
        et_address.setVisibility(View.GONE);

        tv_title.setVisibility(View.GONE);
        tv_vat_number.setVisibility(View.GONE);
        layout_title.setVisibility(View.GONE);
        layout_vat_number.setVisibility(View.GONE);
        et_title.setVisibility(View.GONE);
        et_vat_number.setVisibility(View.GONE);

        et_nick_name.setText(often.getNickName());
        et_mobile.setText(often.getMobile());
    }

    public void setAddress(Often often) {
        tv_nick_name.setVisibility(View.GONE);
        tv_mobile.setVisibility(View.GONE);
        layout_nick_name.setVisibility(View.GONE);
        layout_mobile.setVisibility(View.GONE);
        et_nick_name.setVisibility(View.GONE);
        et_mobile.setVisibility(View.GONE);

        layout_addr_name.setVisibility(View.VISIBLE);
        et_addr_name.setVisibility(View.VISIBLE);
        spinnerCity.setVisibility(View.VISIBLE);
        spinnerArea.setVisibility(View.VISIBLE);
        layout_address.setVisibility(View.VISIBLE);
        et_address.setVisibility(View.VISIBLE);

        tv_title.setVisibility(View.GONE);
        tv_vat_number.setVisibility(View.GONE);
        layout_title.setVisibility(View.GONE);
        layout_vat_number.setVisibility(View.GONE);
        et_title.setVisibility(View.GONE);
        et_vat_number.setVisibility(View.GONE);

        String vCity[] = often.getCityList().split(",");
        int indexCity = 0;
        List<Address> vCityList = new ArrayList<>();

        if(vCity != null) {
            for (int i = 0; i < vCity.length; i++) {
                if (!vCity[i].isEmpty()) {
                    Address oCity = new Address(vCity[i], "");
                    vCityList.add(oCity);

                    if (vCity[i].equals(often.getCityCode())) indexCity = i;
                }
            }
        }

        AddressAdapter CityAdapter = new AddressAdapter(spinnerCity.getContext(), android.R.layout.simple_spinner_item, vCityList);
        CityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCity.setAdapter(CityAdapter);


        spinnerCity.setSelection(indexCity);
        et_address.setText(often.getAddress());
        et_addr_name.setText(often.getAddrName());
    }

    public void setVatNumber(Often often) {
        tv_nick_name.setVisibility(View.GONE);
        tv_mobile.setVisibility(View.GONE);
        layout_nick_name.setVisibility(View.GONE);
        layout_mobile.setVisibility(View.GONE);
        et_nick_name.setVisibility(View.GONE);
        et_mobile.setVisibility(View.GONE);

        layout_addr_name.setVisibility(View.GONE);
        et_addr_name.setVisibility(View.GONE);
        spinnerCity.setVisibility(View.GONE);
        spinnerArea.setVisibility(View.GONE);
        layout_address.setVisibility(View.GONE);
        et_address.setVisibility(View.GONE);

        tv_title.setVisibility(View.VISIBLE);
        tv_vat_number.setVisibility(View.VISIBLE);
        layout_title.setVisibility(View.VISIBLE);
        layout_vat_number.setVisibility(View.VISIBLE);
        et_title.setVisibility(View.VISIBLE);
        et_vat_number.setVisibility(View.VISIBLE);

        et_title.setText(often.getVatTitle());
        et_vat_number.setText(often.getVatNumber());
    }

    public void setSpinnerArea(List<Address> vAreaList, int iIndes){
        AddressAdapter AreaAdapter = new AddressAdapter(spinnerArea.getContext(), android.R.layout.simple_spinner_item, vAreaList);
        AreaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerArea.setAdapter(AreaAdapter);

        if(iIndes != -1) spinnerArea.setSelection(iIndes);
    }
}
