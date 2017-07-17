package com.example.vmm408.taxiuserproject.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vmm408.taxiuserproject.R;
import com.example.vmm408.taxiuserproject.adapters.EndlessScrollListener;
import com.example.vmm408.taxiuserproject.adapters.RecycleViewAdapterOrders;
import com.example.vmm408.taxiuserproject.adapters.RecycleViewAdapterRating;
import com.example.vmm408.taxiuserproject.models.OrderModel;
import com.example.vmm408.taxiuserproject.models.RatingModel;
import com.example.vmm408.taxiuserproject.models.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrdersHistoryFragment extends BaseFragment {
    public static OrdersHistoryFragment newInstance() {
        return new OrdersHistoryFragment();
    }

    @BindView(R.id.text_current_order)
    TextView textCurrentOrder;
    @BindView(R.id.current_order_container)
    LinearLayout currentOrderContainer;
    @BindView(R.id.history_order_container)
    RecyclerView historyOrderContainer;
    private RecycleViewAdapterOrders recycleViewAdapter;
    private LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
    private EndlessScrollListener endlessScrollListener = new EndlessScrollListener(linearLayoutManager) {
        @Override
        public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
            loadMore(page);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_orders_history, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (UserModel.User.getUserModel().getIdCurrentOrder() != null) {
            textCurrentOrder.setVisibility(View.VISIBLE);
            currentOrderContainer.addView(initCurrentOrderView());
        }
        initRecycleView();
        loadMore(0);
    }

    private View initCurrentOrderView() {
        View view = getActivity().getLayoutInflater().inflate(R.layout.item_current_order, null);
        ((TextView) ButterKnife.findById(view, R.id.text_from_order))
                .setText(OrderModel.Order.getOrderModel().getFromOrder());
        ((TextView) ButterKnife.findById(view, R.id.text_destination_order))
                .setText(OrderModel.Order.getOrderModel().getDestinationOrder());
        ((TextView) ButterKnife.findById(view, R.id.text_price_order))
                .setText(String.valueOf(OrderModel.Order.getOrderModel().getPriceOrder()));
        ((TextView) ButterKnife.findById(view, R.id.text_comment_order))
                .setText(OrderModel.Order.getOrderModel().getCommentOrder());
        return view;
    }

    private void initRecycleView() {
        historyOrderContainer.setLayoutManager(linearLayoutManager);
        historyOrderContainer.setAdapter(recycleViewAdapter = new RecycleViewAdapterOrders());
        historyOrderContainer.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        historyOrderContainer.addOnScrollListener(endlessScrollListener);
    }

    private void loadMore(int offset) {
        List<OrderModel> tempList = new ArrayList<>();
        for (int i = 10 * offset; i < (10 * offset) + 10; i++) {
            (mReference = mDatabase.getReference("orders"))
                    .orderByValue()
                    .equalTo(UserModel.User.getUserModel().getIdUser())
                    .equalTo("false", "orderAccepted")
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                System.out.println(snapshot);
                                tempList.add(dataSnapshot.getValue(OrderModel.class));
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
        }
        recycleViewAdapter.addList(tempList);
    }
}
