package com.hamels.daybydayegg.Repository.ApiService;

import com.hamels.daybydayegg.Repository.Model.About;
import com.hamels.daybydayegg.Repository.Model.Address;
import com.hamels.daybydayegg.Repository.Model.BaseModel;
import com.hamels.daybydayegg.Repository.Model.Carousel;
import com.hamels.daybydayegg.Repository.Model.Coupon;
import com.hamels.daybydayegg.Repository.Model.Donate;
import com.hamels.daybydayegg.Repository.Model.DonateCart;
import com.hamels.daybydayegg.Repository.Model.DrawLots;
import com.hamels.daybydayegg.Repository.Model.Faq;
import com.hamels.daybydayegg.Repository.Model.MemberMessage;
import com.hamels.daybydayegg.Repository.Model.Merchant;
import com.hamels.daybydayegg.Repository.Model.Message;
import com.hamels.daybydayegg.Repository.Model.Often;
import com.hamels.daybydayegg.Repository.Model.Order;
import com.hamels.daybydayegg.Repository.Model.PointHistory;
import com.hamels.daybydayegg.Repository.Model.Product;
import com.hamels.daybydayegg.Repository.Model.ProductMainType;
import com.hamels.daybydayegg.Repository.Model.ProductType;
import com.hamels.daybydayegg.Repository.Model.User;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MemberApiService {
    @POST("Authority/DoRegister")
    Call<BaseModel<User>> postRegister(@Body RequestBody body);

    @POST("Authority/DoLogin")
    Call<BaseModel<User>> postLogin(@Body RequestBody body);

    @POST("Member/GetDetail")
    Call<BaseModel<User>> postGetMemberInfo(@Body RequestBody body);

    @POST("Member/DeleteData")
    Call<BaseModel<List<Map<String , String>>>> postDeleteData(@Body RequestBody body);

    @POST("Authority/DoLogout")
    Call<BaseModel<Map<String, String>>> postLogout(@Body RequestBody body);

    @POST("Authority/DoCheckAccount")
    Call<BaseModel<Map<String, String>>> postCheckAccount(@Body RequestBody body);

    @POST("SendMessage/SendTTINETMessage")
    Call<BaseModel<Map<String, String>>> postResendSms(@Body RequestBody body);

    @POST("Authority/DoVerify")
    Call<BaseModel<Map<String, String>>> postVerifySms(@Body RequestBody body);

//    @POST("Authority/DoChangePassword")
//    Call<BaseModel<Map<String, String>>> postForgetPassword(@Body RequestBody body);

    @POST("Authority/DoResetPassword")
    Call<BaseModel<Map<String, String>>> patchVerifyForgetPassword(@Body RequestBody body);

    @POST("Authority/DoChangePassword")
    Call<BaseModel<Map<String, String>>> patchUpdatePassword(@Body RequestBody body);

    @POST("Member/SaveData")
    Call<BaseModel<Map<String, String>>> putUpdateMember(@Body RequestBody body);

    @POST("Member/GetFunctionSaveData")
    Call<BaseModel<Map<String, String>>> GetFunctionSaveData(@Body RequestBody body);

    @POST("Property/GetPropertyList_AddressData")
    Call<BaseModel<List<Address>>> GetPropertyList_AddressData(@Body RequestBody body);

    @POST("Member/GetOftenPickup")
    Call<BaseModel<List<Often>>> GetOftenPickup(@Body RequestBody body);

    @POST("Member/GetOftenVatNumber")
    Call<BaseModel<List<Often>>> GetOftenVatNumber(@Body RequestBody body);

    @POST("News/GetList")
    Call<BaseModel<List<Carousel>>> GetCarouselList(@Body RequestBody body);

    @POST("Location/GetList")
    Call<BaseModel<List<Merchant>>> GetMerchantList(@Body RequestBody body);

    @POST("ProductType/GetStoreDetail")
    Call<BaseModel<List<ProductMainType>>> GetProductMainTypeList(@Body RequestBody body);

    @POST("Product/GetData_Type")
    Call<BaseModel<List<ProductType>>> GetProductTypeList(@Body RequestBody body);

    @POST("Lot/GetList")
    Call<BaseModel<List<DrawLots>>> GetLotList(@Body RequestBody body);

//    @POST("Shopping/GetShopCartTotalQuantity")
//    Call<BaseModel<List<Object>>> GetShoppingCartCount(@Body RequestBody body);

    @POST("Product/GetData")
    Call<BaseModel<List<Product>>> GetProductList(@Body RequestBody body);

    @POST("Product/GetData")
    Call<BaseModel<List<Product>>> GetProductDetail(@Body RequestBody body);

    @POST("Order/GetAppTicketList")
    Call<BaseModel<List<Donate>>> GetDonateList(@Body RequestBody body);

    @POST("Order/GetTicketCart")
    Call<BaseModel<List<DonateCart>>> GetTicketCart(@Body RequestBody body);

    @POST("Order/WriteOffTicketByCart")
    Call<BaseModel<List<DonateCart>>> WriteOffTicketByCart(@Body RequestBody body);

    @POST("Order/UpdateTicketCart")
    Call<BaseModel<DonateCart>> UpdateTicketCart(@Body RequestBody body);

    @POST("Order/GetTicketUsedHistory")
    Call<BaseModel<List<Donate>>> GetTicketUsedHistory(@Body RequestBody body);

    @POST("Order/GetAppTicketList")
    Call<BaseModel<List<Donate>>> GetDonateDetail(@Body RequestBody body);

    @POST("Order/GetAppDetail")
    Call<BaseModel<List<Donate>>> GetAppDetail(@Body RequestBody body);

    @POST("Member/GetList")
    Call<BaseModel<List<Donate>>> ChkPhoneExist(@Body RequestBody body);

    @POST("Order/SaveTicketData")
    Call<BaseModel<Donate>> SaveTicketData(@Body RequestBody body);

    @POST("Order/GiveTicketGiftByCart")
    Call<BaseModel<List<Donate>>> GiveTicketGiftByCart(@Body RequestBody body);

//    @POST("Push/SaveData")
//    Call<BaseModel<Donate>> SavePush(@Body RequestBody body);

    @POST("Lot/GetDetail")
    Call<BaseModel<DrawLots>> GetLotDetail(@Body RequestBody body);

    @POST("Lot/MemberDrawLots")
    Call<BaseModel> JoinDrawLots(@Body RequestBody body);

    @POST("Business/GetBusinessSaleID")
    Call<BaseModel<Map<String, String>>> GetBusinessCode(@Body RequestBody body);

    @POST("News/GetDetail")
    Call<BaseModel<Carousel>> GetNewsDetail(@Body RequestBody body);

    @POST("Product/AddToCart")
    Call<BaseModel<Map<String, String>>> AddShoppingCart(@Body RequestBody body);

    @POST("Member/GetList_PointsHistory")
    Call<BaseModel<List<PointHistory>>> postGetMemberPointHistory(@Body RequestBody body);

    @POST("Push/GetAppMemberPushList")
    Call<BaseModel<List<MemberMessage>>> postGetMemberMessageList(@Body RequestBody body);

    @POST("About/GetList")
    Call<BaseModel<List<About>>> postAboutData(@Body RequestBody body);

    @POST("Faq/GetDetail")
    Call<BaseModel<Faq>> postFaqData(@Body RequestBody body);

    @POST("v1/member/getReadNotification")
    Call<BaseModel<List<Integer>>> postGetReadMessageList(@Body RequestBody body);

    @POST("Push/UpdatePushDataMember")
    Call<BaseModel> postUpdateMessageStatus(@Body RequestBody body);

    @POST("v1/member/getMemberOrder")
    Call<BaseModel<List<Order>>> postGetMemberOrders(@Body RequestBody body);

    @POST("Push/GetPushUnReadNum")
    Call<BaseModel<List<Map<String , String>>>> postGetMailBadge(@Body RequestBody body);

    @POST("Message/GetMessageUnReadNum")
    Call<BaseModel<List<Map<String , String>>>> postGetMessageBadge(@Body RequestBody body);

    @POST("Member/GetBadgeNumber")
    Call<BaseModel<List<Map<String , String>>>> postGetBadgeNumber(@Body RequestBody body);

    @POST("v1/member/returnOrder")
    Call<BaseModel<Map<String, String>>> postReturnOrder(@Body RequestBody body);

    @POST("Message/GetMessageAndReplyData")
    Call<BaseModel<List<Message>>> postGetMessageList(@Body RequestBody body);

    @POST("Message/AddNewMessage")
    Call<BaseModel> postAddMemberContact(@Body RequestBody body);

    @POST("Message/UpdateMessageReadFlag")
    Call<BaseModel> updateReadMessage(@Body RequestBody body);

    @POST("Coupon/GetDetail")
    Call<BaseModel<Coupon>> getCouponDetail(@Body RequestBody body);

    @POST("Shopping/GetShopCartLocationQuantity")
    Call<BaseModel<String>> GetShopCartLocationQuantity(@Body RequestBody body);
}
