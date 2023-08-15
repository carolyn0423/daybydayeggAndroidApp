package com.hamels.daybydayegg.DrawLots.Presenter;

import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Base.BasePresenter;
import com.hamels.daybydayegg.DrawLots.Contract.LotDetailContract;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.DrawLots;
import com.hamels.daybydayegg.Repository.RepositoryManager;

import java.util.Arrays;


public class LotDetailPresenter extends BasePresenter<LotDetailContract.View> implements LotDetailContract.Presenter {
    public static final String TAG = LotDetailPresenter.class.getSimpleName();

    public LotDetailPresenter(LotDetailContract.View view, RepositoryManager repositoryManager) {
        super(view, repositoryManager);
    }

    @Override
    public void getLotDetailData(int lot_id) {
        repositoryManager.callGetLotDetailApi(lot_id ,  new BaseContract.ValueCallback<DrawLots>() {
            @Override
            public void onValueCallback(int task, DrawLots type) {
                view.setLotDetail(type);
            }
        });
    }

    @Override
    public void checkIdentity(String lot_type , String is_draw , String cif_code , String prod_id , String spec_name) {
        if (is_draw.equals("Y")) {
            view.ErrorAlert(R.string.repeat_join);
        }
        else if (lot_type.equals("1")) {
            if (repositoryManager.getUser().getGroup().equals("V")) {
                checkJoinData(cif_code , prod_id , spec_name);
            }
            else {
                view.ErrorAlert(R.string.member_group_error);
            }
        }
        else if (lot_type.equals("2")){
            checkJoinData(cif_code , prod_id , spec_name);
        }
        else{
            view.ErrorAlert(R.string.sys_error);
        }
    }


    private void checkJoinData(String cif_code , String prod_id , String spec_name){
        if(cif_code.equals("")){
            view.ErrorAlert(R.string.cif_empty);
        }

        else if(!checkCardId(cif_code)){
            view.ErrorAlert(R.string.cif_error);
        }

        else if(prod_id.equals("") || spec_name.equals("")){
            view.ErrorAlert(R.string.check_prod_spec);
        }
        else{
            view.showCheckAlert();
        }
    }

    private boolean checkCardId(String id) {
        if (!id.matches("[a-zA-Z][1-2][0-9]{8}")) {
            return false;
        }

        String newId = id.toUpperCase();
        //身分證第一碼代表數值
        int[] headNum = new int[]{
                1, 10, 19, 28, 37,
                46, 55, 64, 39, 73,
                82, 2, 11, 20, 48,
                29, 38, 47, 56, 65,
                74, 83, 21, 3, 12, 30};

        char[] headCharUpper = new char[]{
                'A', 'B', 'C', 'D', 'E', 'F', 'G',
                'H', 'I', 'J', 'K', 'L', 'M', 'N',
                'O', 'P', 'Q', 'R', 'S', 'T', 'U',
                'V', 'W', 'X', 'Y', 'Z'
        };

        int index = Arrays.binarySearch(headCharUpper, newId.charAt(0));
        int base = 8;
        int total = 0;
        for (int i = 1; i < 10; i++) {
            int tmp = Integer.parseInt(Character.toString(newId.charAt(i))) * base;
            total += tmp;
            base--;
        }

        total += headNum[index];
        int remain = total % 10;
        int checkNum = (10 - remain) % 10;
        if (Integer.parseInt(Character.toString(newId.charAt(9))) != checkNum) {
            return false;
        }
        return true;
    }

    @Override
    public void joinLot(int lot_id, String cif_code, String prod_id, String spec_name) {
        repositoryManager.callJoinDrawLotsApi(lot_id , cif_code , prod_id , spec_name ,  new BaseContract.ValueCallback<Boolean>() {
            @Override
            public void onValueCallback(int task, Boolean type) {
                if(type){
                    view.showSuccessMessage(R.string.success_drawlots);
                }
                else{
                    view.ErrorAlert(R.string.failed_drawlots);
                }
            }
        });
    }
}
