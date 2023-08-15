package com.hamels.daybydayegg.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.hamels.daybydayegg.Repository.Model.User;

public class SharedUtils {
    public static final String TAG = SharedUtils.class.getSimpleName();
    private final String INIT = "init";
    private final String FIREBASE_TOKEN = "firebase_token";
    private final String FIRST_OPEN = "first_open";
    private final String FIRST_OPEN_STORE_LIST = "first_open_store_list";

    private final String ACCOUNT_INFO = "account_info";
    private final String ACCOUNT = "account";
    private final String PASSWORD = "password";

    private static SharedUtils sharedUtils;

    public static SharedUtils getInstance() {
        if (sharedUtils == null) {
            sharedUtils = new SharedUtils();
        }

        return sharedUtils;
    }

    private SharedUtils() {
    }

    public boolean getUserFirstOpenApp(Context context) {
        return context.getSharedPreferences(INIT, Context.MODE_PRIVATE).getBoolean(FIRST_OPEN, true);
    }

    public void setUserOpenApp(Context context) {
        context.getSharedPreferences(INIT, Context.MODE_PRIVATE).edit().putBoolean(FIRST_OPEN, false).apply();
    }

    public String getFirebaseToken(Context context) {
        return context.getSharedPreferences(INIT, Context.MODE_PRIVATE).getString(FIREBASE_TOKEN, "");
    }


    public void setFirebaseToken(Context context, String token) {
        context.getSharedPreferences(INIT, Context.MODE_PRIVATE).edit().putString(FIREBASE_TOKEN, token).apply();
    }

    public void saveAccountInfo(Context context, String account, String password) {
        context.getSharedPreferences(ACCOUNT_INFO, Context.MODE_PRIVATE).edit()
                .putString(ACCOUNT, account)
                .putString(PASSWORD, password)
                .apply();
    }

    public void saveUserID(Context context, String member_id){
        context.getSharedPreferences("MemberID", Context.MODE_PRIVATE).edit()
                .putString("MemberID", member_id)
                .apply();
    }
    public void saveUserName(Context context, String member_name){
        context.getSharedPreferences("MemberName", Context.MODE_PRIVATE).edit()
                .putString("MemberName", member_name)
                .apply();
    }
    public void saveLoveCustomer(Context context, String sLoveCustomer){
        context.getSharedPreferences("LoveCustomer", Context.MODE_PRIVATE).edit()
                .putString("LoveCustomer", sLoveCustomer)
                .apply();
    }
    public void saveCustomerID(Context context, String customer_id){
        context.getSharedPreferences("CustomerID", Context.MODE_PRIVATE).edit()
                .putString("CustomerID", customer_id)
                .apply();
    }

    public void saveCustomerName(Context context, String customer_name){
        context.getSharedPreferences("CustomerName", Context.MODE_PRIVATE).edit()
                .putString("CustomerName", customer_name)
                .apply();
    }

    public void saveApiUrl(Context context, String sApiUrl){
        context.getSharedPreferences("ApiUrl", Context.MODE_PRIVATE).edit()
                .putString("ApiUrl", sApiUrl)
                .apply();
    }

    public void saveBusinessSaleID(Context context, String business_sale_id){
        context.getSharedPreferences("BusinessSaleID", Context.MODE_PRIVATE).edit()
                .putString("BusinessSaleID", business_sale_id)
                .apply();
    }

    public void saveShoppingCartCount(Context context, String shoppingcartcount){
        context.getSharedPreferences("ShoppingCartCount", Context.MODE_PRIVATE).edit()
                .putString("ShoppingCartCount", shoppingcartcount)
                .apply();
    }

    public void saveUser(Context context, User user) {
        SharedPreferences.Editor editor = context.getSharedPreferences(User.TAG, Context.MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString(User.TAG, json);
        editor.apply();
    }

    public void saveVerifyCode(Context context, String verifyState){
        context.getSharedPreferences("VerifyCode", Context.MODE_PRIVATE).edit()
                .putString("VerifyCode", verifyState)
                .apply();
    }

    public void saveInvitationCode(Context context, String InvitationCode){
        context.getSharedPreferences("InvitationCode", Context.MODE_PRIVATE).edit()
                .putString("InvitationCode", InvitationCode)
                .apply();
    }

    public void savePaySchemeOrderData(Context context, String sParam){
        context.getSharedPreferences("SchemeOrderData", Context.MODE_PRIVATE).edit()
                .putString("SchemeOrderData", sParam)
                .apply();
    }

    public void saveSourceActive(Context context, String sSourceActive){
        context.getSharedPreferences("SOURCE_ACTIVE", Context.MODE_PRIVATE).edit()
                .putString("SOURCE_ACTIVE", sSourceActive)
                .apply();
    }

    public void saveImageUrl(Context context, String sImageUrl){
        context.getSharedPreferences("IMAGE_URL", Context.MODE_PRIVATE).edit()
                .putString("IMAGE_URL", sImageUrl)
                .apply();
    }

    public void saveFragmentLocation(Context context, String sLocationID){
        context.getSharedPreferences("FRAGMENT_LOCATION", Context.MODE_PRIVATE).edit()
                .putString("FRAGMENT_LOCATION", sLocationID)
                .apply();
    }

    public void saveFragmentMainType(Context context, String sLocationID, String IsETicket){
        context.getSharedPreferences("FRAGMENT_MAINTYPE", Context.MODE_PRIVATE).edit()
                .putString("FRAGMENT_MAINTYPE_LOCATIONID", sLocationID)
                .putString("FRAGMENT_MAINTYPE_ISETICKET", IsETicket)
                .apply();
    }

    public void removeAllLocalData(Context context) {
        context.getSharedPreferences(User.TAG, Context.MODE_PRIVATE).edit().clear().apply();
        context.getSharedPreferences(ACCOUNT_INFO, Context.MODE_PRIVATE).edit().clear().apply();
        //context.getSharedPreferences("CustomerID", Context.MODE_PRIVATE).edit().clear().apply();
        context.getSharedPreferences("MemberID", Context.MODE_PRIVATE).edit().clear().apply();
        context.getSharedPreferences("BusinessSaleID", Context.MODE_PRIVATE).edit().clear().apply();
        context.getSharedPreferences("ShoppingCartCount", Context.MODE_PRIVATE).edit().clear().apply();
        context.getSharedPreferences("config", Context.MODE_PRIVATE).edit().clear().apply();
        context.getSharedPreferences("VerifyCode", Context.MODE_PRIVATE).edit().clear().apply();
        context.getSharedPreferences("FRAGMENT_LOCATION", Context.MODE_PRIVATE).edit().clear().apply();
        context.getSharedPreferences("FRAGMENT_MAINTYPE", Context.MODE_PRIVATE).edit().clear().apply();
        context.getSharedPreferences("IMAGE_URL", Context.MODE_PRIVATE).edit().clear().apply();
        context.getSharedPreferences("SOURCE_ACTIVE", Context.MODE_PRIVATE).edit().clear().apply();
    }
    public User getUser(Context context) {
        String json = context.getSharedPreferences(User.TAG, Context.MODE_PRIVATE).getString(User.TAG, "");
        User user = new Gson().fromJson(json, User.class);
        if (user != null && user.getMember()!=0) {
            return user;
        }
        return null;
    }

    public String getUserAccount(Context context) {
        return context.getSharedPreferences(ACCOUNT_INFO, Context.MODE_PRIVATE).getString(ACCOUNT, "");
    }

    public String getUserID(Context context) {
        return context.getSharedPreferences("MemberID", Context.MODE_PRIVATE).getString("MemberID", "");
    }

    public String getUserName(Context context) {
        return context.getSharedPreferences("MemberName", Context.MODE_PRIVATE).getString("MemberName", "");
    }

    public String getLoveCustomer(Context context) {
        return context.getSharedPreferences("LoveCustomer", Context.MODE_PRIVATE).getString("LoveCustomer", "");
    }

    public String getCustomerID(Context context) {
        return context.getSharedPreferences("CustomerID", Context.MODE_PRIVATE).getString("CustomerID", "");
    }

    public String getCustomerName(Context context) {
        return context.getSharedPreferences("CustomerName", Context.MODE_PRIVATE).getString("CustomerName", "");
    }

    public String getApiUrl(Context context) {
        return context.getSharedPreferences("ApiUrl", Context.MODE_PRIVATE).getString("ApiUrl", "");
    }

    public String getBusinessSaleID(Context context) {
        return context.getSharedPreferences("BusinessSaleID", Context.MODE_PRIVATE).getString("BusinessSaleID", "");
    }

    public String getShoppingCartCount(Context context) {
        return context.getSharedPreferences("ShoppingCartCount", Context.MODE_PRIVATE).getString("ShoppingCartCount", "");
    }

    public String getUserPassword(Context context) {
        return context.getSharedPreferences(ACCOUNT_INFO, Context.MODE_PRIVATE).getString(PASSWORD, "");
    }

    public String getVerifyCode(Context context) {
        return context.getSharedPreferences("VerifyCode", Context.MODE_PRIVATE).getString("VerifyCode", "");
    }

    public String getInvitationCode(Context context) {
        return context.getSharedPreferences("InvitationCode", Context.MODE_PRIVATE).getString("InvitationCode", "");
    }

    public String getSourceActive(Context context) {
        return context.getSharedPreferences("SOURCE_ACTIVE", Context.MODE_PRIVATE).getString("SOURCE_ACTIVE", "");
    }

    public String getImageUrl(Context context) {
        return context.getSharedPreferences("IMAGE_URL", Context.MODE_PRIVATE).getString("IMAGE_URL", "");
    }

    public String getFragmentLocation(Context context) {
        return context.getSharedPreferences("FRAGMENT_LOCATION", Context.MODE_PRIVATE).getString("FRAGMENT_LOCATION", "");
    }

    public String getFragmentMainType(Context context, String sParam) {
        String sValue = "";

        switch (sParam){
            case "LOCATIONID":
                sValue = context.getSharedPreferences("FRAGMENT_MAINTYPE", Context.MODE_PRIVATE).getString("FRAGMENT_MAINTYPE_LOCATIONID", "");
                break;
            case "ISETICKET":
                sValue = context.getSharedPreferences("FRAGMENT_MAINTYPE", Context.MODE_PRIVATE).getString("FRAGMENT_MAINTYPE_ISETICKET", "");
                break;
        }

        return sValue;
    }

    public String getPaySchemeOrderData(Context context) {
        return context.getSharedPreferences("SchemeOrderData", context.MODE_PRIVATE).getString("SchemeOrderData", "");
    }
}
