package com.hamels.daybydayegg.Order.View;

import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.Base.BaseFragment;
import com.hamels.daybydayegg.Main.View.MainActivity;
import com.hamels.daybydayegg.Order.Adapter.TransRecordPagerAdapter;
import com.hamels.daybydayegg.Order.Presenter.TransRecordPresenter;
import com.hamels.daybydayegg.Order.Contract.TransRecordContract;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.Order;

import java.util.Objects;

public class TransRecordFragment extends BaseFragment implements TransRecordContract.View, BaseContract.ValueCallback<Integer> {
    public static final String TAG = TransRecordFragment.class.getSimpleName();

    private static TransRecordFragment fragment;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TransRecordPagerAdapter transRecordPagerAdapter;
    private TransRecordContract.Presenter transPresenter;

    public static TransRecordFragment getInstance() {
        if (fragment == null) {
            fragment = new TransRecordFragment();
        }

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trans_record, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        transPresenter.getOrders();
    }

    private void initView(View view) {
        ((MainActivity) getActivity()).setAppTitle(R.string.trans_record);
        ((MainActivity) getActivity()).setBackButtonVisibility(true);
        ((MainActivity) getActivity()).setMessageButtonVisibility(true);
        ((MainActivity) getActivity()).setMailButtonVisibility(true);
        ((MainActivity) getActivity()).setTopBarVisibility(false);
        ((MainActivity) getActivity()).setAppToolbarVisibility(true);
        ((MainActivity) getActivity()).setMainIndexMessageUnreadVisibility(false);
        ((MainActivity) getActivity()).setCartBadgeVisibility(true);

        tabLayout = view.findViewById(R.id.layout_tab);
        viewPager = view.findViewById(R.id.view_pager);
        transRecordPagerAdapter = new TransRecordPagerAdapter(getChildFragmentManager(), getContext());
        viewPager.setAdapter(transRecordPagerAdapter);
        tabLayout.setupWithViewPager(viewPager, true);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                transRecordPagerAdapter.getItem(position).setList(transPresenter.getStatusOrders(position));
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        transPresenter = new TransRecordPresenter(this, getRepositoryManager(getContext()));
    }

    @Override
    public void addReturnFragment(Order order, int status) {
        ReturnListFragment fragment = ReturnListFragment.getInstance(order, status);
        fragment.setTransRecordTabCallback(this);
        ((MainActivity) Objects.requireNonNull(getActivity())).addFragment(fragment);
    }

    @Override
    public void onDestroy() {
        fragment = null;
        super.onDestroy();
    }

    @Override
    public void onValueCallback(int task, Integer page) {
        setStatusPage(page);
    }

    @Override
    public void setStatusPage(final int page) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                viewPager.setCurrentItem(page, true);
            }
        });
    }

    @Override
    public void onOrderListReadyCallback() {
        int position = viewPager.getCurrentItem();
        transRecordPagerAdapter.getItem(position).setList(transPresenter.getStatusOrders(position));
    }
}
