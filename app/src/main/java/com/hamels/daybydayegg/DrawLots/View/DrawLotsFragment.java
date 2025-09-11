package com.hamels.daybydayegg.DrawLots.View;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hamels.daybydayegg.Base.BaseFragment;
import com.hamels.daybydayegg.DrawLots.Adapter.DrawLotsListAdapter;
import com.hamels.daybydayegg.DrawLots.Contract.DrawLotsContract;
import com.hamels.daybydayegg.DrawLots.Presenter.DrawLotsPresenter;
import com.hamels.daybydayegg.Main.View.MainActivity;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.DrawLots;

import java.util.List;

public class DrawLotsFragment extends BaseFragment implements DrawLotsContract.View{
    public static final String TAG = DrawLotsFragment.class.getSimpleName();

    private static DrawLotsFragment fragment;
    private DrawLotsContract.Presenter drawlotsPresenter;
    private RecyclerView recyclerView;
    private ConstraintLayout constraintLayoutEmpty;
    private DrawLotsListAdapter drawlotsListAdapter;


    public static DrawLotsFragment getInstance() {
        if (fragment == null) {
            fragment = new DrawLotsFragment();
        }
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drawlots, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        ((MainActivity) getActivity()).setAppTitle(R.string.tab_lot);
        ((MainActivity) getActivity()).setBackButtonVisibility(true);
        ((MainActivity) getActivity()).setMessageButtonVisibility(true);
        ((MainActivity) getActivity()).setMailButtonVisibility(true);
        ((MainActivity) getActivity()).setSortButtonVisibility(false);
        ((MainActivity) getActivity()).setTopBarVisibility(false);
        ((MainActivity) getActivity()).setAppToolbarVisibility(true);
        //((MainActivity) getActivity()).setMainIndexMailUnreadVisibility(false);
        ((MainActivity) getActivity()).setCartBadgeVisibility(true);
        recyclerView = view.findViewById(R.id.dwawlots_recycler_view);
        constraintLayoutEmpty = view.findViewById(R.id.layout_empty);
        drawlotsPresenter = new DrawLotsPresenter(this, getRepositoryManager(getContext()));
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setStackFromEnd(false);
        recyclerView.setLayoutManager(manager);
        drawlotsListAdapter = new DrawLotsListAdapter(drawlotsPresenter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(drawlotsListAdapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        drawlotsPresenter.getLotList();
    }


    @Override
    public void setLotList(List<DrawLots> drawLotsList) {
        if (drawLotsList.size()>0){
            recyclerView.setVisibility(View.VISIBLE);
            constraintLayoutEmpty.setVisibility(View.GONE);
            drawlotsListAdapter.setDrawlotslist(drawLotsList);
            recyclerView.scrollToPosition(0);
        }else{
            recyclerView.setVisibility(View.GONE);
            constraintLayoutEmpty.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void goLotDetail(int lot_id) {
        ((MainActivity) getActivity()).addFragment(LotDetailFragment.getInstance(lot_id));
    }
}



