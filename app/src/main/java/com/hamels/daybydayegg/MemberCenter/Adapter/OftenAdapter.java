package com.hamels.daybydayegg.MemberCenter.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hamels.daybydayegg.Base.BaseAdapter;
import com.hamels.daybydayegg.MemberCenter.Contract.OftenContract;
import com.hamels.daybydayegg.MemberCenter.Holder.OftenHolder;
import com.hamels.daybydayegg.MemberCenter.View.OftenFragment;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.Address;
import com.hamels.daybydayegg.Repository.Model.Often;

import java.util.ArrayList;
import java.util.List;

public class OftenAdapter extends BaseAdapter<OftenHolder> {
    public static final String TAG = OftenAdapter.class.getSimpleName();
    private OftenContract.View view;
    private OftenContract.Presenter presenter;
    private List<Often> oftens;
    private String FunctionName = "";

    public OftenAdapter(OftenContract.View view, OftenContract.Presenter presenter) {
        this.view = view;
        this.presenter = presenter;
        oftens = new ArrayList<>();
    }

    @NonNull
    @Override
    public OftenHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_often, viewGroup, false);
        return new OftenHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final OftenHolder oftenHolder, int position) {
        final String func = FunctionName;

        switch (func) {
            case "INVOICE":
                oftenHolder.setVatNumber(oftens.get(position));
                break;
            case "MOBILE":
                oftenHolder.setMobile(oftens.get(position));
                break;
            case "ADDRESS":
                oftenHolder.setAddress(oftens.get(position));
                break;
        }

        oftenHolder.btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = oftenHolder.getAdapterPosition();
                if (pos == RecyclerView.NO_POSITION) return;

                switch (func) {
                    case "INVOICE":
                        String sVatTitle = oftenHolder.et_title.getText().toString();
                        String sVatNumber = oftenHolder.et_vat_number.getText().toString();
                        presenter.GetFunctionSaveDataApi("UpdateOftenInvoice", oftens.get(pos).getUid(), sVatTitle, sVatNumber, "", "");
                        break;
                    case "MOBILE":
                        String sMobile = oftenHolder.et_mobile.getText().toString();
                        String sNick = oftenHolder.et_nick_name.getText().toString();
                        presenter.saveOftenMobile(sMobile, sNick, oftens.get(pos).getMobile());
                        break;
                    case "ADDRESS":
                        String sAddrName = oftenHolder.et_addr_name.getText().toString();
                        String sCityCode = oftens.get(pos).getTmpCityCode();
                        String sAreaCode = oftens.get(pos).getTmpAreaCode();
                        String sAddress = oftenHolder.et_address.getText().toString();
                        presenter.GetFunctionSaveDataApi("UpdateOftenAddress", oftens.get(pos).getUid(), sAddrName, sCityCode, sAreaCode, sAddress);
                        break;
                }
            }
        });

        oftenHolder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = oftenHolder.getAdapterPosition();
                if (pos == RecyclerView.NO_POSITION) return;

                switch (func) {
                    case "INVOICE":
                        presenter.GetFunctionSaveDataApi("DeleteOftenInvoice", oftens.get(pos).getUid(), "", "", "", "");
                        break;
                    case "MOBILE":
                        presenter.removeOftenMobile(oftens.get(pos).getMobile());
                        break;
                    case "ADDRESS":
                        presenter.GetFunctionSaveDataApi("DeleteOftenAddress", oftens.get(pos).getUid(), "", "", "", "");
                        break;
                }
            }
        });

        oftenHolder.spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position2, long id) {
                int pos = oftenHolder.getAdapterPosition();
                if (pos == RecyclerView.NO_POSITION) return;

                int iAreaIndex = -1;
                int iIndex = 0;
                String[] vCity = oftens.get(pos).getCityList().split(",");
                String sCity = vCity[position2];

                List<Address> tmpAreas = new ArrayList<>();
                for (Address address : OftenFragment.getInstance().addresses) {
                    if (address.getCity().equals(sCity)) {
                        oftens.get(pos).setTmpCityCode(sCity);
                        Address oArea = new Address("", address.getArea());
                        tmpAreas.add(oArea);

                        if (address.getArea().equals(oftens.get(pos).getAreaCode())) {
                            iAreaIndex = iIndex;
                        }
                        iIndex++;
                    }
                }

                oftenHolder.setSpinnerArea(tmpAreas, iAreaIndex);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        oftenHolder.spinnerArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position2, long id) {
                int pos = oftenHolder.getAdapterPosition();
                if (pos == RecyclerView.NO_POSITION) return;

                int iIndex = 0;
                for (Address address : OftenFragment.getInstance().addresses) {
                    if (address.getCity().equals(oftens.get(pos).getTmpCityCode())) {
                        if (position2 == iIndex) {
                            oftens.get(pos).setTmpAreaCode(address.getArea());
                            break;
                        }
                        iIndex++;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }


//    @Override
//    public void onBindViewHolder(@NonNull final OftenHolder oftenHolder, final int position) {
//        switch (FunctionName){
//            case "INVOICE":
//                oftenHolder.setVatNumber(oftens.get(position));
//                break;
//            case "MOBILE":
//                oftenHolder.setMobile(oftens.get(position));
//                break;
//            case "ADDRESS":
//                oftenHolder.setAddress(oftens.get(position));
//                break;
//        }
//
//        oftenHolder.btn_save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                switch (FunctionName){
//                    case "INVOICE":
//                        String sVatTitle = oftenHolder.et_title.getText().toString();
//                        String sVatNumber = oftenHolder.et_vat_number.getText().toString();
//
//                        presenter.GetFunctionSaveDataApi("UpdateOftenInvoice", oftens.get(position).getUid(), sVatTitle, sVatNumber, "", "");
//                        break;
//                    case "MOBILE":
//                        String sMobile = oftenHolder.et_mobile.getText().toString();
//                        String sNick = oftenHolder.et_nick_name.getText().toString();
//                        presenter.saveOftenMobile(sMobile, sNick, oftens.get(position).getMobile());
//                        break;
//                    case "ADDRESS":
//                        String sAddrName = oftenHolder.et_addr_name.getText().toString();
//                        String sCityCode = oftens.get(position).getTmpCityCode();
//                        String sAreaCode = oftens.get(position).getTmpAreaCode();
//                        String sAddress = oftenHolder.et_address.getText().toString();
//
//                        presenter.GetFunctionSaveDataApi("UpdateOftenAddress", oftens.get(position).getUid(), sAddrName, sCityCode, sAreaCode, sAddress);
//                        break;
//                }
//            }
//        });
//
//        oftenHolder.btn_delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                switch (FunctionName){
//                    case "INVOICE":
//                        presenter.GetFunctionSaveDataApi("DeleteOftenInvoice", oftens.get(position).getUid(), "", "", "", "");
//                        break;
//                    case "MOBILE":
//                        presenter.removeOftenMobile(oftens.get(position).getMobile());
//                        break;
//                    case "ADDRESS":
//                        presenter.GetFunctionSaveDataApi("DeleteOftenAddress", oftens.get(position).getUid(), "", "", "", "");
//                        break;
//                }
//            }
//        });
//
//        oftenHolder.spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position2, long id) {
//                // 在此處處理所選擇的縣市
//                int iAreaIndex = -1;
//                int iIndex = 0;
//                String vCity[] = oftens.get(position).getCityList().split(",");
//                String sCity = vCity[position2];
//                // 執行相應的操作
//                List<Address> tmpAreas = new ArrayList<>();
//                for(Address address : OftenFragment.getInstance().addresses){
//                    if(address.getCity().equals(sCity)){
//                        oftens.get(position).setTmpCityCode(sCity);
//                        Address oArea = new Address("", address.getArea());
//                        tmpAreas.add(oArea);
//                        if(address.getArea().equals(oftens.get(position).getAreaCode())){
//                            iAreaIndex = iIndex;
//                        }
//                        iIndex++;
//                    }
//                }
//
//                oftenHolder.setSpinnerArea(tmpAreas, iAreaIndex);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                // 當沒有選擇時的處理邏輯
//            }
//        });
//
//        oftenHolder.spinnerArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position2, long id) {
//                // 執行相應的操作
//                int iIndex = 0;
//                for(Address address : OftenFragment.getInstance().addresses){
//                    if(address.getCity().equals(oftens.get(position).getTmpCityCode())){
//                        if(position2 == iIndex){
//                            oftens.get(position).setTmpAreaCode(address.getArea());
//                            break;
//                        }
//                        iIndex++;
//                    }
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                // 當沒有選擇時的處理邏輯
//            }
//        });
//    }

    @Override
    public int getItemCount() {
        return oftens.size();
    }

    public void setData(String sFunctionName, List<Often> oftens) {
        this.FunctionName = sFunctionName;
        this.oftens = oftens;
        notifyDataSetChanged();
    }
}
