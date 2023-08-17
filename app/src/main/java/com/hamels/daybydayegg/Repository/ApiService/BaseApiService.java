package com.hamels.daybydayegg.Repository.ApiService;

import com.hamels.daybydayegg.Repository.Model.BaseModel;
import com.hamels.daybydayegg.Repository.Model.Customer;
import com.hamels.daybydayegg.Repository.Model.Machine;
import com.hamels.daybydayegg.Repository.Model.Store;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BaseApiService {
    @Headers("Authorization: Basic ZG91Ymxlc2VydmljZTp2T2x1TjN4T3Nt")
    @POST("v1/auth/{action}")
    Call<BaseModel<Map<String, String>>> postGetMemberToken(@Path("action") String action, @Body RequestBody body);

    @Headers("Authorization: Basic ZG91Ymxlc2VydmljZTp2T2x1TjN4T3Nt")
    @POST("Location/GetList")
    Call<BaseModel<List<Store>>> postGetLocationList(@Body RequestBody body);

    @POST("Machine/GetMachineList")
    Call<BaseModel<List<Machine>>> postGetMachineList(@Body RequestBody body);

    @Headers("Authorization: Basic ZG91Ymxlc2VydmljZTp2T2x1TjN4T3Nt")
    @POST("Location/SaveDetail")
    Call<BaseModel> postSetStoreOften(@Body RequestBody body);

    @POST("Machine/SaveDetail")
    Call<BaseModel> postSetMachineStoreOften(@Body RequestBody body);

    @POST("Customer/GetList")
    Call<BaseModel<List<Customer>>> GetCustomerList(@Body RequestBody body);
    @POST("Customer/GetDetail")
    Call<BaseModel<Customer>> GetCustomerDetail(@Body RequestBody body);
}
