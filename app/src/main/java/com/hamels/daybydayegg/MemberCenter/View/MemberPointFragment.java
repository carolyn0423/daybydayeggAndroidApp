package com.hamels.daybydayegg.MemberCenter.View;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hamels.daybydayegg.Base.BaseFragment;
import com.hamels.daybydayegg.Main.View.MainActivity;
import com.hamels.daybydayegg.MemberCenter.Adapter.PointRecordAdapter;
import com.hamels.daybydayegg.MemberCenter.Contract.MemberPointContract;
import com.hamels.daybydayegg.MemberCenter.Presenter.MemberPointPresenter;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.PointHistory;
import com.hamels.daybydayegg.Repository.Model.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MemberPointFragment extends BaseFragment implements MemberPointContract.View{
    public static final String TAG = MemberPointFragment.class.getSimpleName();

    private static MemberPointFragment fragment;
    private MemberPointContract.Presenter memberPresenter;

    private TextView tvTotalPoint , tvdate , tv_no_histort , tvExpireTime;
//    private ImageButton btn_left_arrow, btn_right_arrow;
    private ConstraintLayout layout_left_arrow , layout_right_arrow;

    private Calendar calendar = Calendar.getInstance();
    private RecyclerView recyclerView;
    private PointRecordAdapter pointRecordAdapter;
    private String monthNow;

    public static MemberPointFragment getInstance() {
        if (fragment == null) {
            fragment = new MemberPointFragment();
        }

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_member_point, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        memberPresenter.getPointData();
        memberPresenter.getMemberPoint(calendar.get(Calendar.YEAR)+"-"+monthNow);
    }

    private void initView(View view) {
        ((MainActivity) getActivity()).setAppTitle(R.string.my_point);
        ((MainActivity) getActivity()).setBackButtonVisibility(true);
        ((MainActivity) getActivity()).setMessageButtonVisibility(true);
        ((MainActivity) getActivity()).setMailButtonVisibility(true);
        ((MainActivity) getActivity()).setTopBarVisibility(false);
        ((MainActivity) getActivity()).setAppToolbarVisibility(true);
        ((MainActivity) getActivity()).setMainIndexMessageUnreadVisibility(false);
        ((MainActivity) getActivity()).setCartBadgeVisibility(true);

        String[] vYear = new String[7];
        String[] vMonth = new String[7];
        final int[] count = {0};
        tvdate = view.findViewById(R.id.tv_date);

        int nYear = calendar.get(Calendar.YEAR);
        int nMonth = calendar.get(Calendar.MONTH)+1;

        monthNow=Integer.toString(nMonth);
        if(nMonth < 10){
            monthNow= "0" + nMonth;
        }

        tvdate.setText(nYear+"年"+monthNow+"月");

        final ArrayList<String> YearList = new ArrayList<>();
        final ArrayList<String> MonthList = new ArrayList<>();

        for(int i = 0 ; i < 7 ; i++){
            if(nMonth != 0){
                YearList.add(nYear+"");
                if(nMonth < 10){
                    MonthList.add("0"+nMonth+"");
                }
                else{
                    MonthList.add(nMonth+"");
                }
                nMonth--;
            }
            else{
                nMonth = 12;
                nYear = nYear - 1;
                YearList.add(nYear+"");
                MonthList.add(nMonth+"");
                nMonth--;
            }
        }

        for(int i = 0 ; i < 7 ; i++){
            Log.e(TAG,YearList.get(i)+" " + MonthList.get(i));
        }


        layout_left_arrow = view.findViewById(R.id.layout_left_arrow);
        layout_right_arrow = view.findViewById(R.id.layout_right_arrow);
        layout_left_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count[0] < 6){
                    count[0]++;
                }
                tvdate.setText(YearList.get(count[0])+"年"+MonthList.get(count[0])+"月");
                memberPresenter.getMemberPoint(YearList.get(count[0])+"-"+MonthList.get(count[0]));
            }
        });
        layout_right_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count[0] > 0){
                    count[0]--;
                }
                tvdate.setText(YearList.get(count[0])+"年"+MonthList.get(count[0])+"月");
                memberPresenter.getMemberPoint(YearList.get(count[0])+"-"+MonthList.get(count[0]));
            }
        });

        tvTotalPoint = view.findViewById(R.id.tv_total_point);
        tv_no_histort = view.findViewById(R.id.tv_no_histort);
        tvExpireTime = view.findViewById(R.id.tv_expire_time);
        recyclerView = view.findViewById(R.id.recyclerview);
        memberPresenter = new MemberPointPresenter(this, getRepositoryManager(getContext()));
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setStackFromEnd(false);
        recyclerView.setLayoutManager(manager);
        pointRecordAdapter = new PointRecordAdapter(memberPresenter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(pointRecordAdapter);


    }

    @Override
    public void setPoint(List<PointHistory> pointHistoryList) {
        if(pointHistoryList.size() != 0 ){
            tv_no_histort.setVisibility(View.GONE);
        }
        else{
            tv_no_histort.setVisibility(View.VISIBLE);
        }
        pointRecordAdapter.setList(pointHistoryList);
    }

    @Override
    public void setPointData(User user) {
        tvTotalPoint.setText(user.getPoint());
        tvExpireTime.setText(String.format(getString(R.string.expire_date), Calendar.getInstance().get(Calendar.YEAR) + "/12/31",user.getBonusexpiredsoon()));
    }
}
