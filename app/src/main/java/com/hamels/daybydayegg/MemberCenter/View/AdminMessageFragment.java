package com.hamels.daybydayegg.MemberCenter.View;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.hamels.daybydayegg.Base.BaseFragment;
import com.hamels.daybydayegg.EOrderApplication;
import com.hamels.daybydayegg.Main.View.MainActivity;
import com.hamels.daybydayegg.MemberCenter.Adapter.AdminMessageAdapter;
import com.hamels.daybydayegg.MemberCenter.Contract.AdminMessageContract;
import com.hamels.daybydayegg.MemberCenter.Presenter.AdminMessagePresenter;
import com.hamels.daybydayegg.MemberCenter.Presenter.MessageListPresenter;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.MessageEvent;
import com.hamels.daybydayegg.Repository.Model.MessageGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminMessageFragment extends BaseFragment implements AdminMessageContract.View {
    public static final String TAG = AdminMessageFragment.class.getSimpleName();

    private static AdminMessageFragment fragment;

    private RecyclerView recyclerView;
    private AdminMessageAdapter mAdapter;
    private TextView no_results_text;

    private AdminMessagePresenter mPresenter;
    private Handler handler = new Handler();
    private int iMessageCount = 0;
    public static AdminMessageFragment getInstance() {
        if (fragment == null) {
            fragment = new AdminMessageFragment();
        }

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_message, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(mPresenter.getUserLogin()) {
            queryCustomerServiceAPI();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mPresenter.getUserLogin()) {
            queryCustomerServiceAPI();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        queryCustomerServiceAPI();
    }

    private void initView(View view) {
        ((MainActivity) getActivity()).setAppTitleString("留言回覆");
        ((MainActivity) getActivity()).setBackButtonVisibility(true);
        ((MainActivity) getActivity()).setMessageButtonVisibility(true);
        ((MainActivity) getActivity()).setMailButtonVisibility(true);
        ((MainActivity) getActivity()).setSortButtonVisibility(false);
        ((MainActivity) getActivity()).setTopBarVisibility(false);
        ((MainActivity) getActivity()).setAppToolbarVisibility(true);
        ((MainActivity) getActivity()).setMainIndexMailUnreadVisibility(false);
        ((MainActivity) getActivity()).setCartBadgeVisibility(true);

        mPresenter = new AdminMessagePresenter(this, getRepositoryManager(getContext()));

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        mAdapter = new AdminMessageAdapter(mPresenter);
        recyclerView.setAdapter(mAdapter);

        no_results_text = view.findViewById(R.id.no_results_text);
    }

//    @Override
//    public void showMailDetail(MemberMessage memberMessage) {
//        ((MainActivity) getActivity()).addFragment(MailDetailFragment.getInstance(memberMessage));
//    }

    public void queryCustomerServiceAPI() {
        // 调用查询客服中心API的代码
        // 更新消息列表
        if(mPresenter != null) {
            mPresenter.getMessageList();
        }else{
            mPresenter = new AdminMessagePresenter(this, getRepositoryManager(getContext()));
            mPresenter.getMessageList();
        }
    }

    public void goMessageList(String sReMemberID, String sMobile){
        //  建立 WebSocket
        //  跳轉
        EOrderApplication.WEB_SOCKET_MOBILE_CHK = "";
        EOrderApplication.WEB_SOCKET_MOBILE = sMobile;
        ((MainActivity) getActivity()).addFragment(MessageListFragment.getInstance(sReMemberID, sMobile, "Y"));
    }

    @Override
    public void setMessageList(List<MessageGroup> messageGroups) {
        if(messageGroups.size() == 0){
            no_results_text.setVisibility(View.VISIBLE);
        }else{
            no_results_text.setVisibility(View.GONE);
        }
        mAdapter.setMessagesList(messageGroups);

        if(iMessageCount != messageGroups.size()){
            iMessageCount = messageGroups.size();
            recyclerView.scrollToPosition(messageGroups.size() - 1);
        }
    }
}
