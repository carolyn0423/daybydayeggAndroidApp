package com.hamels.daybydayegg.Repository;

import android.content.Context;
import android.util.Log;

import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Base.BasePresenter;
import com.hamels.daybydayegg.EOrderApplication;
import com.hamels.daybydayegg.Repository.ApiRepository.ApiAdminRepository;
import com.hamels.daybydayegg.Repository.ApiRepository.ApiRepository;
import com.hamels.daybydayegg.Repository.ApiRepository.MemberRepository;
import com.hamels.daybydayegg.Repository.Model.About;
import com.hamels.daybydayegg.Repository.Model.Address;
import com.hamels.daybydayegg.Repository.Model.BaseModel;
import com.hamels.daybydayegg.Repository.Model.Carousel;
import com.hamels.daybydayegg.Repository.Model.Coupon;
import com.hamels.daybydayegg.Repository.Model.Customer;
import com.hamels.daybydayegg.Repository.Model.Donate;
import com.hamels.daybydayegg.Repository.Model.DonateCart;
import com.hamels.daybydayegg.Repository.Model.DrawLots;
import com.hamels.daybydayegg.Repository.Model.Faq;
import com.hamels.daybydayegg.Repository.Model.Machine;
import com.hamels.daybydayegg.Repository.Model.MemberMessage;
import com.hamels.daybydayegg.Repository.Model.Merchant;
import com.hamels.daybydayegg.Repository.Model.Message;
import com.hamels.daybydayegg.Repository.Model.Order;
import com.hamels.daybydayegg.Repository.Model.OrderProduct;
import com.hamels.daybydayegg.Repository.Model.PointHistory;
import com.hamels.daybydayegg.Repository.Model.Product;
import com.hamels.daybydayegg.Repository.Model.ProductMainType;
import com.hamels.daybydayegg.Repository.Model.ProductType;
import com.hamels.daybydayegg.Repository.Model.Store;
import com.hamels.daybydayegg.Repository.Model.User;
import com.hamels.daybydayegg.Repository.Model.WebSetup;
import com.hamels.daybydayegg.Utils.ApiUtils;
import com.hamels.daybydayegg.Utils.SharedUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.hamels.daybydayegg.Constant.ApiConstant.*;

public class RepositoryManager {
    public static final String TAG = RepositoryManager.class.getSimpleName();
    private Context context;
    private BasePresenter basePresenter;

    public RepositoryManager(Context context) {
        this.context = context;
    }

    public void setPresenter(BasePresenter basePresenter) {
        this.basePresenter = basePresenter;
    }

    public boolean getUserLogin() {
        boolean isLogin = false;

        if(SharedUtils.getInstance().getUser(context) != null && !EOrderApplication.sApiUrl.equals("")){
            isLogin = true;
        }

        if(SharedUtils.getInstance().getUserID(context) == null || SharedUtils.getInstance().getUserID(context).equals("")){
            isLogin = false;
        }

//        return (SharedUtils.getInstance().getUser(context) != null && SharedUtils.getInstance().getVerifyCode(context).equals("Y"));
        return isLogin;
    }

    public void callRegisterApi(User user, String password, String InvitationCode, final BaseContract.ValueCallback<User> valueCallback, final BaseContract.ValueCallback<String> errorCallback) {
        basePresenter.startCallApi();
        MemberRepository.getInstance().register(user, password, InvitationCode, new ApiCallback<BaseModel<User>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<User> response) {
                super.onApiSuccess(response);
                Boolean isSuccess = response.getSuccess();
                String Message = response.getMessage();
                String[] sMessage = Message.split("\\|");
                if (isSuccess) {
                    valueCallback.onValueCallback(TASK_POST_REGISTER, response.getItems());
                } else {
                    errorCallback.onValueCallback(TASK_POST_REGISTER, sMessage[1]);
                }

            }

            @Override
            public void onApiFail(int errorCode, BaseModel failBaseModel) {
                super.onApiFail(errorCode, failBaseModel);

            }
        });
    }

    public void callLoginApi(String customer_id, String account, String password, final BaseContract.ValueCallback<User> valueCallback, final BaseContract.ValueCallback<String> errorCallback) {
        basePresenter.startCallApi();

        ApiRepository.getInstance().login(customer_id, account, password, new ApiCallback<BaseModel<User>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<User> response) {
                super.onApiSuccess(response);

                Boolean isSuccess = response.getSuccess();
                String Message = response.getMessage();
                String[] sMessage = Message.split("\\|");
                //sMessage[1] = L_0X004  <-簡訊尚未完成驗證  .  L_1X000  <-已完成驗證成功登入
                if (isSuccess) {
                    if (sMessage[0].equals("L_1X000")) {  // 登入成功
                        valueCallback.onValueCallback(TASK_POST_LOGIN, response.getItems());
                        RepositoryManager.this.saveUser(response.getItems());
                    } else if (sMessage[0].equals("L_0X004")) { // 尚未完成簡訊驗證
                        valueCallback.onValueCallback(209, response.getItems());
//                        RepositoryManager.this.saveUser(response.getItems());
                    } else { // 其他錯誤訊息
                        errorCallback.onValueCallback(500, sMessage[1]);
                    }
                } else {
                    errorCallback.onValueCallback(500, sMessage[1]);
                }
            }

            @Override
            public void onApiFail(int errorCode, BaseModel failBaseModel) {
                super.onApiFail(errorCode, failBaseModel);
                errorCallback.onValueCallback(failBaseModel.getCode(), failBaseModel.getMessage());
            }
        });
    }

    public void callLogOutApi(final String member_id, final BaseContract.ValueCallback<String> valueCallback) {
        basePresenter.startCallApi();
        MemberRepository.getInstance().logout(member_id, new ApiCallback<BaseModel>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel response) {
                super.onApiSuccess(response);
                SharedUtils.getInstance().removeAllLocalData(context);
                valueCallback.onValueCallback(TASK_POST_LOGOUT, response.getMessage());
            }
        });
    }

    public void callGetMemberInfoApi(String member_id, final BaseContract.ValueCallback<User> valueCallback) {
        basePresenter.startCallApi();
        String customer_id = context.getSharedPreferences("CustomerID", Context.MODE_PRIVATE).getString("CustomerID", "");
        MemberRepository.getInstance().getMemberInfo(customer_id, member_id, new ApiCallback<BaseModel<User>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<User> response) {
                super.onApiSuccess(response);
                RepositoryManager.this.saveUser(response.getItems());
                valueCallback.onValueCallback(TASK_POST_GET_MEMBER_INFO, response.getItems());
            }
        });
    }

    public void callDeleteMemberApi(String member_id, final BaseContract.ValueCallback<String> valueCallback) {
        basePresenter.startCallApi();
        MemberRepository.getInstance().getDeleteMember(member_id, new ApiCallback<BaseModel<String>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<String> response) {
                super.onApiSuccess(response);
                valueCallback.onValueCallback(TASK_POST_DELETE_MEMBER, response.getItems());
            }
        });
    }

    public void callResendSmsApi(final String customer_id,final String account, final BaseContract.ValueCallback<String> valueCallback) {
        basePresenter.startCallApi();
        int iRandom = new Random().nextInt(9999);
        String srandom;
        srandom = Integer.toString(iRandom);
        if (iRandom < 1000) {
            srandom = "0" + srandom;
        }
        context.getSharedPreferences("SmsCode", Context.MODE_PRIVATE).edit()
                .putString("SmsCode", srandom)
                .apply();

        MemberRepository.getInstance().resendSms(customer_id, account, srandom, "DoRegister", new ApiCallback<BaseModel>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel response) {
                super.onApiSuccess(response);
//                valueCallback.onValueCallback(TASK_POST_RESEND_SMS, response.getMessage());
                valueCallback.onValueCallback(TASK_POST_RESEND_SMS, "簡訊驗證發送成功");
            }
        });
    }

    public void callVerifySmsApi(final String verifyCode, final String member_id, final BaseContract.ValueCallback<String> valueCallback, final BaseContract.ValueCallback<String> errorCallback) {
        String SmsCode = context.getSharedPreferences("SmsCode", Context.MODE_PRIVATE).getString("SmsCode", "");
        String customer_id = context.getSharedPreferences("CustomerID", Context.MODE_PRIVATE).getString("CustomerID", "");
        if (verifyCode.equals(SmsCode)) {
            basePresenter.startCallApi();
            MemberRepository.getInstance().verifySms(customer_id,member_id, new ApiCallback<BaseModel>(basePresenter) {
                @Override
                public void onApiSuccess(BaseModel response) {
                    super.onApiSuccess(response);
                    Boolean isSuccess = response.getSuccess();
                    String Message = response.getMessage();
                    String[] sMessage = Message.split("\\|");
                    if (isSuccess) {
                        if (sMessage[0].equals("V_1X000") || sMessage[0].equals("V_1X001") ||
                                sMessage[0].equals("V_0X001") || sMessage[0].equals("V_0X002")) {
                            valueCallback.onValueCallback(TASK_POST_VERIFY_SMS, sMessage[1]);
                        } else {
                            // L_0X007:很抱歉，目前網路不穩定，帳號驗證失敗，請重試驗證或等待10分鐘後再重試。若持續發生錯誤，請通知服務人員，謝謝。
                            // L_0X008:POS來的錯誤訊息
                            valueCallback.onValueCallback(TOTAL_ERROR_CODE, sMessage[1]);
                        }
                    } else {
                        valueCallback.onValueCallback(TOTAL_ERROR_CODE, sMessage[1]);
                    }
                }

                @Override
                public void onApiFail(int errorCode, BaseModel failBaseModel) {
                    super.onApiFail(errorCode, failBaseModel);
                    errorCallback.onValueCallback(TASK_POST_VERIFY_SMS, failBaseModel.getMessage());
                }
            });
        } else {
            errorCallback.onValueCallback(500, "驗證碼錯誤");
        }
    }

    public void callCheckAccountApi(String customer_id, String cellphone, final BaseContract.ValueCallback<String> valueCallback, final BaseContract.ValueCallback<String> errorCallback) {
        basePresenter.startCallApi();
        MemberRepository.getInstance().checkAccount(customer_id, cellphone, new ApiCallback<BaseModel>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel response) {
                super.onApiSuccess(response);
                Boolean isSuccess = response.getSuccess();
                String Message = response.getMessage();
                String[] sMessage = Message.split("\\|");
                //sMessage[1] = L_0X006  <-無此會員資料，請先註冊帳號。  .  0X002  <-新增失敗
                //              1X000  <-操作成功1X002   .  1X002 <-新增成功
                if (isSuccess) {
                    valueCallback.onValueCallback(RESPONSE_CODE_LOGIN_VERIFIED_ERROR, sMessage[1]);
                } else {
                    errorCallback.onValueCallback(TOTAL_ERROR_CODE, sMessage[1]);
                }
            }

            @Override
            public void onApiFail(int errorCode, BaseModel failBaseModel) {
                super.onApiFail(errorCode, failBaseModel);
                errorCallback.onValueCallback(failBaseModel.getCode(), failBaseModel.getMessage());
            }
        });
    }

    public void callForgetPsdSmsApi(String customer_id,String cellphone, final BaseContract.ValueCallback<String> valueCallback, final BaseContract.ValueCallback<String> errorCallback) {
        basePresenter.startCallApi();
        int iRandom = new Random().nextInt(9999);
        String srandom;
        srandom = Integer.toString(iRandom);
        if (iRandom < 1000) {
            srandom = "0" + srandom;
        }
        context.getSharedPreferences("SmsCode", Context.MODE_PRIVATE).edit()
                .putString("SmsCode", srandom)
                .apply();
        MemberRepository.getInstance().resendSms(customer_id,cellphone, srandom, "DoResetPassword", new ApiCallback<BaseModel>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel response) {
                super.onApiSuccess(response);
                Boolean isSuccess = response.getSuccess();
                String[] aMessage = response.getMessage().split("\\|");
                //sMessage[1] = L_0X006  <-無此會員資料，請先註冊帳號  .  L_0X000  <-無此帳號
                if (isSuccess) {
                    valueCallback.onValueCallback(TASK_POST_FORGET_PASSWORD, aMessage[1]);
                } else {
                    errorCallback.onValueCallback(TASK_PATCH_UPDATE_PASSWORD, aMessage[1]);
                }
            }

            @Override
            public void onApiFail(int errorCode, BaseModel failBaseModel) {
                super.onApiFail(errorCode, failBaseModel);
                errorCallback.onValueCallback(TASK_POST_FORGET_PASSWORD, failBaseModel.getMessage());
            }
        });
    }

    public void callVerifyForgetPsdApi(String customer_id, String cellphone, String password, String verifyCode, final BaseContract.ValueCallback<String> valueCallback, final BaseContract.ValueCallback<String> errorCallback) {
        String SmsCode = context.getSharedPreferences("SmsCode", Context.MODE_PRIVATE).getString("SmsCode", "");
        if (verifyCode.equals(SmsCode)) {
            basePresenter.startCallApi();
            MemberRepository.getInstance().verifyForgetPassword(customer_id, cellphone, password, new ApiCallback<BaseModel>(basePresenter) {
                @Override
                public void onApiSuccess(BaseModel response) {
                    super.onApiSuccess(response);
                    Boolean isSuccess = response.getSuccess();
                    String[] aMessage = response.getMessage().split("\\|");
                    if (isSuccess) {
                        valueCallback.onValueCallback(TASK_POST_FORGET_PASSWORD, aMessage[1]);
                    } else {
                        errorCallback.onValueCallback(TASK_PATCH_UPDATE_PASSWORD, aMessage[1]);
                    }
                }

                @Override
                public void onApiFail(int errorCode, BaseModel failBaseModel) {
                    super.onApiFail(errorCode, failBaseModel);
                    errorCallback.onValueCallback(TASK_POST_FORGET_PASSWORD_VERIFY, failBaseModel.getMessage());
                }
            });
        } else {
            errorCallback.onValueCallback(500, "驗證碼錯誤");
        }
    }

    public void callAdminGetPropertyDataApi(final BaseContract.ValueCallback<List<Address>> valueCallback) {
        basePresenter.startCallApi();
        String member_id = context.getSharedPreferences("MemberID", Context.MODE_PRIVATE).getString("MemberID", "");
        ApiAdminRepository.getInstance().getPropertyData(new ApiCallback<BaseModel<List<Address>>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<List<Address>> response) {
                super.onApiSuccess(response);
                valueCallback.onValueCallback(TASK_POST_GET_PROPERTY_CODE, response.getItems());
            }
        });
    }

    public void callGetPropertyDataApi(final BaseContract.ValueCallback<List<Address>> valueCallback) {
        basePresenter.startCallApi();
        String member_id = context.getSharedPreferences("MemberID", Context.MODE_PRIVATE).getString("MemberID", "");
        MemberRepository.getInstance().getPropertyData(member_id, new ApiCallback<BaseModel<List<Address>>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<List<Address>> response) {
                super.onApiSuccess(response);
                valueCallback.onValueCallback(TASK_POST_GET_PROPERTY_CODE, response.getItems());
            }
        });
    }

    public void callGetCarouselListApi(String sCustomer_id, final BaseContract.ValueCallback<List<Carousel>> valueCallback) {
        basePresenter.startCallApi();
        MemberRepository.getInstance().getCarouselList(sCustomer_id, new ApiCallback<BaseModel<List<Carousel>>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<List<Carousel>> response) {
                super.onApiSuccess(response);
                valueCallback.onValueCallback(TASK_POST_GET_CAROUSE, response.getItems());
            }
        });
    }

    public void callGetAdminCarouselListApi(final BaseContract.ValueCallback<List<Carousel>> valueCallback) {
        basePresenter.startCallApi();
        ApiAdminRepository.getInstance().getCarouselList(new ApiCallback<BaseModel<List<Carousel>>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<List<Carousel>> response) {
                super.onApiSuccess(response);
                valueCallback.onValueCallback(TASK_POST_GET_CAROUSE, response.getItems());
            }
        });
    }

    public void callGetMerchantListApi(final BaseContract.ValueCallback<List<Merchant>> valueCallback) {
        basePresenter.startCallApi();
        String customer_id = context.getSharedPreferences("CustomerID", Context.MODE_PRIVATE).getString("CustomerID", "");
        MemberRepository.getInstance().getMerchantList(customer_id,new ApiCallback<BaseModel<List<Merchant>>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<List<Merchant>> response) {
                super.onApiSuccess(response);
                valueCallback.onValueCallback(TASK_POST_GET_MERCHANT, response.getItems());
            }
        });
    }

    public void callGetProductMainTypeListApi(final String location_id, final String customer_id, final BaseContract.ValueCallback<List<ProductMainType>> valueCallback) {
        basePresenter.startCallApi();
        MemberRepository.getInstance().getProductMainTypeList(location_id, customer_id, new ApiCallback<BaseModel<List<ProductMainType>>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<List<ProductMainType>> response) {
                super.onApiSuccess(response);
                valueCallback.onValueCallback(TASK_POST_GET_PRODUCT_MAIN_TYPE, response.getItems());
            }
        });
    }

    public void callGetProductTypeListApi(final int product_type_main_id, final BaseContract.ValueCallback<List<ProductType>> valueCallback) {
        basePresenter.startCallApi();
        MemberRepository.getInstance().getProductTypeList(product_type_main_id, new ApiCallback<BaseModel<List<ProductType>>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<List<ProductType>> response) {
                super.onApiSuccess(response);
                valueCallback.onValueCallback(TASK_POST_GET_PRODUCT_TYPE, response.getItems());
            }
        });
    }

    public void callGetLotListApi(final BaseContract.ValueCallback<List<DrawLots>> valueCallback) {
        basePresenter.startCallApi();
        String member_id = context.getSharedPreferences("MemberID", Context.MODE_PRIVATE).getString("MemberID", "");
        MemberRepository.getInstance().getLotList(member_id, new ApiCallback<BaseModel<List<DrawLots>>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<List<DrawLots>> response) {
                super.onApiSuccess(response);
                if (response.getSuccess()) {
                    valueCallback.onValueCallback(TASK_POST_GET_LOT_LIST, response.getItems());
                } else {
                    valueCallback.onValueCallback(TOTAL_ERROR_CODE, response.getItems());
                }
            }
        });
    }

//    public void getShoppingCartCountApi(final BaseContract.ValueCallback<List<Object>> valueCallback) {
//        basePresenter.startCallApi();
//        String member_id = context.getSharedPreferences("MemberID", Context.MODE_PRIVATE).getString("MemberID", "");
//        MemberRepository.getInstance().getShoppingCartCount(member_id , new ApiCallback<BaseModel<List<Object>>>(basePresenter) {
//            @Override
//            public void onApiSuccess(BaseModel<List<Object>> response) {
//                super.onApiSuccess(response);
//                valueCallback.onValueCallback(TASK_POST_GET_SHOPPING_COUNT, response.getItems());
//            }
//        });
//    }

    public void callGetProductListApi(final String location_id,final String business_sale_id, final String sort, final String product_type_main_id, final String product_type_id, final String product_name, final String e_ticket, final BaseContract.ValueCallback<List<Product>> valueCallback) {
        basePresenter.startCallApi();
        String customer_id = context.getSharedPreferences("CustomerID", Context.MODE_PRIVATE).getString("CustomerID", "");
        MemberRepository.getInstance().getProductList(customer_id,location_id,business_sale_id, sort, product_type_main_id, product_type_id, product_name, e_ticket, new ApiCallback<BaseModel<List<Product>>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<List<Product>> response) {
                super.onApiSuccess(response);
                valueCallback.onValueCallback(TASK_POST_GET_PRODUCT_LIST, response.getItems());
            }
        });
    }

    public void callGetProductDetailApi(final String business_sale_id, final String product_id, final String e_ticket, final BaseContract.ValueCallback<List<Product>> valueCallback) {
        basePresenter.startCallApi();
        String member_id = context.getSharedPreferences("MemberID", Context.MODE_PRIVATE).getString("MemberID", "");
        MemberRepository.getInstance().getProductDetail(business_sale_id, member_id, product_id, e_ticket, new ApiCallback<BaseModel<List<Product>>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<List<Product>> response) {
                super.onApiSuccess(response);
                valueCallback.onValueCallback(TASK_POST_GET_PRODUCT_DETAIL, response.getItems());
            }
        });
    }

    public void callGetDonateListApi(final BaseContract.ValueCallback<List<Donate>> valueCallback) {
        basePresenter.startCallApi();
        String member_id = context.getSharedPreferences("MemberID", Context.MODE_PRIVATE).getString("MemberID", "");
        MemberRepository.getInstance().getDonateList(member_id, new ApiCallback<BaseModel<List<Donate>>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<List<Donate>> response) {
                super.onApiSuccess(response);
                valueCallback.onValueCallback(TASK_POST_GET_DONATE_LIST, response.getItems());
            }
        });
    }

    public void callGetTicketCartApi(final BaseContract.ValueCallback<List<DonateCart>> valueCallback) {
        basePresenter.startCallApi();
        String member_id = context.getSharedPreferences("MemberID", Context.MODE_PRIVATE).getString("MemberID", "");
        MemberRepository.getInstance().getTicketCart(member_id, new ApiCallback<BaseModel<List<DonateCart>>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<List<DonateCart>> response) {
                super.onApiSuccess(response);
                valueCallback.onValueCallback(TASK_POST_GET_DONATE_LIST, response.getItems());
            }
        });
    }

    public void callUpdateTicketCartCode(String cart_ticket_code, final BaseContract.ValueCallback<Boolean> valueCallback) {
        basePresenter.startCallApi();
        String member_id = context.getSharedPreferences("MemberID", Context.MODE_PRIVATE).getString("MemberID", "");
        MemberRepository.getInstance().updateTicketCartCode(member_id, cart_ticket_code, new ApiCallback<BaseModel<Boolean>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<Boolean> response) {
                super.onApiSuccess(response);
                Log.e(TAG, response.toString());
                valueCallback.onValueCallback(TASK_POST_GET_DONATE_LIST, response.getSuccess());
            }
        });
    }

    public void callUpdateTicketCartApi(final String action, final String product_id, final String spec_id, final String give_date, final BaseContract.ValueCallback<Boolean> valueCallback) {
        basePresenter.startCallApi();
        String member_id = context.getSharedPreferences("MemberID", Context.MODE_PRIVATE).getString("MemberID", "");
        MemberRepository.getInstance().updateTicketCart(member_id, action, product_id, spec_id, give_date, new ApiCallback<BaseModel<Boolean>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<Boolean> response) {
                super.onApiSuccess(response);
                Log.e(TAG, response.toString());
                valueCallback.onValueCallback(TASK_POST_GET_DONATE_LIST, response.getSuccess());
            }
        });
    }

    public void callGetTicketUsedHistoryApi(final BaseContract.ValueCallback<List<Donate>> valueCallback) {
        basePresenter.startCallApi();
        String member_id = context.getSharedPreferences("MemberID", Context.MODE_PRIVATE).getString("MemberID", "");
        MemberRepository.getInstance().GetTicketUsedHistory(member_id, new ApiCallback<BaseModel<List<Donate>>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<List<Donate>> response) {
                super.onApiSuccess(response);
                Log.e(TAG, response.toString());
                valueCallback.onValueCallback(TASK_POST_GET_DONATE_LIST, response.getItems());
            }
        });
    }

    public void callGetDonateDetailApi(final String uid, final BaseContract.ValueCallback<List<Donate>> valueCallback) {
        basePresenter.startCallApi();
        String member_id = context.getSharedPreferences("MemberID", Context.MODE_PRIVATE).getString("MemberID", "");
        MemberRepository.getInstance().getDonateDetail(member_id, uid, new ApiCallback<BaseModel<List<Donate>>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<List<Donate>> response) {
                super.onApiSuccess(response);
                valueCallback.onValueCallback(TASK_POST_GET_DONATE_DETAIL, response.getItems());
            }
        });
    }

    public void callGetAppDetailApi(final String writeoff_order_id, final String eticket_due_date, final String modified_date, final BaseContract.ValueCallback<List<Donate>> valueCallback) {
        basePresenter.startCallApi();
        String member_id = context.getSharedPreferences("MemberID", Context.MODE_PRIVATE).getString("MemberID", "");
        MemberRepository.getInstance().GetAppDetail(member_id, writeoff_order_id, eticket_due_date, modified_date, new ApiCallback<BaseModel<List<Donate>>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<List<Donate>> response) {
                super.onApiSuccess(response);
                valueCallback.onValueCallback(TASK_POST_GET_DONATE_DETAIL, response.getItems());
            }
        });
    }

    public void callChkPhoneExistApi(final String mobile, final BaseContract.ValueCallback<Boolean> valueCallback) {
        basePresenter.startCallApi();
        String customer_id = context.getSharedPreferences("CustomerID", Context.MODE_PRIVATE).getString("CustomerID", "");
        MemberRepository.getInstance().ChkPhoneExist(customer_id,mobile, new ApiCallback<BaseModel<List<Donate>>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<List<Donate>> response) {
                super.onApiSuccess(response);
                valueCallback.onValueCallback(TASK_POST_GET_DONATE_DETAIL, response.getSuccess());
            }
        });
    }

    public void callSaveTicketDataApi(final String uid, final String mobile, final String quantity, final BaseContract.ValueCallback<Boolean> valueCallback) {
        basePresenter.startCallApi();
        String member_id = context.getSharedPreferences("MemberID", Context.MODE_PRIVATE).getString("MemberID", "");
        String customer_id = context.getSharedPreferences("CustomerID", Context.MODE_PRIVATE).getString("CustomerID", "");
        MemberRepository.getInstance().SaveTicketData(customer_id,member_id, uid, mobile, quantity, new ApiCallback<BaseModel<List<Donate>>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<List<Donate>> response) {
                super.onApiSuccess(response);
                valueCallback.onValueCallback(TASK_POST_GET_DONATE_DETAIL, response.getSuccess());
            }
        });
    }

    public void callGiveTicketGiftByCartApi(final String mobile, final BaseContract.ValueCallback<Boolean> valueCallback) {
        basePresenter.startCallApi();
        String member_id = context.getSharedPreferences("MemberID", Context.MODE_PRIVATE).getString("MemberID", "");
        String customer_id = context.getSharedPreferences("CustomerID", Context.MODE_PRIVATE).getString("CustomerID", "");
        MemberRepository.getInstance().GiveTicketGiftByCart(customer_id,member_id, mobile, new ApiCallback<BaseModel<List<Donate>>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<List<Donate>> response) {
                super.onApiSuccess(response);
                valueCallback.onValueCallback(TASK_POST_GET_DONATE_DETAIL, response.getSuccess());
            }
        });
    }

    public void callSavePushApi(final String sendmember_id, final String title, final String content, final BaseContract.ValueCallback<Boolean> valueCallback) {
        basePresenter.startCallApi();
        String member_id = context.getSharedPreferences("MemberID", Context.MODE_PRIVATE).getString("MemberID", "");
        MemberRepository.getInstance().SavePush(member_id, sendmember_id, title, content, new ApiCallback<BaseModel<Donate>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<Donate> response) {
                super.onApiSuccess(response);
                valueCallback.onValueCallback(TASK_POST_GET_DONATE_DETAIL, response.getSuccess());
            }
        });
    }

    public void callgetMemberInfoAssignmemberApi(String keyword, final BaseContract.ValueCallback<User> valueCallback) {
        basePresenter.startCallApi();
        String member_id = context.getSharedPreferences("MemberID", Context.MODE_PRIVATE).getString("MemberID", "");
        String customer_id = context.getSharedPreferences("CustomerID", Context.MODE_PRIVATE).getString("CustomerID", "");
        MemberRepository.getInstance().getMemberInfoAssignmember(customer_id,member_id, keyword, new ApiCallback<BaseModel<User>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<User> response) {
                super.onApiSuccess(response);
                Log.e(TAG, response.toString());
                valueCallback.onValueCallback(TASK_POST_GET_MEMBER_INFO, response.getItems());
            }
        });
    }

    public void callGetLotDetailApi(final int lot_id, final BaseContract.ValueCallback<DrawLots> valueCallback) {
        basePresenter.startCallApi();
        String member_id = context.getSharedPreferences("MemberID", Context.MODE_PRIVATE).getString("MemberID", "");
        MemberRepository.getInstance().getLotDetail(lot_id, member_id, new ApiCallback<BaseModel<DrawLots>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<DrawLots> response) {
                super.onApiSuccess(response);
                valueCallback.onValueCallback(TASK_POST_GET_LOTDATA_DETAIL, response.getItems());
            }
        });
    }

    public void callJoinDrawLotsApi(final int lot_id, final String cif_code, final String prod_id, final String spec_name, final BaseContract.ValueCallback<Boolean> valueCallback) {
        basePresenter.startCallApi();
        String member_id = context.getSharedPreferences("MemberID", Context.MODE_PRIVATE).getString("MemberID", "");
        MemberRepository.getInstance().joinDrawLots(lot_id, cif_code, prod_id, spec_name, member_id, new ApiCallback<BaseModel<Boolean>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel response) {
                super.onApiSuccess(response);
                Log.e(TAG, response.toString());
                valueCallback.onValueCallback(TASK_POST_JOIN_LOT, response.getSuccess());
            }
        });
    }

    public void callGetBusinessCodeApi(final String sale_password, final BaseContract.ValueCallback<String> valueCallback) {
        basePresenter.startCallApi();
        MemberRepository.getInstance().getBusinessCode(sale_password, new ApiCallback<BaseModel<Map<String, String>>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<Map<String, String>> response) {
                super.onApiSuccess(response);
                if (response.getSuccess()) {
                    Map<String, String> map = response.getItems();
                    if (map.containsKey("business_sale_id")) {
                        valueCallback.onValueCallback(TASK_POST_GET_BUSINESS_CODE, response.getItems().get("business_sale_id"));
                    } else {
                        valueCallback.onValueCallback(TASK_POST_GET_BUSINESS_CODE_FALSE, response.getMessage().split("\\|")[1]);
                    }
                } else {
                    valueCallback.onValueCallback(TASK_POST_GET_BUSINESS_CODE_FALSE, response.getMessage().split("\\|")[1]);
                }
            }
        });
    }

    public void callAddShoppingCattApi(final String business_sale_id, final String sale_type, final String product_id, final String spec_id, final String location_id, final String quantity, final String order_type, final String conf_list, final BaseContract.ValueCallback<String> valueCallback) {
        basePresenter.startCallApi();
        String customer_id = context.getSharedPreferences("CustomerID", Context.MODE_PRIVATE).getString("CustomerID", "");
        String member_id = context.getSharedPreferences("MemberID", Context.MODE_PRIVATE).getString("MemberID", "");
        MemberRepository.getInstance().addShoppingCart(customer_id,business_sale_id, sale_type, member_id, product_id, spec_id, location_id, quantity, order_type, conf_list, new ApiCallback<BaseModel>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel response) {
                super.onApiSuccess(response);
                valueCallback.onValueCallback(TASK_POST_ADD_SHOPPING_CART, response.getMessage());
            }
        });
    }

    public void callUpdatePasswordApi(String oldPassword, String newPassword, final BaseContract.ValueCallback<String> valueCallback, final BaseContract.ValueCallback<String> errorCallback) {
        basePresenter.startCallApi();
        String customer_id = context.getSharedPreferences("CustomerID", Context.MODE_PRIVATE).getString("CustomerID", "");
        String member_id = context.getSharedPreferences("MemberID", Context.MODE_PRIVATE).getString("MemberID", "");
        MemberRepository.getInstance().updatePassword(customer_id,member_id, oldPassword, newPassword, new ApiCallback<BaseModel<User>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<User> response) {
                super.onApiSuccess(response);
                Boolean isSuccess = response.getSuccess();
                String[] aMessage = response.getMessage().split("\\|");
                if (isSuccess) {
                    valueCallback.onValueCallback(TASK_PATCH_UPDATE_PASSWORD, aMessage[1]);
                } else {
                    errorCallback.onValueCallback(TASK_PATCH_UPDATE_PASSWORD, aMessage[1]);
                }
            }

            @Override
            public void onApiFail(int errorCode, BaseModel failBaseModel) {
                super.onApiFail(errorCode, failBaseModel);
                errorCallback.onValueCallback(TASK_PATCH_UPDATE_PASSWORD, failBaseModel.getMessage());
            }
        });
    }

    public void callUpdateMemberInfoApi(String customer_id, String city_code, String area_code, String address, String email, String birth, String carrier_no, final BaseContract.ValueCallback<String> valueCallback) {
        basePresenter.startCallApi();
        String member_id = context.getSharedPreferences("MemberID", Context.MODE_PRIVATE).getString("MemberID", "");
        MemberRepository.getInstance().updateMemberInfo(customer_id, member_id, city_code, area_code, address, email, birth, carrier_no, new ApiCallback<BaseModel>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel response) {
                super.onApiSuccess(response);
                valueCallback.onValueCallback(TASK_PUT_UPDATE_MEMBER, response.getMessage().split("\\|")[1]);
            }
        });
    }


    public void callMemberPointHistoryApi(final String date, final BaseContract.ValueCallback<List<PointHistory>> valueCallback) {
        basePresenter.startCallApi();
        String member_id = context.getSharedPreferences("MemberID", Context.MODE_PRIVATE).getString("MemberID", "");
        MemberRepository.getInstance().getMemberPointHistory(date, member_id, new ApiCallback<BaseModel<List<PointHistory>>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<List<PointHistory>> response) {
                super.onApiSuccess(response);
                Log.e(TAG, response.toString());
                valueCallback.onValueCallback(TASK_POST_GET_MEMBER_POINT, response.getItems());
            }
        });
    }

    public void callMemberMessageListApi(final BaseContract.ValueCallback<List<MemberMessage>> valueCallback) {
        basePresenter.startCallApi();
        String member_id = context.getSharedPreferences("MemberID", Context.MODE_PRIVATE).getString("MemberID", "");
        String customer_id = context.getSharedPreferences("CustomerID", Context.MODE_PRIVATE).getString("CustomerID", "");
        MemberRepository.getInstance().getMemberMessage(customer_id,member_id, new ApiCallback<BaseModel<List<MemberMessage>>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<List<MemberMessage>> response) {
                super.onApiSuccess(response);
                valueCallback.onValueCallback(TASK_POST_GET_MEMBER_MESSAGE_LIST, response.getItems());
            }
        });
    }

    public void callAboutDataApi(final BaseContract.ValueCallback<List<About>> valueCallback) {
        basePresenter.startCallApi();
        String customer_id = context.getSharedPreferences("CustomerID", Context.MODE_PRIVATE).getString("CustomerID", "");
        MemberRepository.getInstance().getAboutData(customer_id,new ApiCallback<BaseModel<List<About>>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<List<About>> response) {
                super.onApiSuccess(response);
                valueCallback.onValueCallback(TASK_POST_GET_ABOUT_DATA, response.getItems());
            }
        });
    }

    public void callFaqDataApi(final String faq_type_id, final BaseContract.ValueCallback<Faq> valueCallback) {
        basePresenter.startCallApi();
        String customer_id = context.getSharedPreferences("CustomerID", Context.MODE_PRIVATE).getString("CustomerID", "");
        MemberRepository.getInstance().getFaqData(customer_id, faq_type_id, new ApiCallback<BaseModel<Faq>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<Faq> response) {
                super.onApiSuccess(response);
                valueCallback.onValueCallback(TASK_POST_GET_FAQ_DATA, response.getItems());
            }
        });
    }

    public void callUpdateMessageStatusApi(int messageId, String status, final BaseContract.ValueCallback<String> valueCallback) {
//        basePresenter.startCallApi();
        String member_id = context.getSharedPreferences("MemberID", Context.MODE_PRIVATE).getString("MemberID", "");
        MemberRepository.getInstance().updateMessageStatus(member_id, messageId, status, new ApiCallback<BaseModel>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel response) {
                super.onApiSuccess(response);
                valueCallback.onValueCallback(TASK_POST_UPDATE_MESSAGE_STATUS, response.getMessage());
            }
        });
    }

    public void callGetOrdersApi(final BaseContract.ValueCallback<List<Order>> valueCallback) {
        basePresenter.startCallApi();
        MemberRepository.getInstance().getOrders(new ApiCallback<BaseModel<List<Order>>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<List<Order>> response) {
                super.onApiSuccess(response);
                valueCallback.onValueCallback(TASK_POST_GET_MEMBER_ORDERS, response.getItems());
            }
        });
    }

    public void callGetMailBadgeApi(final BaseContract.ValueCallback<String> valueCallback) {
//        basePresenter.startCallApi();
        String member_id = context.getSharedPreferences("MemberID", Context.MODE_PRIVATE).getString("MemberID", "");
        MemberRepository.getInstance().getMailBadge(member_id,new ApiCallback<BaseModel<List<Map<String, String>>>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<List<Map<String, String>>> response) {
                super.onApiSuccess(response);
                if (response.getSuccess()) {
                    List<Map<String, String>> map = response.getItems();
                    if (map.get(0).containsKey("unReadNum")) {
                        valueCallback.onValueCallback(TASK_POST_GET_MAIL_BADGE, response.getItems().get(0).get("unReadNum"));
                    } else {
                        valueCallback.onValueCallback(TASK_POST_GET_MAIL_BADGE, "0");
                    }
                } else {
                    valueCallback.onValueCallback(TASK_POST_GET_MAIL_BADGE, "0");
                }
            }
        });
    }

    public void callGetMessageBadgeApi(final BaseContract.ValueCallback<String> valueCallback) {
//        basePresenter.startCallApi();
        String member_id = context.getSharedPreferences("MemberID", Context.MODE_PRIVATE).getString("MemberID", "");
        MemberRepository.getInstance().getMessageBadge(member_id,new ApiCallback<BaseModel<List<Map<String, String>>>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<List<Map<String, String>>> response) {
                super.onApiSuccess(response);
//                if (response.getCode() == 200) {
                if (response.getSuccess()) {
                    List<Map<String, String>> map = response.getItems();
                    if (map.get(0).containsKey("unReadNum")) {
                        valueCallback.onValueCallback(TASK_POST_GET_MESSAGE_BADGE, response.getItems().get(0).get("unReadNum"));
//                        valueCallback.onValueCallback(TASK_POST_GET_MAIL_BADGE, "");
                    } else {
                        valueCallback.onValueCallback(TASK_POST_GET_MESSAGE_BADGE, "0");
                    }
                } else {
                    valueCallback.onValueCallback(TASK_POST_GET_MESSAGE_BADGE, "0");
                }
            }
        });
    }


    public void callGetBadgeNumberApi(final BaseContract.ValueCallback<String> valueCallback) {
        String customer_id = context.getSharedPreferences("CustomerID", Context.MODE_PRIVATE).getString("CustomerID", "");
        String member_id = context.getSharedPreferences("MemberID", Context.MODE_PRIVATE).getString("MemberID", "");
        MemberRepository.getInstance().getBadgeNumber(customer_id, member_id, new ApiCallback<BaseModel<List<Map<String, String>>>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<List<Map<String, String>>> response) {
                super.onApiSuccess(response);
                if (response.getSuccess()) {
                    List<Map<String, String>> map = response.getItems();
                    String messageUnreadNum = "0", pushUnreadNum = "0", e_ticket_cart_total_quantity = "0", cartTotalQuantity = "0", sCouponNum = "", sPointNum = "";
                    if (map.get(0).containsKey("message_unread_num")) {
                        messageUnreadNum = response.getItems().get(0).get("message_unread_num");
                    }
                    if (map.get(0).containsKey("push_unread_num")) {
                        pushUnreadNum = response.getItems().get(0).get("push_unread_num");
                    }
                    if (map.get(0).containsKey("e_ticket_cart_total_quantity")) {
                        e_ticket_cart_total_quantity = response.getItems().get(0).get("e_ticket_cart_total_quantity");
                        if (e_ticket_cart_total_quantity != null) {
                            if (e_ticket_cart_total_quantity.equals("")) {
                                e_ticket_cart_total_quantity = "0";
                            }
                        }
                    }
                    if (map.get(0).containsKey("cart_total_quantity")) {
                        cartTotalQuantity = response.getItems().get(0).get("cart_total_quantity");
                        if (cartTotalQuantity != null) {
                            if (cartTotalQuantity.equals("")) {
                                cartTotalQuantity = "0";
                            }
                        }
                    }
                    if (map.get(0).containsKey("coupon_num")) {
                        sCouponNum = response.getItems().get(0).get("coupon_num");
                        if (sCouponNum != null) {
                            if (sCouponNum.equals("")) {
                                sCouponNum = "0";
                            }
                        }
                    }
                    if (map.get(0).containsKey("point_num")) {
                        sPointNum = response.getItems().get(0).get("point_num");
                        if (sPointNum != null) {
                            if (sPointNum.equals("")) {
                                sPointNum = "0";
                            }
                        }
                    }
                    valueCallback.onValueCallback(TASK_POST_GET_MESSAGE_BADGE, messageUnreadNum + "_" + pushUnreadNum + "_" + e_ticket_cart_total_quantity + "_" + cartTotalQuantity + "_" + sCouponNum + "_" + sPointNum);
                } else {
                    valueCallback.onValueCallback(TASK_POST_GET_MESSAGE_BADGE, "0_0_0_0_0_0");
                }
            }
        });
    }

    public void callGetCustomerApi(String sCity, String sCustomerName, final BaseContract.ValueCallback<List<Customer>> valueCallback) {
        basePresenter.startCallApi();
        ApiAdminRepository.getInstance().getCustomerList(sCity, sCustomerName, new ApiCallback<BaseModel<List<Customer>>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<List<Customer>> response) {
                super.onApiSuccess(response);

                Log.e(TAG, "onApiSuccess : " + response);
                valueCallback.onValueCallback(TASK_POST_GET_CUSTOMER_LIST, response.getItems());
            }
        });
    }

    public void callGetCustomerDetailListApi(String sCustomerIDList, final BaseContract.ValueCallback<List<Customer>> valueCallback) {
        basePresenter.startCallApi();
        ApiAdminRepository.getInstance().getCustomerDetailList(sCustomerIDList, new ApiCallback<BaseModel<List<Customer>>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<List<Customer>> response) {
                super.onApiSuccess(response);

                Log.e(TAG, "onApiSuccess : " + response);
                valueCallback.onValueCallback(TASK_POST_GET_CUSTOMER_LIST, response.getItems());
            }
        });
    }

    public void callGetCustomerDetailApi(String sCustomerID, final BaseContract.ValueCallback<Customer> valueCallback) {
        basePresenter.startCallApi();
        ApiAdminRepository.getInstance().getCustomerDetail(sCustomerID, "CustomerData", new ApiCallback<BaseModel<Customer>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel <Customer> response) {
                super.onApiSuccess(response);

                Log.e(TAG, "onApiSuccess : " + response);
                valueCallback.onValueCallback(TASK_POST_GET_CUSTOMER_DETAIL, response.getItems());
            }
        });
    }

    public void callGetCustomerByNoApi(String sCustomerNo, final BaseContract.ValueCallback<Customer> valueCallback) {
        basePresenter.startCallApi();
        ApiAdminRepository.getInstance().getCustomerNo(sCustomerNo, new ApiCallback<BaseModel<Customer>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel <Customer> response) {
                super.onApiSuccess(response);

                Log.e(TAG, "onApiSuccess : " + response);
                valueCallback.onValueCallback(TASK_POST_GET_CUSTOMER_DETAIL, response.getItems());
            }
        });
    }

    public void callGetLocationApi(String functionname, String sCustomerID, String location_id, String sKilometer, String sHeadLocationFlag, final BaseContract.ValueCallback<List<Store>> valueCallback) {
        basePresenter.startCallApi();
        String member_id = getUserLogin() ? context.getSharedPreferences("MemberID", Context.MODE_PRIVATE).getString("MemberID", "") : "";
        ApiRepository.getInstance().getLocationList(functionname, sCustomerID, member_id, location_id, sKilometer, sHeadLocationFlag, new ApiCallback<BaseModel<List<Store>>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<List<Store>> response) {
                super.onApiSuccess(response);

                Log.e(TAG, "onApiSuccess : " + response);
                valueCallback.onValueCallback(TASK_POST_GET_LOCATION_LIST, response.getItems());
            }

//            @Override
//            public void onApiFail(int errorCode, BaseModel failBaseModel) {
//                super.onApiFail(errorCode, failBaseModel);
//                valueCallback.onValueCallback(TASK_FAIL, failBaseModel.getMessage());
//            }
        });
    }

    public void callGetMachineApi(String functionname, String sCustomerID, final BaseContract.ValueCallback<List<Machine>> valueCallback) {
        basePresenter.startCallApi();
        String member_id = getUserLogin() ? context.getSharedPreferences("MemberID", Context.MODE_PRIVATE).getString("MemberID", "") : "";
        ApiRepository.getInstance().getMachineList(functionname, sCustomerID, member_id, new ApiCallback<BaseModel<List<Machine>>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<List<Machine>> response) {
                super.onApiSuccess(response);

                Log.e(TAG, "onApiSuccess : " + response);
                valueCallback.onValueCallback(TASK_POST_GET_LOCATION_LIST, response.getItems());
            }

//            @Override
//            public void onApiFail(int errorCode, BaseModel failBaseModel) {
//                super.onApiFail(errorCode, failBaseModel);
//                valueCallback.onValueCallback(TASK_FAIL, failBaseModel.getMessage());
//            }
        });
    }
    public void callSetMachineOftenApi(String machine_id, String uid, final BaseContract.ValueCallback<Boolean> valueCallback) {
        basePresenter.startCallApi();
        String member_id = context.getSharedPreferences("MemberID", Context.MODE_PRIVATE).getString("MemberID", "");
        ApiRepository.getInstance().setMachineOften(member_id, uid, machine_id, new ApiCallback<BaseModel>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel response) {
                super.onApiSuccess(response);

                Log.e(TAG, "onApiSuccess : " + response);
                valueCallback.onValueCallback(TASK_POST_GET_LOCATION_LIST, response.getSuccess());
            }
        });
    }
    public void callSetLocationOftenApi(String location_id, String uid, final BaseContract.ValueCallback<Boolean> valueCallback) {
        basePresenter.startCallApi();
        String member_id = context.getSharedPreferences("MemberID", Context.MODE_PRIVATE).getString("MemberID", "");
        ApiRepository.getInstance().setLocationOften(member_id, location_id, uid, new ApiCallback<BaseModel>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel response) {
                super.onApiSuccess(response);

                Log.e(TAG, "onApiSuccess : " + response);
                valueCallback.onValueCallback(TASK_POST_GET_LOCATION_LIST, response.getSuccess());
            }
        });
    }

    public void callGetOnlineVision(String sSysName, final BaseContract.ValueCallback<WebSetup> valueCallback) {
        basePresenter.startCallApi();
        ApiAdminRepository.getInstance().getWebConfig(sSysName, new ApiCallback<BaseModel<WebSetup>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel <WebSetup> response) {
                super.onApiSuccess(response);

                Log.e(TAG, "onApiSuccess : " + response);
                valueCallback.onValueCallback(TASK_POST_GET_WEB_CONFIG, response.getItems());
            }
        });
    }

    public void callReturnProductsApi(final ArrayList<OrderProduct> products, final String name, final String address,
                                      final String mobile, final String email, final int returnType, final BaseContract.ValueCallback<String> valueCallback) {
        basePresenter.startCallApi();
        MemberRepository.getInstance().returnProducts(products, name, address, mobile, email, returnType, new ApiCallback<BaseModel>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel response) {
                super.onApiSuccess(response);
                valueCallback.onValueCallback(TASK_POST_RETURN_ORDER_SUCCESS, response.getMessage());
            }

            @Override
            public void onApiFail(int errorCode, BaseModel failBaseModel) {
                super.onApiFail(errorCode, failBaseModel);
                valueCallback.onValueCallback(TASK_FAIL, failBaseModel.getMessage());
            }
        });
    }

    public void callGetMessageListApi(final BaseContract.ValueCallback<List<Message>> valueCallback) {
        basePresenter.startCallApi();
        String member_id = context.getSharedPreferences("MemberID", Context.MODE_PRIVATE).getString("MemberID", "");
        MemberRepository.getInstance().getMessageList(member_id, new ApiCallback<BaseModel<List<Message>>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<List<Message>> response) {
                super.onApiSuccess(response);
                valueCallback.onValueCallback(TASK_POST_GET_MESSAGE_LIST, response.getItems());
            }
        });
    }

    public void callSendMessageApi(String message, final BaseContract.ValueCallback<Boolean> valueCallback) {
        basePresenter.startCallApi();
        String customer_id = context.getSharedPreferences("CustomerID", Context.MODE_PRIVATE).getString("CustomerID", "");
        String member_id = context.getSharedPreferences("MemberID", Context.MODE_PRIVATE).getString("MemberID", "");
        MemberRepository.getInstance().addMemberContact(customer_id,member_id, message, new ApiCallback<BaseModel>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel response) {
                super.onApiSuccess(response);
                valueCallback.onValueCallback(TASK_POST_ADD_MEMBER_CONTACT, true);
            }
        });
    }

    public void callReadMessageApi(final BaseContract.ValueCallback<Boolean> valueCallback) {
        basePresenter.startCallApi();
        String member_id = context.getSharedPreferences("MemberID", Context.MODE_PRIVATE).getString("MemberID", "");
        MemberRepository.getInstance().updateReadMessage(member_id, new ApiCallback<BaseModel>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel response) {
                super.onApiSuccess(response);
                valueCallback.onValueCallback(TASK_POST_READ_MESSAGE, true);
            }
        });
    }

    public void callGetRegisterCouponApi(String member_id, final BaseContract.ValueCallback<Coupon> valueCallback) {
        basePresenter.startCallApi();
        MemberRepository.getInstance().GetRegisterCoupon(member_id, new ApiCallback<BaseModel<Coupon>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<Coupon> response) {
                super.onApiSuccess(response);
                valueCallback.onValueCallback(TASK_POST_GET_MEMBER_INFO, response.getItems());
            }
        });
    }

    public void GetShopCartLocationQuantity(final BaseContract.ValueCallback<String> valueCallback) {
        basePresenter.startCallApi();
        String member_id = context.getSharedPreferences("MemberID", Context.MODE_PRIVATE).getString("MemberID", "");
        MemberRepository.getInstance().GetShopCartLocationQuantity(member_id, new ApiCallback<BaseModel<String>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<String> response) {
                super.onApiSuccess(response);
                valueCallback.onValueCallback(TASK_POST_GET_SHOPCARTLOCATIONQUANTITY, response.getItems());
            }
        });
    }

    public void saveAccountInfo(String account, String password) { SharedUtils.getInstance().saveAccountInfo(context, ApiUtils.encryption(account), ApiUtils.encryption(password)); }

    public void saveUserID(String member_id) { SharedUtils.getInstance().saveUserID(context, member_id); }

    public void saveUserName(String member_name) { SharedUtils.getInstance().saveUserName(context, member_name); }

    public Boolean saveLoveCustomer(String customer_id) {
        String sLoveCustomerID = getLoveCustomer();
        boolean isLove = false;

        //  初始化
        if(!sLoveCustomerID.contains("|")){
            sLoveCustomerID = "";
        }

        if(!sLoveCustomerID.contains("|" + customer_id + "|")){
            //  無相符 點擊增加
            sLoveCustomerID += "|" + customer_id + "|";
            isLove = true;
        }else{
            //  相符 點擊減少
            StringBuilder newCustomerID = new StringBuilder();
            String[] Customer = sLoveCustomerID.split("\\|");
            for (String s : Customer) {
                if (!s.equals("") && !s.equals("|") && !s.equals(customer_id)) {
                    newCustomerID.append("|").append(s).append("|");
                }
            }
            sLoveCustomerID = newCustomerID.toString();
        }

        SharedUtils.getInstance().saveLoveCustomer(context, sLoveCustomerID);
        String[] aCustomer = sLoveCustomerID.split("\\|");
        if(aCustomer.length == 0 || sLoveCustomerID.equals("")){
            SharedUtils.getInstance().saveCustomerID(context, "");
        }

        return isLove;
    }

    public void saveCustomerID(String customer_id) { SharedUtils.getInstance().saveCustomerID(context, customer_id); }

    public void saveCustomerName(String customer_name) { SharedUtils.getInstance().saveCustomerName(context, customer_name); }

    public void saveApiUrl(String sApiUrl) { SharedUtils.getInstance().saveApiUrl(context, sApiUrl); }

    public void saveBusinessSaleID(String business_sale_id) { SharedUtils.getInstance().saveBusinessSaleID(context, business_sale_id); }

    public void saveShoppingCartCount(String Count) { SharedUtils.getInstance().saveShoppingCartCount(context, Count); }

    public void saveVerifyCode(String verifyCode) { SharedUtils.getInstance().saveVerifyCode(context, verifyCode); }

    public void saveInvitationCode(String InvitationCode) { SharedUtils.getInstance().saveInvitationCode(context, InvitationCode); }

    public void saveSourceActive(String sSourceActive) { SharedUtils.getInstance().saveSourceActive(context, sSourceActive); }

    public void saveFragmentLocation(String sLocationID) { SharedUtils.getInstance().saveFragmentLocation(context, sLocationID); }

    public void saveFragmentMainType(String sLocationID, String sIsETicket) { SharedUtils.getInstance().saveFragmentMainType(context, sLocationID, sIsETicket); }

    public void savePaySchemeOrderData(String sPaySchemeOrderData) { SharedUtils.getInstance().savePaySchemeOrderData(context, sPaySchemeOrderData); }

    public void saveUser(User user) {
        SharedUtils.getInstance().saveUser(context, user);
    }

    /* ------------------------------------------------------------------------- */

    public User getUser() {
        return SharedUtils.getInstance().getUser(context);
    }

    public String getUserAccount() { return ApiUtils.decryption(SharedUtils.getInstance().getUserAccount(context)).trim(); }

    public String getUserID() { return SharedUtils.getInstance().getUserID(context); }

    public String getUserName() { return SharedUtils.getInstance().getUserName(context); }

    public String getLoveCustomer() { return SharedUtils.getInstance().getLoveCustomer(context); }

    public String getCustomerID() { return SharedUtils.getInstance().getCustomerID(context); }

    public String getCustomerName() { return SharedUtils.getInstance().getCustomerName(context); }

    public String getApiUrl() { return SharedUtils.getInstance().getApiUrl(context); }

    public String getBusinessSaleID() { return SharedUtils.getInstance().getBusinessSaleID(context); }

    public String getShoppingCartCount() { return SharedUtils.getInstance().getShoppingCartCount(context); }

    public String getUserPassword() { return ApiUtils.decryption(SharedUtils.getInstance().getUserPassword(context)).trim(); }

    public String getVerifyCode() { return SharedUtils.getInstance().getVerifyCode(context); }

    public String getInvitationCode() { return SharedUtils.getInstance().getInvitationCode(context); }

    public String getSourceActive() { return SharedUtils.getInstance().getSourceActive(context); }

    public String getFragmentLocation() { return SharedUtils.getInstance().getFragmentLocation(context); }

    public String getFragmentMainType(String sParam) { return SharedUtils.getInstance().getFragmentMainType(context, sParam); }

    public String getPaySchemeOrderData() { return SharedUtils.getInstance().getPaySchemeOrderData(context); }
}
