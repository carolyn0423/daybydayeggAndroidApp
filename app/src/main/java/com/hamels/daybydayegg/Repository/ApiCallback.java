package com.hamels.daybydayegg.Repository;

import android.util.Log;

import com.google.gson.Gson;
import com.hamels.daybydayegg.Base.BasePresenter;
import com.hamels.daybydayegg.Repository.Model.BaseModel;
import com.hamels.daybydayegg.Utils.ApiUtils;

import retrofit2.Call;
import retrofit2.Response;

public class ApiCallback<T extends BaseModel> extends AbsApiCallback<T> {
    public static final String TAG = ApiCallback.class.getSimpleName();

    private BasePresenter basePresenter;

    public ApiCallback(BasePresenter basePresenter) {
        this.basePresenter = basePresenter;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful() && response.code() == 200) {
            onApiSuccess(response.body());
        } else if (response.body() != null) {
            onApiFail(response.code(), new Gson().fromJson(response.body().toString(), BaseModel.class));
        } else {
            try {
                onApiFail(response.code(), new Gson().fromJson(ApiUtils.decryption(response.errorBody().string()), BaseModel.class));
            } catch (Exception e) { // } catch (IOException e) {
                // onApiFail(response.code(), new BaseModel());
            }
        }
    }

    @Override
    public void onApiSuccess(T response) {
        if (response.getCode() == 401) {
            basePresenter.forceLogout();
        }
        Log.e(TAG, "fail onApiSuccess: hide() ");
        onApiFlowFinish();
    }

    @Override
    public void onApiFail(int errorCode, BaseModel failBaseModel) {
        Log.e(TAG, "fail message");
        onApiFlowFinish();
    }

    @Override
    public void onTokenExpired() {
        onApiFlowFinish();
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        Log.e(TAG, "fail throwable: " + t.getMessage());
        onApiFlowFinish();
    }

    @Override
    public void onApiFlowFinish() {
        basePresenter.finishApi();
    }
}
