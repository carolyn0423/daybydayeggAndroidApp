package com.hamels.daybydayegg.MemberCenter.View;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.hamels.daybydayegg.Base.BaseFragment;
import com.hamels.daybydayegg.EOrderApplication;
import com.hamels.daybydayegg.Main.View.MainActivity;
import com.hamels.daybydayegg.MemberCenter.Adapter.MessageListAdapter;
import com.hamels.daybydayegg.MemberCenter.Contract.MessageListContract;
import com.hamels.daybydayegg.MemberCenter.Presenter.MessageListPresenter;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.Message;
import com.hamels.daybydayegg.Repository.Model.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class MessageListFragment extends BaseFragment implements MessageListContract.View {
    public static final String TAG = MessageListFragment.class.getSimpleName();

    private static MessageListFragment fragment;
    private RecyclerView recyclerView;
    private EditText etMessage;
    private ImageButton btnSend;

    private MessageListAdapter messageListAdapter;
    private MessageListContract.Presenter messagePresenter;
    private Handler handler = new Handler();
    private int iMessageCount = 0;
    private String sMemberID = "", sMobile = "";
    boolean isAdmin = false;
    //  WebSocket
    public static MessageListFragment getInstance(String parmMemberID, String sMobile, String isAdmin) {
        if (fragment == null) {
            fragment = new MessageListFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putString("MEMBERID", parmMemberID);
        bundle.putString("MOBILE", sMobile);
        bundle.putString("ISADMIN", isAdmin);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_message_list, container, false);
        sMemberID = getArguments().getString("MEMBERID", "");
        sMobile = getArguments().getString("MOBILE", "");
        isAdmin = getArguments().getString("ISADMIN", "").equals("Y");
        initView(view);

        if(messagePresenter == null) {
            messagePresenter = new MessageListPresenter(this, getRepositoryManager(getContext()));
        }

        EOrderApplication.WEB_SOCKET_MOBILE = sMobile;
        ((MainActivity) getActivity()).CreateWebSocket();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
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
        if(isAdmin){
            ((MainActivity) getActivity()).setAppTitleString("回覆留言");
        }else {
            ((MainActivity) getActivity()).setAppTitle(R.string.message_list);
        }
        ((MainActivity) getActivity()).setAppTitle(R.string.message_list);
        ((MainActivity) getActivity()).setBackButtonVisibility(true);
        ((MainActivity) getActivity()).setMessageButtonVisibility(true);
        ((MainActivity) getActivity()).setMailButtonVisibility(true);
        ((MainActivity) getActivity()).setSortButtonVisibility(false);
        ((MainActivity) getActivity()).setTopBarVisibility(false);
        ((MainActivity) getActivity()).setAppToolbarVisibility(true);
        //((MainActivity) getActivity()).setMainIndexMailUnreadVisibility(false);
        ((MainActivity) getActivity()).setCartBadgeVisibility(true);

        etMessage = view.findViewById(R.id.et_message);
        btnSend = view.findViewById(R.id.btn_send);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = etMessage.getText().toString();
                if (!msg.isEmpty()) {
                    if(isAdmin){
                        messagePresenter.reSendMessage(sMemberID, msg);
                    }else {
                        messagePresenter.sendMessage(sMemberID, msg);
                    }

                    ((MainActivity) getActivity()).sendMessageToWebSocket(sMemberID);
                    etMessage.setText("");
                }
            }
        });

        recyclerView = view.findViewById(R.id.product_recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setStackFromEnd(false);
        recyclerView.setLayoutManager(manager);
        messageListAdapter = new MessageListAdapter();
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(messageListAdapter);

        queryCustomerServiceAPI();
    }

    public void queryCustomerServiceAPI() {
        // 调用查询客服中心API的代码
        // 更新消息列表
        if(messagePresenter != null) {
            messagePresenter.getMessageList(sMemberID);
        }else{
            messagePresenter = new MessageListPresenter(this, getRepositoryManager(getContext()));
            messagePresenter.getMessageList(sMemberID);
        }
    }

    @Override
    public void setMessageList(List<Message> list) {
        messageListAdapter.setMessages(list);
        messagePresenter.updateReadMessageApi();

//        if (iMessageCount != messageListAdapter.getItemCount()) {
//            iMessageCount = messageListAdapter.getItemCount();
//            recyclerView.smoothScrollToPosition(messageListAdapter.getItemCount() - 1);
//        }

        int itemCount = messageListAdapter.getItemCount();
        if (itemCount > 0) {
            recyclerView.smoothScrollToPosition(itemCount - 1);
        }
    }
}
