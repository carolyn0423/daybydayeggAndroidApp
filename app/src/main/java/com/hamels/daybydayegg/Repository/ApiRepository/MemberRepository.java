package com.hamels.daybydayegg.Repository.ApiRepository;

import android.util.Log;

import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Repository.AbsApiCallback;
import com.hamels.daybydayegg.Repository.ApiService.MemberApiService;
import com.hamels.daybydayegg.Repository.Model.OrderProduct;
import com.hamels.daybydayegg.Repository.Model.User;
import com.hamels.daybydayegg.EOrderApplication;
import com.hamels.daybydayegg.Utils.ApiUtils;
import com.hamels.daybydayegg.Utils.SharedUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.hamels.daybydayegg.Constant.ApiConstant.*;
import static com.hamels.daybydayegg.Order.View.ReturnListFragment.STATUS_RETURN;

public class MemberRepository extends ApiRepository {
    public static final String TAG = MemberRepository.class.getSimpleName();

    public static MemberRepository memberRepository;

    public static MemberRepository getInstance() {
        if (memberRepository == null) {
            memberRepository = new MemberRepository();
        }
        return memberRepository;
    }

    private MemberRepository() {
        super();
    }

    public void register(final User user, final String password, String InvitationCode, final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("connection_name", EOrderApplication.dbConnectName);
        map.put("customer_id", EOrderApplication.CUSTOMER_ID);
        map.put("accountno", user.getAccount());
        map.put("password", password);
        map.put("name", user.getName());
        //map.put("birthday", user.getBirthday());
        map.put("birthday", "");
        map.put("email", "");
        //map.put("gender", user.getGender());
        map.put("isApp", "true");
        map.put("machine_type", "android"); //原生自行寫死，安卓傳”android”，蘋果傳”iOS”
        map.put("firebase_token", SharedUtils.getInstance().getFirebaseToken(EOrderApplication.getInstance()));
        map.put("invitation_code", InvitationCode);
        Log.e(TAG, "API register : " + map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).postRegister(requestBody).enqueue(callback);
    }

    public void logout(final String member_id, final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("connection_name", EOrderApplication.dbConnectName);
        map.put("member_id", member_id);
        map.put("isApp", "true");
        Log.e(TAG, "API logout : " + map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).postLogout(requestBody).enqueue(callback);
    }

    public void getNewsDetailApi(final String news_id, final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("connection_name", EOrderApplication.dbConnectName);
        map.put("news_id", news_id);
        map.put("isApp", "true");
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).GetNewsDetail(requestBody).enqueue(callback);
    }

    public void checkAccount(final String customer_id, final String account, final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("connection_name", EOrderApplication.dbConnectName);
        map.put("customer_id", customer_id);
        map.put("accountno", account);
        map.put("isApp", "true");
        Log.e(TAG, "API checkAccount : " + map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).postCheckAccount(requestBody).enqueue(callback);
    }

    public void resendSms(final String customer_id,final String account, final String code, final String function_name, final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("connection_name", EOrderApplication.dbConnectName);
        map.put("customer_id", customer_id);
        map.put("accountno", account);
        map.put("smsverifycode", code);
        map.put("isApp", "true");
        map.put("functionname", function_name);
        Log.e(TAG, "API resendSms : " + map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).postResendSms(requestBody).enqueue(callback);
    }

    public void verifySms(final String customer_id,final String member_id, final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("connection_name", EOrderApplication.dbConnectName);
        map.put("modified_user", member_id);
        map.put("customer_id", customer_id);
        map.put("member_id", member_id);
        map.put("isApp", "true");
        Log.e(TAG, "API verifySms : " + map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).postVerifySms(requestBody).enqueue(callback);
    }

//    public void forgetPasswordSms(final String cellphone, final AbsApiCallback callback) {
//        Map<String, String> map = new HashMap<>();
//        map.put("modified_user", cellphone);
//        map.put("account", cellphone);
//        map.put("account", cellphone);
//        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
//        retrofit.create(MemberApiService.class).postForgetPassword(requestBody).enqueue(callback);
//    }

    public void verifyForgetPassword(String customer_id, final String cellphone, final String password, final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("connection_name", EOrderApplication.dbConnectName);
        map.put("customer_id", customer_id);
        map.put("accountno", cellphone);
        map.put("password", password);
        map.put("isApp", "true");
        Log.e(TAG, "API verifyForgetPassword : " + map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).patchVerifyForgetPassword(requestBody).enqueue(callback);
    }

    public void getMemberInfo(String customer_id,String member_id, final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("connection_name", EOrderApplication.dbConnectName);
        map.put("modified_user", member_id);
        map.put("member_id", member_id);
        //map.put("functionname", "membercard_normal");
        map.put("functionname", "membercard");
        map.put("customer_id", customer_id);
        map.put("isApp", "true");
        Log.e(TAG, "API getMemberInfo : " + map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).postGetMemberInfo(requestBody).enqueue(callback);
    }

    public void getDeleteMember(String member_id, final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("connection_name", EOrderApplication.dbConnectName);
        map.put("modified_user", member_id);
        map.put("member_id", member_id);
        //map.put("functionname", "membercard_normal");
        map.put("delete_flag", "Y");
        Log.e(TAG, "API getDeleteMember : " + map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).postDeleteData(requestBody).enqueue(callback);
    }

    public void getPropertyData(final String sMebmer_id, final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("connection_name", EOrderApplication.dbConnectName);
        map.put("modified_user", sMebmer_id);
        map.put("isApp", "true");
        map.put("functionname", "fulladdress");
        Log.e(TAG, "API getPropertyData : " + map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).GetPropertyList_AddressData(requestBody).enqueue(callback);
    }

    public void getCarouselList(final String sCustomer_id,final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("connection_name", EOrderApplication.dbConnectName);
        map.put("isApp", "true");
        map.put("customer_id", sCustomer_id);
        //map.put("category_name", keyword);
        Log.e(TAG, "API getCarouselList : " + map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).GetCarouselList(requestBody).enqueue(callback);
    }

    public void getMerchantList(final String sCustomer_id, final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("connection_name", EOrderApplication.dbConnectName);
        //map.put("business_sale_id", business_sale_id);
        map.put("isApp", "true");
        map.put("customer_id", sCustomer_id);
        //map.put("category_name", keyword);
        Log.e(TAG, "API getMerchantList : " + map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).GetMerchantList(requestBody).enqueue(callback);
    }

    public void getProductMainTypeList(final String location_id, final String customer_id, final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("connection_name", EOrderApplication.dbConnectName);
        String sLocationID = location_id.equals("0") ? "" : location_id;

        map.put("isApp", "true");
        map.put("isPagination", "false");
        map.put("enabled", "Y");
        map.put("customer_id", customer_id);
        map.put("location_id", sLocationID);
        Log.e(TAG, "API getProductMainTypeList : " + map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).GetProductMainTypeList(requestBody).enqueue(callback);
    }

    public void getProductTypeList(final int product_type_main_id, final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("connection_name", EOrderApplication.dbConnectName);
        map.put("isApp", "true");
        map.put("product_type_main_id", Integer.toString(product_type_main_id));
        //map.put("product_type_main_id", Integer.toString(product_type_main_id));
        Log.e(TAG, "API getProductTypeList : " + map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).GetProductTypeList(requestBody).enqueue(callback);
    }

    public void getLotList(final String mebmer_id, final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("connection_name", EOrderApplication.dbConnectName);
        map.put("isApp", "true");
        map.put("modified_user", mebmer_id);
        map.put("mebmer_id", mebmer_id);
        map.put("functionname", "lotlist");
        Log.e(TAG, "API getLotList : " + map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).GetLotList(requestBody).enqueue(callback);
    }

//    public void getShoppingCartCount(final String mebmer_id ,final AbsApiCallback callback) {
//        Map<String, String> map = new HashMap<>();
//        map.put("isApp", "true");
//        map.put("member_id", mebmer_id);
//        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
//        retrofit.create(MemberApiService.class).GetShoppingCartCount(requestBody).enqueue(callback);
//    }

    public void getProductList(final String customer_id,final String location_id,final String business_sale_id, final String sort, final String product_type_main_id, final String product_type_id, final String product_name, final String e_ticket, final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("connection_name", EOrderApplication.dbConnectName);
        String sLocationID = location_id.equals("0") ? "" : location_id;

        map.put("isApp", "true");
        map.put("customer_id", customer_id);
        map.put("location_id", sLocationID);
        map.put("product_type_id", product_type_id);
        map.put("product_type_main_id", product_type_main_id);
        map.put("product_name", product_name);
        map.put("functionname", "productlist");
        map.put("SortMode", sort);
        map.put("e_ticket", e_ticket);
       //map.put("sales_type", "");
        map.put("business_sale_id", business_sale_id);
        Log.e(TAG, "API getProductList : " + map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).GetProductList(requestBody).enqueue(callback);
    }

    public void getProductDetail(final String business_sale_id, final String member_id, final String product_id, final String e_ticket, final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("connection_name", EOrderApplication.dbConnectName);
        map.put("product_id", product_id);
        map.put("isApp", "true");
        map.put("functionname", "productdetail");
        map.put("member_id", member_id);
        map.put("business_sale_id", business_sale_id);
        map.put("e_ticket", e_ticket);
        Log.e(TAG, "API getProductDetail : " + map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).GetProductDetail(requestBody).enqueue(callback);
    }

    public void getDonateList(final String member_id, final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("connection_name", EOrderApplication.dbConnectName);
        map.put("isApp", "true");
        map.put("member_id", member_id);
        Log.e(TAG, "API getDonateList : " + map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).GetDonateList(requestBody).enqueue(callback);
    }

    public void getTicketCart(final String member_id, final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("connection_name", EOrderApplication.dbConnectName);
        map.put("isApp", "true");
        map.put("member_id", member_id);
        Log.e(TAG, "API getTicketCart : " + map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).GetTicketCart(requestBody).enqueue(callback);
    }

    public void writeOffTicketByCart(final String member_id, String cart_ticket_code, final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("connection_name", EOrderApplication.dbConnectName);
        map.put("isApp", "true");
        map.put("member_id", member_id);
        map.put("cart_ticket_code", cart_ticket_code);
        Log.e(TAG, "API writeOffTicketByCart : " + map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).WriteOffTicketByCart(requestBody).enqueue(callback);
    }

    public void updateTicketCart(final String member_id, final String action, final String product_id, final String spec_id, final String give_date, final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("connection_name", EOrderApplication.dbConnectName);
        map.put("isApp", "true");
        map.put("member_id", member_id);
        map.put("modified_user", member_id);

        map.put("action", action);
        map.put("product_id", product_id);
        map.put("spec_id", spec_id);
        map.put("give_date", give_date);

        Log.e(TAG, "API updateTicketCart : " + map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).UpdateTicketCart(requestBody).enqueue(callback);
    }

    public void updateTicketCartCode(final String member_id, final String cart_ticket_code, final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("connection_name", EOrderApplication.dbConnectName);
        map.put("isApp", "true");
        map.put("member_id", member_id);
        map.put("modified_user", member_id);

        map.put("action", "reset_cart_ticket_code");
        map.put("cart_ticket_code", cart_ticket_code);

        Log.e(TAG, "API updateTicketCartCode : " + map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).UpdateTicketCart(requestBody).enqueue(callback);
    }
    public void GetTicketUsedHistory(final String member_id, final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("connection_name", EOrderApplication.dbConnectName);
        map.put("isApp", "true");
        map.put("member_id", member_id);

        Log.e(TAG, "API GetTicketUsedHistory : " + map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).GetTicketUsedHistory(requestBody).enqueue(callback);
    }

    public void getDonateDetail(final String member_id, final String uid, final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("connection_name", EOrderApplication.dbConnectName);
        map.put("isApp", "true");
        map.put("member_id", member_id);
        map.put("uid", uid);

        Log.e(TAG, "API getDonateDetail : " + map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).GetDonateDetail(requestBody).enqueue(callback);
    }

    public void GetAppDetail(final String member_id, final String writeoff_order_id, final String eticket_due_date, final String modified_date, final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("connection_name", EOrderApplication.dbConnectName);
        map.put("isApp", "true");
        map.put("member_id", member_id);
        map.put("writeoff_order_id", writeoff_order_id);
        map.put("modified_date", modified_date);
        map.put("eticket_due_date", eticket_due_date);

        Log.e(TAG, "API GetAppDetail : " + map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).GetAppDetail(requestBody).enqueue(callback);
    }

    public void ChkPhoneExist(final String customer_id,final String mobile, final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("connection_name", EOrderApplication.dbConnectName);
        map.put("customer_id", customer_id);
        map.put("mobile", mobile);
        map.put("functionname", "assignmember");

        Log.e(TAG, "API ChkPhoneExist : " + map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).ChkPhoneExist(requestBody).enqueue(callback);
    }

    public void SaveTicketData(final String customer_id,final String member_id, final String uid, final String mobile, final String quantity, final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("connection_name", EOrderApplication.dbConnectName);
        map.put("isApp", "true");
        map.put("modified_user", member_id);
        map.put("uid", uid);
        map.put("ticket_status", "G");
        map.put("Mode", "GiveTicket");
        map.put("mobile", mobile);
        map.put("customer_id", customer_id);
        map.put("quantity", quantity);

        Log.e(TAG, "API SaveTicketData : " + map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).SaveTicketData(requestBody).enqueue(callback);
    }

    public void GiveTicketGiftByCart(final String customer_id,final String member_id, final String mobile, final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("connection_name", EOrderApplication.dbConnectName);
        map.put("isApp", "true");
        map.put("customer_id", customer_id);
        map.put("member_id", member_id);
        map.put("mobile", mobile);

        Log.e(TAG, "API GiveTicketGiftByCart : " + map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).GiveTicketGiftByCart(requestBody).enqueue(callback);
    }

    public void SavePush(final String member_id, final String sendmember_id, final String title, final String content, final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("connection_name", EOrderApplication.dbConnectName);
        map.put("push_id", "");
        map.put("push_type", "G");
        map.put("title", title);
        map.put("content", content);
        map.put("schedule_flag", "N");
        map.put("schedule_datetime", "");
        map.put("receiver_type", "S");
        map.put("receiver_member_list", sendmember_id);
        map.put("modified_user", member_id);

        Log.e(TAG, "API SavePush : " + map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).SavePush(requestBody).enqueue(callback);
    }

    public void getMemberInfoAssignmember(String customer_id,String member_id, String keyword, final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("connection_name", EOrderApplication.dbConnectName);
        map.put("modified_user", member_id);
        map.put("keyworld", keyword);
        map.put("functionname", "assignmember");
        map.put("customer_id", customer_id);
        map.put("isApp", "true");

        Log.e(TAG, "API getMemberInfoAssignmember : " + map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).postGetMemberInfo(requestBody).enqueue(callback);
    }

    public void getLotDetail(final int lot_id, final String member_id, final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("connection_name", EOrderApplication.dbConnectName);
        map.put("modified_user", member_id);
        map.put("member_id", member_id);
        map.put("isApp", "true");
        map.put("functionname", "lotdetail");
        map.put("lot_id", Integer.toString(lot_id));

        Log.e(TAG, "API getLotDetail : " + map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).GetLotDetail(requestBody).enqueue(callback);
    }

    public void joinDrawLots(final int lot_id, final String cif_code, final String prod_id, final String spec_name, final String member_id, final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("connection_name", EOrderApplication.dbConnectName);
        map.put("modified_user", member_id);
        map.put("member_id", member_id);
        map.put("isApp", "true");
        map.put("lot_product_id", prod_id);
        map.put("lot_spec_name", spec_name);
        map.put("cif_id", cif_code);
        map.put("lot_id", Integer.toString(lot_id));

        Log.e(TAG, "API joinDrawLots : " + map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).JoinDrawLots(requestBody).enqueue(callback);
    }

    public void getBusinessCode(final String sale_password, final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("connection_name", EOrderApplication.dbConnectName);
        map.put("sale_password", sale_password);
        map.put("isApp", "true");

        Log.e(TAG, "API getBusinessCode : " + map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).GetBusinessCode(requestBody).enqueue(callback);
    }

    public void addShoppingCart(final String customer_id,final String business_sale_id, final String sale_type, final String member_id, final String product_id, final String spec_id, final String location_id, final String quantity, final String order_type, final String conf_list, final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("connection_name", EOrderApplication.dbConnectName);
        map.put("customer_id", customer_id);
        map.put("member_id", member_id);
        map.put("product_id", product_id);
        map.put("spec_id", spec_id);
        map.put("location_id", location_id);
        map.put("quantity", quantity);
        map.put("isApp", "true");
        map.put("sales_type", sale_type);
        map.put("modified_user", member_id);
        map.put("business_sale_id", business_sale_id);
        map.put("order_type", order_type);
        map.put("conf_list", conf_list);

        Log.e(TAG, "API addShoppingCart : " + map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).AddShoppingCart(requestBody).enqueue(callback);
    }

    public void updatePassword(final String customer_id,final String member_id, final String oldPassword, final String newPassword, final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("connection_name", EOrderApplication.dbConnectName);
        map.put("modified_user", member_id);
        map.put("customer_id", customer_id);
        map.put("member_id", member_id);
        map.put("password", newPassword);
        map.put("oldpassword", oldPassword);

        Log.e(TAG, "API updatePassword : " + map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).patchUpdatePassword(requestBody).enqueue(callback);
    }

    public void updateMemberInfo(String customer_id, final String sMebmer_id, final String city_code, final String area_code, final String address, final String email, final String birth, String carrier_no, final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("connection_name", EOrderApplication.dbConnectName);
        map.put("customer_id", customer_id);
        map.put("modified_user", sMebmer_id);
        map.put("member_id", sMebmer_id);
//        map.put("gender", gender);
        map.put("birthday", birth);
        map.put("city_code", city_code);
        map.put("area_code", area_code);
        map.put("address", address);
        map.put("email", email);
        map.put("carrier_no", carrier_no);
        map.put("isApp", "true");

        Log.e(TAG, "API updateMemberInfo : " + map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).putUpdateMember(requestBody).enqueue(callback);
    }

    public void getMemberPointHistory(final String date, final String member_id, final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("connection_name", EOrderApplication.dbConnectName);
        map.put("modified_user", member_id);
        map.put("member_id", member_id);
        map.put("modified_date_y", date);
        map.put("isApp", "true");

        Log.e(TAG, "API getMemberPointHistory : " + map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).postGetMemberPointHistory(requestBody).enqueue(callback);
    }

    public void getMemberMessage(final String customer_id,final String member_id, final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("connection_name", EOrderApplication.dbConnectName);
        map.put("modified_user", member_id);
        map.put("functionname", "apppushlist");
        map.put("customer_id", customer_id);
        map.put("member_id", member_id);
        map.put("isApp", "true");

        Log.e(TAG, "API getMemberMessage : " + map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).postGetMemberMessageList(requestBody).enqueue(callback);

    }

    public void getAboutData(final String customer_id,final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("connection_name", EOrderApplication.dbConnectName);
        map.put("isApp", "true");
        map.put("customer_id", customer_id);

        Log.e(TAG, "API getAboutData : " + map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).postAboutData(requestBody).enqueue(callback);
    }

    public void getFaqData(final String customer_id,final String faq_type_id, final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("connection_name", EOrderApplication.dbConnectName);
        map.put("isApp", "true");
        map.put("customer_id", customer_id);
        map.put("faq_type_id", faq_type_id);

        Log.e(TAG, "API getFaqData : " + map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).postFaqData(requestBody).enqueue(callback);
    }

    public void getReadMessage(final AbsApiCallback callback) {
        getMemberToken(ACTION_GET_READ_MESSAGE_LIST, new BaseContract.ValueCallback<String>() {
            @Override
            public void onValueCallback(int task, String code) {
                Map<String, String> map = new HashMap<>();
                map.put("connection_name", EOrderApplication.dbConnectName);
                map.put("code", code);

                RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
                retrofit.create(MemberApiService.class).postGetReadMessageList(requestBody).enqueue(callback);
            }
        }, callback);
    }

    public void updateMessageStatus(final String mebmer_id, final int messageId, final String status, final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("connection_name", EOrderApplication.dbConnectName);
        map.put("modified_user", mebmer_id);
        map.put("member_id", mebmer_id);
        map.put("isApp", "true");
        map.put("push_id", Integer.toString(messageId));
        map.put(status, "Y");

        Log.e(TAG, "API updateMessageStatus : " + map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).postUpdateMessageStatus(requestBody).enqueue(callback);
    }

    public void getOrders(final AbsApiCallback callback) {
        getMemberToken(ACTION_GET_ORDERS, new BaseContract.ValueCallback<String>() {
            @Override
            public void onValueCallback(int task, String code) {
                Map<String, String> map = new HashMap<>();
                map.put("connection_name", EOrderApplication.dbConnectName);
                map.put("code", code);

                RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
                retrofit.create(MemberApiService.class).postGetMemberOrders(requestBody).enqueue(callback);
            }
        }, callback);
    }

    public void getMailBadge(final String mebmer_id , final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("connection_name", EOrderApplication.dbConnectName);
        map.put("modified_user",mebmer_id);
        map.put("member_id",mebmer_id);
        map.put("isApp","true");

        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).postGetMailBadge(requestBody).enqueue(callback);
    }

    public void getMessageBadge(final String mebmer_id , final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("connection_name", EOrderApplication.dbConnectName);
        map.put("modified_user",mebmer_id);
        map.put("member_id",mebmer_id);
        map.put("isApp","true");

        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).postGetMessageBadge(requestBody).enqueue(callback);
    }

    public void getBadgeNumber(final String customer_id,final String mebmer_id, final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("connection_name", EOrderApplication.dbConnectName);
        map.put("modified_user", mebmer_id);
        map.put("customer_id", customer_id);
        map.put("member_id", mebmer_id);
        map.put("isApp", "true");

        Log.e(TAG, "API getBadgeNumber : " + map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).postGetBadgeNumber(requestBody).enqueue(callback);
    }

    public void returnProducts(final ArrayList<OrderProduct> products, final String name, final String address,
                               final String mobile, final String email, final int returnType, final AbsApiCallback callback) {
        getMemberToken(ACTION_RETURN_ORDER, new BaseContract.ValueCallback<String>() {
            @Override
            public void onValueCallback(int task, String code) {
                Map<String, String> map = new HashMap<>();
                map.put("connection_name", EOrderApplication.dbConnectName);
                map.put("code", code);
                map.put("oid", products.get(0).getOrderId());
                map.put("name", name);
                map.put("address", address);
                map.put("mobile", mobile);
                map.put("email", email);
                map.put("type", returnType == STATUS_RETURN ? "return" : "exchange");

                Log.e(TAG, "API returnProducts : " + map);
                StringBuilder productBuilder = new StringBuilder();
                for (OrderProduct orderProduct : products) {
                    productBuilder.append(orderProduct.getProduct().getProductNo()).append(",");
                }
                map.put("product", productBuilder.substring(0, productBuilder.length() - 1));
                RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
                retrofit.create(MemberApiService.class).postReturnOrder(requestBody).enqueue(callback);
            }
        }, callback);
    }

    public void getMessageList(final String member_id, final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("connection_name", EOrderApplication.dbConnectName);
        map.put("modified_user", member_id);
        map.put("isApp", "true");
        map.put("member_id", member_id);

        Log.e(TAG, "API getMessageList : " + map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).postGetMessageList(requestBody).enqueue(callback);
    }

    public void addMemberContact(final String customer_id,final String member_id, final String message, final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("connection_name", EOrderApplication.dbConnectName);
        map.put("modified_user", member_id);
        map.put("isApp", "true");
        map.put("customer_id", customer_id);
        map.put("member_id", member_id);
        map.put("message_content", message);

        Log.e(TAG, "API addMemberContact : " + map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).postAddMemberContact(requestBody).enqueue(callback);
    }

    public void updateReadMessage(final String member_id, final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("connection_name", EOrderApplication.dbConnectName);
        map.put("modified_user", member_id);
        map.put("isApp", "true");
        map.put("member_id", member_id);

        Log.e(TAG, "API updateReadMessage : " + map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).updateReadMessage(requestBody).enqueue(callback);
    }

    public void GetRegisterCoupon(String member_id, final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("connection_name", EOrderApplication.dbConnectName);
        map.put("modified_user", member_id);
        map.put("functionname", "MemberRegisterCoupon");
        map.put("customer_id", EOrderApplication.CUSTOMER_ID);
        map.put("isApp", "true");
        Log.e(TAG, "API GetRegisterCoupon : " + map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).getCouponDetail(requestBody).enqueue(callback);
    }

    public void GetShopCartLocationQuantity(final String sMemberID, final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("connection_name", EOrderApplication.dbConnectName);
        map.put("customer_id", EOrderApplication.CUSTOMER_ID);
        map.put("isApp", "true");
        map.put("member_id", sMemberID);

        Log.e(TAG, "API GetShopCartLocationQuantity : " + map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).GetShopCartLocationQuantity(requestBody).enqueue(callback);
    }
}
