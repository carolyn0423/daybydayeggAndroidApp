package com.hamels.daybydayegg.Repository.ApiRepository;

import static com.hamels.daybydayegg.EOrderApplication.ADMIN_DOMAIN;

import android.util.Log;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.hamels.daybydayegg.EOrderApplication;
import com.hamels.daybydayegg.Repository.AbsApiCallback;
import com.hamels.daybydayegg.Repository.ApiService.CustomerApiService;
import com.hamels.daybydayegg.Repository.SmileGsonConverterFactory;
import com.hamels.daybydayegg.Utils.ApiUtils;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;

public class ApiAdminRepository {
    public static final String TAG = ApiAdminRepository.class.getSimpleName();

    private final String CLIENT_ID = "doubleservice";

    protected Retrofit retrofit;
    private static ApiAdminRepository repository;

    public static ApiAdminRepository getInstance() {
        if (repository == null) {
            repository = new ApiAdminRepository();
        }

        return repository;
    }

    ApiAdminRepository() {
        retrofit = new Retrofit.Builder()
                .baseUrl(ADMIN_DOMAIN + "/api/")
                .addConverterFactory(SmileGsonConverterFactory.create())
                .client(getClient())
                .build();
    }

    public void getCustomerList(String sCity, String sCustomerName, final AbsApiCallback apiCallback) {
        Map<String, String> map = new HashMap<>();

        map.put("isApp", "true");
        map.put("isPagination", "false");
        map.put("functionname", "CustomerData");
        map.put("lon", Double.toString(EOrderApplication.lon));
        map.put("lat", Double.toString(EOrderApplication.lat));
        map.put("city", sCity);
        map.put("customer_name", sCustomerName);
        map.put("Online_Enabled", "Y");

        Log.e(TAG, "API getCustomerList : " + map);

        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(CustomerApiService.class).GetCustomerList(requestBody).enqueue(apiCallback);
    }

    public void getCustomerDetailList(String sCustomerIDList, final AbsApiCallback apiCallback) {
        Map<String, String> map = new HashMap<>();
        map.put("customer_id_list", sCustomerIDList);
        map.put("isApp", "true");
        map.put("functionname", "CustomerData");
        map.put("modified_user", "app");
        map.put("Online_Enabled", "Y");

        Log.e(TAG, "API getCustomerDetailList : " + map);

        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(CustomerApiService.class).GetCustomerList(requestBody).enqueue(apiCallback);
    }

    public void getCustomerDetail(String sCustomerID, String sFunctionName, final AbsApiCallback apiCallback) {
        Map<String, String> map = new HashMap<>();
        map.put("customer_id", sCustomerID);
        map.put("isApp", "true");
        map.put("isPagination", "false");
        map.put("functionname", sFunctionName);
        map.put("modified_user", "app");
        map.put("sys_name", "android_daybydayegg_appstore_version");

        Log.e(TAG, "API getCustomerDetail : " + map);

        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(CustomerApiService.class).GetCustomerDetail(requestBody).enqueue(apiCallback);
    }

    public void getCustomerNo(String sCustomerNo, final AbsApiCallback apiCallback) {
        Map<String, String> map = new HashMap<>();
        map.put("customer_no", sCustomerNo);
        map.put("isApp", "true");
        map.put("isPagination", "false");
        map.put("functionname", "CustomerNo");
        map.put("modified_user", "app");

        Log.e(TAG, "API getCustomerNo : " + map);

        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(CustomerApiService.class).GetCustomerDetail(requestBody).enqueue(apiCallback);
    }

    public void getCarouselList(final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("isApp", "true");
        Log.e(TAG, "API getCarouselList : " + map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(CustomerApiService.class).GetCarouselList(requestBody).enqueue(callback);
    }

    public void getPropertyData(final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("isApp", "true");
        Log.e(TAG, "API getPropertyData : " + map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(CustomerApiService.class).GetPropertyList_AddressData(requestBody).enqueue(callback);
    }

    private OkHttpClient getClient() {
        return new OkHttpClient.Builder()
                .cookieJar(new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(EOrderApplication.getInstance())))
                .build();
    }

    //  取得版本號
    public void getWebConfig(String sSysName, final AbsApiCallback apiCallback) {
        Map<String, String> map = new HashMap<>();
        map.put("isAdmin", "true");
        map.put("sys_name", sSysName);
        map.put("isApp", "true");

        Log.e(TAG, "API getWebConfig : " + map);

        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(CustomerApiService.class).GetWebConfig(requestBody).enqueue(apiCallback);
    }
}
