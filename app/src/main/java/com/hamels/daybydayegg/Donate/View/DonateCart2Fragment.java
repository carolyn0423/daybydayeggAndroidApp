package com.hamels.daybydayegg.Donate.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hamels.daybydayegg.Base.BaseFragment;
import com.hamels.daybydayegg.Donate.Adapter.DonateCartAdapter;
import com.hamels.daybydayegg.Donate.Contract.DonateCartContract;
import com.hamels.daybydayegg.Donate.Presenter.DonateCartPresenter;
import com.hamels.daybydayegg.Login.VIew.LoginActivity;
import com.hamels.daybydayegg.Main.View.MainActivity;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.DonateCart;

import java.util.List;

public class DonateCart2Fragment extends BaseFragment implements DonateCartContract.View{
    public static final String TAG = DonateCart2Fragment.class.getSimpleName();

    protected final int REQUEST_ADDRESSBOOK = 11;

    private static DonateCart2Fragment fragment;
    private DonateCartContract.Presenter donateCartPresenter;
    private RecyclerView recyclerView;
    private DonateCartAdapter donateCartAdapter;
    private EditText edit_phone;
    private Button btn_close, btn_addressbook, btn_submit;
    private TextView tv_product_cnt;

    public static DonateCart2Fragment getInstance() {
        if (fragment == null) {
            fragment = new DonateCart2Fragment();
        }
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView");

        View view = inflater.inflate(R.layout.fragment_donate_cart2, container, false);

        initView(view);

        initData(view);

        return view;
    }

    private void initView(View view) {
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
        btn_close = view.findViewById(R.id.btn_close);
        recyclerView = view.findViewById(R.id.donate_recycler_view);
        edit_phone = view.findViewById(R.id.edit_phone);
        btn_addressbook = view.findViewById(R.id.btn_addressbook);
        btn_submit = view.findViewById(R.id.btn_submit);
        tv_product_cnt = view.findViewById(R.id.tv_product_cnt);

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        btn_addressbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.PICK");
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setType("vnd.android.cursor.dir/phone_v2");
                startActivityForResult(intent, REQUEST_ADDRESSBOOK);
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                donateCartPresenter.GiveTicketGiftByCart(edit_phone.getText().toString());
            }
        });
    }

    private void initData(View view) {
        Log.e(TAG, "initData");

        donateCartPresenter = new DonateCartPresenter(this, getRepositoryManager(getContext()));
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setStackFromEnd(false);

        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);

        donateCartAdapter = new DonateCartAdapter(this, donateCartPresenter, R.layout.item_donatecart2);
        recyclerView.setAdapter(donateCartAdapter);

        donateCartPresenter.getDonateCart();
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onViewCreated");

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void setDonateCart(List<DonateCart> donateList) {
//        for (int i = 0; i < donateList.size(); i++) {
//            Log.e(TAG, donateList.get(i).getProductName());
//        }

        boolean donateflag = true;

        for(int i = 0 ; i < donateList.size() ; i++){
            if (donateList.get(i).getTicketStatus().equals("R")) {
                donateflag = false;
            }
        }

        if (!donateflag) {
            final FragmentManager manager = getActivity().getSupportFragmentManager();
            new androidx.appcompat.app.AlertDialog.Builder(fragment.getActivity()).setTitle(R.string.dialog_hint).setMessage("受贈提貨券不可再做轉贈")
                    .setPositiveButton(R.string.verify, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            manager.popBackStack();
                        }
                    }).show();
        }

        donateCartAdapter.setDonate(donateList);

        int total_cart_count = 0;
        for(int i = 0 ; i < donateList.size() ; i++){
            total_cart_count += Integer.parseInt(donateList.get(i).getcart_count());
        }
        tv_product_cnt.setText(Integer.toString(total_cart_count));

        recyclerView.scrollToPosition(0);
    }

    public void intentToLogin(int requestCode) {
        Intent intent = new Intent(fragment.getActivity(), LoginActivity.class);
        fragment.getActivity().startActivityForResult(intent, requestCode);
    }

    @Override
    public void showErrorAlert(String message) {
        new AlertDialog.Builder(fragment.getActivity()).setTitle(R.string.dialog_hint).setMessage(message).setPositiveButton(android.R.string.ok, null).show();
        edit_phone.setText("");
        ((MainActivity) getActivity()).refreshBadge();
    }

    @Override
    public void goBack() {
        getActivity().getSupportFragmentManager().popBackStack();
        getActivity().getSupportFragmentManager().popBackStack();
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