package com.hamels.daybydayegg.Repository.ApiRepository;

import android.util.Log;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Repository.AbsApiCallback;
import com.hamels.daybydayegg.Repository.ApiService.BaseApiService;
import com.hamels.daybydayegg.Repository.ApiService.MemberApiService;
import com.hamels.daybydayegg.Repository.Model.BaseModel;
import com.hamels.daybydayegg.Repository.SmileGsonConverterFactory;
import com.hamels.daybydayegg.EOrderApplication;
import com.hamels.daybydayegg.Utils.ApiUtils;
import com.hamels.daybydayegg.Utils.SharedUtils;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ApiRepository {
    public static final String TAG = ApiRepository.class.getSimpleName();

    private final String CLIENT_ID = "doubleservice";
    protected Retrofit retrofit;
    public static ApiRepository repository;
    private String sDOMAIN = EOrderApplication.sApiUrl;

    public static ApiRepository getInstance() {
        if (repository == null) {
            repository = new ApiRepository();
        }

        return repository;
    }

    public ApiRepository() {

        if (EOrderApplication.isLogin) {
            sDOMAIN = EOrderApplication.sApiUrl;
        } else {
            if (EOrderApplication.sApiUrl.equals("")) {
                sDOMAIN = EOrderApplication.ADMIN_DOMAIN;
            } else {
                sDOMAIN = EOrderApplication.sApiUrl;
            }
        }

        retrofit = new Retrofit.Builder()
                .baseUrl(sDOMAIN + "/api/")
                .addConverterFactory(SmileGsonConverterFactory.create())
                .client(getClient())
                .build();
    }

    protected void getMemberToken(String action, final BaseContract.ValueCallback<String> tokenCallback, final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("client_id", CLIENT_ID);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(BaseApiService.class).postGetMemberToken(action, requestBody).enqueue(new Callback<BaseModel<Map<String, String>>>() {
            @Override
            public void onResponse(Call<BaseModel<Map<String, String>>> call, Response<BaseModel<Map<String, String>>> response) {
                if (response.isSuccessful()) {
                    String token = response.body().getItems().get("token");
                    tokenCallback.onValueCallback(0, token);
                } else {
                    callback.onTokenExpired();
                }
            }

            @Override
            public void onFailure(Call<BaseModel<Map<String, String>>> call, Throwable t) {
                callback.onFailure(call, t);
            }
        });
    }

    public void login(final String customer_id, final String account, final String password, final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("customer_id", customer_id);
        map.put("accountno", account);
        map.put("password", password);
        map.put("isApp", "true");
        map.put("machine_type", "android"); //原生自行寫死，安卓傳”android”，蘋果傳”iOS”
        map.put("firebase_token", SharedUtils.getInstance().getFirebaseToken(EOrderApplication.getInstance()));

        Log.e(TAG, "API login DOMAIN : " + sDOMAIN + " parma : " + map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).postLogin(requestBody).enqueue(callback);
    }

//    public void getCustomerList(String lon, String lat, final AbsApiCallback apiCallback) {
//        Map<String, String> map = new HashMap<>();
//
//        map.put("isApp", "true");
//        map.put("isPagination", "false");
//        map.put("functionname", "CustomerData");
//        map.put("lon", lon);
//        map.put("lat", lat);
//        Log.e(TAG,"map : " + map.toString());
//
//        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
//        retrofit.create(BaseApiService.class).GetCustomerList(requestBody).enqueue(apiCallback);
//    }

//    public void getCustomerDetail(String sCustomerID, final AbsApiCallback apiCallback) {
//        Map<String, String> map = new HashMap<>();
//        map.put("customer_id", sCustomerID);
//        map.put("isApp", "true");
//        map.put("isPagination", "false");
//        map.put("functionname", "CustomerData");
//        map.put("modified_user", "app");
//
//        Log.e(TAG,"map : " + map.toString());
//
//        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
//        retrofit.create(BaseApiService.class).GetCustomerDetail(requestBody).enqueue(apiCallback);
//    }

    public void getMachineList(String functionname, String customer_id, String member_id, String sKilometer, String sHeadLocationFlag, AbsApiCallback apiCallback) {
        Map<String, String> map = new HashMap<>();
        switch (functionname){
            case "AppLocation1":
                functionname = "AppOftenLocation";
                break;
            case "AppLocation2":
                functionname = "AppLocation";
                break;
            case "AppLocation3":
                functionname = "AppLocation";
                break;
        }

        map.put("isApp", "true");
        map.put("functionname", functionname);
        map.put("customer_id", customer_id);
        map.put("member_id", member_id);
        map.put("kilometer", sKilometer);
        map.put("head_location_flag", sHeadLocationFlag);
        map.put("lon", Double.toString(EOrderApplication.lon));
        map.put("lat", Double.toString(EOrderApplication.lat));

        Log.e(TAG, "API getMachineList : " + map);

        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(BaseApiService.class).postGetMachineList(requestBody).enqueue(apiCallback);
    }

    public void setMachineOften(String member_id, String uid, String machine_id, final AbsApiCallback apiCallback) {
        Map<String, String> map = new HashMap<>();

        map.put("isApp", "true");
        map.put("functionname", "MachineOften");

        map.put("machine_id", machine_id);
        map.put("member_id", member_id);
        map.put("uid", uid);
        map.put("modified_user", member_id);

        Log.e(TAG, "API setMachineOften : " + map);

        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(BaseApiService.class).postSetMachineStoreOften(requestBody).enqueue(apiCallback);
    }

    public void getLocationList(String functionname, String customer_id, String member_id, String location_id, String sKilometer, String sHeadLocationFlag, AbsApiCallback apiCallback) {
        Map<String, String> map = new HashMap<>();
        switch (functionname){
            case "AppLocation1":
                functionname = "AppOftenLocation";
                break;
            case "AppLocation2":
                functionname = "AppLocation";
                break;
            case "AppLocation3":
                functionname = "AppLocation";
                break;
        }

        String sLocationID = location_id.equals("0")  ? "" : location_id;

        map.put("isApp", "true");
        map.put("functionname", functionname);
        map.put("customer_id", customer_id);
        map.put("member_id", member_id);
        map.put("location_id", sLocationID);
        map.put("kilometer", sKilometer);
        if(!sHeadLocationFlag.equals("Y")) map.put("order_source", "1");
        map.put("head_location_flag", sHeadLocationFlag);
        map.put("lon", Double.toString(EOrderApplication.lon));
        map.put("lat", Double.toString(EOrderApplication.lat));

        Log.e(TAG, "API getLocationList : " + map);

        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(BaseApiService.class).postGetLocationList(requestBody).enqueue(apiCallback);
    }

    public void setLocationOften(String member_id, String location_id, String uid, final AbsApiCallback apiCallback) {
        Map<String, String> map = new HashMap<>();

        map.put("isApp", "true");
        map.put("functionname", "AppOftenLocation");

        map.put("member_id", member_id);
        map.put("location_id", location_id);

        map.put("uid", uid);

        map.put("modified_user", member_id);

        Log.e(TAG, "API setLocationOften : " + map);

        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(BaseApiService.class).postSetStoreOften(requestBody).enqueue(apiCallback);
    }

    private OkHttpClient getClient() {
        return new OkHttpClient.Builder()
                .cookieJar(new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(EOrderApplication.getInstance())))
                .build();
    }
}
