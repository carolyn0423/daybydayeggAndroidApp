package com.hamels.daybydayegg.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hamels.daybydayegg.Repository.Model.OftenMobile;
import com.hamels.daybydayegg.Repository.Model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

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
    public void saveShopkeeper(Context context, String sShopkeeper){
        context.getSharedPreferences("Shopkeeper", Context.MODE_PRIVATE).edit()
                .putString("Shopkeeper", sShopkeeper)
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

//    public void saveOftenMobile(Context context, String sMobile, String sNick, String sRemoveKey){
//        HashMap hashMap = getOftenMobile(context);
//        if(hashMap == null) hashMap = new HashMap();
//        if(!sRemoveKey.isEmpty()){
//            if(hashMap.containsKey(sRemoveKey)){
//                hashMap.remove(sRemoveKey);
//            }
//        }
//        hashMap.put(sMobile, sNick);
//
//        // 將 HashMap 轉換為 JSON 字符串
//        context.getSharedPreferences("OftenMobileJson", Context.MODE_PRIVATE).edit()
//                .putString("OftenMobileJson", new Gson().toJson(hashMap))
//                .apply();
//    }
//
//    public void removeOftenMobile(Context context, String sMobile){
//        HashMap hashMap = getOftenMobile(context);
//        if(hashMap == null) hashMap = new HashMap();
//        hashMap.remove(sMobile);
//
//        // 將 HashMap 轉換為 JSON 字符串
//        context.getSharedPreferences("OftenMobileJson", Context.MODE_PRIVATE).edit()
//                .putString("OftenMobileJson", new Gson().toJson(hashMap))
//                .apply();
//    }

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
        //  context.getSharedPreferences("OftenMobileJson", Context.MODE_PRIVATE).edit().clear().apply();
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

    public String getShopkeeper(Context context) {
        return context.getSharedPreferences("Shopkeeper", Context.MODE_PRIVATE).getString("Shopkeeper", "");
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

    // 新增或修改会员的手机号与昵称
    public void saveOftenMobile(Context context, String sMemberID, String sMobile, String sNick, String sRemoveKey) {
        Map<String, Map<String, String>> memberData = retrieveOftenMobileData(context, "OftenMobileJson");

        Map<String, String> oftenMobileMap = memberData.get(sMemberID);
        if (oftenMobileMap == null) {
            oftenMobileMap = new HashMap<>();
        }

        if (!sRemoveKey.isEmpty() && oftenMobileMap.containsKey(sRemoveKey)) {
            oftenMobileMap.remove(sRemoveKey);
        }

        oftenMobileMap.put(sMobile, sNick);
        memberData.put(sMemberID, oftenMobileMap);

        saveOftenMobileData(context, "OftenMobileJson", memberData);
    }

    // 移除会员的特定手机号
    public void removeOftenMobile(Context context, String memberId, String sMobile) {
        Map<String, Map<String, String>> memberData = retrieveOftenMobileData(context, "OftenMobileJson");

        Map<String, String> oftenMobileMap = memberData.get(memberId);
        if (oftenMobileMap != null) {
            oftenMobileMap.remove(sMobile);
            memberData.put(memberId, oftenMobileMap);
            saveOftenMobileData(context, "OftenMobileJson", memberData);
        }
    }

    // 移除整个会员的所有数据
    public void removeMemberOftenMobile(Context context, String memberId) {
        Map<String, Map<String, String>> OftenMobileMap = retrieveOftenMobileData(context, "OftenMobileJson");

        OftenMobileMap.remove(memberId);

        saveOftenMobileData(context, "OftenMobileJson", OftenMobileMap);
    }

    // 取得該会员 HashMap
    public HashMap getOftenMobile(Context context, String memberId) {
        Map<String, Map<String, String>> OftenMobileMap = retrieveOftenMobileData(context, "OftenMobileJson");

        if (OftenMobileMap.containsKey(memberId)) {
            return new HashMap(OftenMobileMap.get(memberId));
        } else {
            return new HashMap();
        }
    }

    // 從 SharedPreferences 中提取資料
    public Map<String, Map<String, String>> retrieveOftenMobileData(Context context, String key) {
        Map<String, Map<String, String>> OftenMobileMap = new HashMap<>();
        SharedPreferences sharedPreferences = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        String OftenMobileJson = sharedPreferences.getString(key, "");

        if (!OftenMobileJson.isEmpty()) {
            try {
                JSONObject jsonObject = new JSONObject(OftenMobileJson);
                JSONArray keys = jsonObject.names();

                if (keys != null) {
                    for (int i = 0; i < keys.length(); i++) {
                        String memberId = keys.getString(i);
                        JSONObject innerJsonObject = jsonObject.getJSONObject(memberId);
                        Map<String, String> innerMap = new HashMap<>();

                        JSONArray innerKeys = innerJsonObject.names();
                        if (innerKeys != null) {
                            for (int j = 0; j < innerKeys.length(); j++) {
                                String innerKey = innerKeys.getString(j);
                                String innerValue = innerJsonObject.getString(innerKey);
                                innerMap.put(innerKey, innerValue);
                            }
                        }
                        OftenMobileMap.put(memberId, innerMap);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return OftenMobileMap;
    }

    // 將資料存入 SharedPreferences
    public void saveOftenMobileData(Context context, String key, Map<String, Map<String, String>> OftenMobile) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        JSONObject jsonObject = new JSONObject();

        for (Map.Entry<String, Map<String, String>> entry : OftenMobile.entrySet()) {
            try {
                JSONObject innerJsonObject = new JSONObject();
                Map<String, String> innerMap = entry.getValue();

                for (Map.Entry<String, String> innerEntry : innerMap.entrySet()) {
                    innerJsonObject.put(innerEntry.getKey(), innerEntry.getValue());
                }

                jsonObject.put(entry.getKey(), innerJsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Log.e(TAG, "OftenMobile => Key:" + key + " JSON:" + jsonObject.toString());

        editor.putString(key, jsonObject.toString());
        editor.apply();
    }
}
