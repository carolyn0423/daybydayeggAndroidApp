package com.hamels.daybydayegg.MemberCenter.View;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.hamels.daybydayegg.Base.BaseFragment;
import com.hamels.daybydayegg.Main.View.MainActivity;
import com.hamels.daybydayegg.MemberCenter.Adapter.MessageListAdapter;
import com.hamels.daybydayegg.MemberCenter.Contract.MessageListContract;
import com.hamels.daybydayegg.MemberCenter.Presenter.MessageListPresenter;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.Message;

import java.util.List;

public class MessageListFragment extends BaseFragment implements MessageListContract.View {
    public static final String TAG = MessageListFragment.class.getSimpleName();

    private static MessageListFragment fragment;
    private RecyclerView recyclerView;
    private EditText etMessage;
    private ImageButton btnSend;

    private MessageListAdapter messageListAdapter;
    private MessageListContract.Presenter messagePresenter;

    public static MessageListFragment getInstance() {
        if (fragment == null) {
            fragment = new MessageListFragment();
        }
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_message_list, container, false);
        initView(view);

        messagePresenter = new MessageListPresenter(this, getRepositoryManager(getContext()));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        messagePresenter.getMessageList();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void initView(View view) {
        ((MainActivity) getActivity()).setAppTitle(R.string.message_list);
        ((MainActivity) getActivity()).setBackButtonVisibility(true);
        ((MainActivity) getActivity()).setMessageButtonVisibility(true);
        ((MainActivity) getActivity()).setMailButtonVisibility(true);
        ((MainActivity) getActivity()).setSortButtonVisibility(false);
        ((MainActivity) getActivity()).setTopBarVisibility(false);
        ((MainActivity) getActivity()).setAppToolbarVisibility(true);
        ((MainActivity) getActivity()).setMainIndexMessageUnreadVisibility(false);
        ((MainActivity) getActivity()).setCartBadgeVisibility(true);

        etMessage = view.findViewById(R.id.et_message);
        btnSend = view.findViewById(R.id.btn_send);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = etMessage.getText().toString();
                if (!msg.isEmpty()) {
                    messagePresenter.sendMessage(msg);
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
    }

    @Override
    public void setMessageList(List<Message> list) {
        messageListAdapter.setMessages(list);
        recyclerView.scrollToPosition(list.size() - 1);
        messagePresenter.updateReadMessageApi();
    }
}
