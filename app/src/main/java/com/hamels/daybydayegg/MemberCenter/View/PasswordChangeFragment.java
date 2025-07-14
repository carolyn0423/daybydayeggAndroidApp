package com.hamels.daybydayegg.MemberCenter.View;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.hamels.daybydayegg.Base.BaseFragment;
import com.hamels.daybydayegg.Main.View.MainActivity;
import com.hamels.daybydayegg.MemberCenter.Contract.PasswordChangeContract;
import com.hamels.daybydayegg.MemberCenter.Presenter.PasswordChangePresenter;
import com.hamels.daybydayegg.R;

import java.util.Objects;

public class PasswordChangeFragment extends BaseFragment implements PasswordChangeContract.View {
    public static final String TAG = PasswordChangeFragment.class.getSimpleName();

    private TextView tvErrorMessage, tvSend;
    private EditText etExistPassword, etPassword, etPasswordAgain;

    private static PasswordChangeFragment fragment;
    private PasswordChangeContract.Presenter changePresenter;

    public static PasswordChangeFragment getInstance() {
        if (fragment == null) {
            fragment = new PasswordChangeFragment();
        }

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_password_change, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        changePresenter = new PasswordChangePresenter(this, getRepositoryManager(getContext()));
        ((MainActivity) getActivity()).setAppTitle(R.string.title_password_change);
        ((MainActivity) getActivity()).setBackButtonVisibility(true);
        ((MainActivity) getActivity()).setMessageButtonVisibility(true);
        ((MainActivity) getActivity()).setMailButtonVisibility(true);
        ((MainActivity) getActivity()).setTopBarVisibility(false);
        ((MainActivity) getActivity()).setAppToolbarVisibility(true);
        ((MainActivity) getActivity()).setMainIndexMailUnreadVisibility(false);
        ((MainActivity) getActivity()).setCartBadgeVisibility(true);

        tvErrorMessage = view.findViewById(R.id.tv_error_message);
        tvErrorMessage.setVisibility(View.GONE);
        tvSend = view.findViewById(R.id.tv_send);
        tvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePresenter.checkInputAndSendApi(etExistPassword.getText().toString(), etPassword.getText().toString(), etPasswordAgain.getText().toString());
            }
        });

        etExistPassword = view.findViewById(R.id.et_exist_password);
        etPassword = view.findViewById(R.id.et_password);
        etPasswordAgain = view.findViewById(R.id.et_password_again);
    }


    @Override
    public void showInputErrorAlert(int stringRes) {
        new AlertDialog.Builder(getContext()).setTitle(R.string.dialog_hint).setMessage(stringRes).setPositiveButton(android.R.string.ok, null).show();
    }

    @Override
    public void showFinishAlert() {
        new AlertDialog.Builder(getContext()).setTitle(R.string.dialog_hint).setMessage(R.string.change_password_success).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                etExistPassword.setText("");
                etPassword.setText("");
                etPasswordAgain.setText("");
                ((MainActivity) requireActivity()).resetPassword();
            }
        }).show();
    }

    @Override
    public void showErrorMessage(String stringRes) {
        tvErrorMessage.setVisibility(View.VISIBLE);
        tvErrorMessage.setText(stringRes);
    }
}
