package com.hamels.daybydayegg.Order.View;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Base.BaseFragment;
import com.hamels.daybydayegg.Main.View.MainActivity;
import com.hamels.daybydayegg.Order.Contract.ReturnInputContract;
import com.hamels.daybydayegg.Order.Presenter.ReturnInputPresenter;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.OrderProduct;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.hamels.daybydayegg.Order.View.ReturnListFragment.STATUS_RETURN;

public class ReturnInputFragment extends BaseFragment implements ReturnInputContract.View {
    public static final String TAG = ReturnInputFragment.class.getSimpleName();

    private TextView tvName, tvAddress, tvPhone, tvMail;
    private Button btnConfirm;
    private EditText etName, etAddress, etPhone, etMail;

    private ReturnInputContract.Presenter returnPresenter;
    private BaseContract.ValueCallback<Boolean> finishCallback;

    private static ReturnInputFragment fragment;
    private ArrayList<OrderProduct> products;
    private int status = STATUS_RETURN;

    public static ReturnInputFragment getInstance(List<OrderProduct> products, int status) {
        if (fragment == null) {
            fragment = new ReturnInputFragment();
        }

        Bundle bundle = new Bundle();
        bundle.putSerializable(OrderProduct.TAG, (Serializable) products);
        bundle.putInt(TAG, status);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_return_input, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        if (getArguments() != null) {
            if (getArguments().containsKey(TAG)) {
                status = getArguments().getInt(TAG, STATUS_RETURN);
            }
            if (getArguments().containsKey(OrderProduct.TAG)) {
                products = (ArrayList<OrderProduct>) getArguments().getSerializable(OrderProduct.TAG);
            }
        }

        ((MainActivity) requireActivity()).setAppTitle(status == STATUS_RETURN ? R.string.return_apply_title : R.string.exchange_apply_title);
        ((MainActivity) getActivity()).setCartBadgeVisibility(true);

        tvName = view.findViewById(R.id.tv_name);
        addSpanColor(tvName);
        tvAddress = view.findViewById(R.id.tv_address);
        addSpanColor(tvAddress);
        tvPhone = view.findViewById(R.id.tv_phone);
        addSpanColor(tvPhone);
        tvMail = view.findViewById(R.id.tv_mail);
        addSpanColor(tvMail);

        etName = view.findViewById(R.id.et_name);
        etAddress = view.findViewById(R.id.et_address);
        etPhone = view.findViewById(R.id.et_phone);
        etMail = view.findViewById(R.id.et_mail);

        btnConfirm = view.findViewById(R.id.btn_confirm);
        btnConfirm.setText(status == STATUS_RETURN ? R.string.return_confirm : R.string.exchange_confirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnPresenter.checkInputAndSend(products, status, etName.getText().toString(), etAddress.getText().toString(),
                        etPhone.getText().toString(), etMail.getText().toString());
            }
        });

        returnPresenter = new ReturnInputPresenter(this, getRepositoryManager(getContext()));
    }


    private void addSpanColor(TextView textView) {
        SpannableString spannableString = new SpannableString(textView.getText().toString());
        spannableString.setSpan(new ForegroundColorSpan(requireContext().getResources().getColor(R.color.redHint)),
                0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString);
    }

    @Override
    public void showAlert(int message) {
        showAlert(getString(message));
    }

    @Override
    public void showAlert(String message) {
        new AlertDialog.Builder(requireActivity()).setTitle(R.string.dialog_hint).setMessage(message)
                .setPositiveButton(android.R.string.ok, null).show();
    }

    @Override
    public void showReturnResult(boolean isSuccess, String message) {
        if (finishCallback != null) {
            finishCallback.onValueCallback(0, isSuccess);
        }

        showAlert(message);
        requireActivity().onBackPressed();
    }

    public void setFinishCallback(BaseContract.ValueCallback<Boolean> finishCallback) {
        this.finishCallback = finishCallback;
    }
}
