package com.hamels.daybydayegg.MemberCenter.View;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hamels.daybydayegg.Base.BaseFragment;
import com.hamels.daybydayegg.Main.View.MainActivity;
import com.hamels.daybydayegg.MemberCenter.Adapter.NoReadMessageListAdapter;
import com.hamels.daybydayegg.MemberCenter.Contract.NoReadMessageListContract;
import com.hamels.daybydayegg.MemberCenter.Presenter.NoReadMessageListPresenter;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.Message;

import java.util.List;

public class NoReadMessageListFragment extends BaseFragment implements NoReadMessageListContract.View {
    public static final String TAG = NoReadMessageListFragment.class.getSimpleName();

    private static NoReadMessageListFragment fragment;
    private RecyclerView recyclerView;

    private NoReadMessageListAdapter mAdapter;
    private NoReadMessageListContract.Presenter mPresenter;
    private Handler handler = new Handler();
    private int iMessageCount = 0;
    public static NoReadMessageListFragment getInstance() {
        if (fragment == null) {
            fragment = new NoReadMessageListFragment();
        }
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_noread_message_list, container, false);
        initView(view);

        mPresenter = new NoReadMessageListPresenter(this, getRepositoryManager(getContext()));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mPresenter.getUserLogin()){
            startAutoRefresh();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacksAndMessages(null); // 取消所有待执行的任务
    }

    private void initView(View view) {
        ((MainActivity) getActivity()).setAppTitle(R.string.message_list);
        ((MainActivity) getActivity()).setBackButtonVisibility(true);
        ((MainActivity) getActivity()).setMessageButtonVisibility(true);
        ((MainActivity) getActivity()).setMailButtonVisibility(true);
        ((MainActivity) getActivity()).setSortButtonVisibility(false);
        ((MainActivity) getActivity()).setTopBarVisibility(false);
        ((MainActivity) getActivity()).setAppToolbarVisibility(true);
        ((MainActivity) getActivity()).setMainIndexMailUnreadVisibility(false);
        ((MainActivity) getActivity()).setCartBadgeVisibility(true);

        recyclerView = view.findViewById(R.id.item_location);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setStackFromEnd(false);
        recyclerView.setLayoutManager(manager);

        mAdapter = new NoReadMessageListAdapter();
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);
    }

    private void startAutoRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // 在此处执行API请求以刷新数据
                mPresenter.getMessageList();
                // 完成后再次调度自动刷新
                startAutoRefresh();
            }
        }, 1000);
    }

    @Override
    public void setMessageList(List<Message> list) {
        mAdapter.setMessages(list);
        mPresenter.updateReadMessageApi();

        if(iMessageCount != list.size()){
            iMessageCount = list.size();
            recyclerView.scrollToPosition(list.size() - 1);
        }
    }
}
