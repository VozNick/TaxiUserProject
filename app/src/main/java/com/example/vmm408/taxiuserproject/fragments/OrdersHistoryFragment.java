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

import com.example.vmm408.taxiuserproject.CustomValueEventListener;
import com.example.vmm408.taxiuserproject.R;
import com.example.vmm408.taxiuserproject.adapters.EndlessScrollListener;
import com.example.vmm408.taxiuserproject.adapters.RecycleViewAdapterOrders;
import com.example.vmm408.taxiuserproject.models.OrderModel;
import com.example.vmm408.taxiuserproject.models.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.example.vmm408.taxiuserproject.FirebaseDataBaseKeys.ORDERS_REF_KEY;

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
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_orders_history, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (UserModel.User.getUserModel().getIdCurrentOrder() != null) {
            textCurrentOrder.setVisibility(View.VISIBLE);
            currentOrderContainer.addView(super.initCurrentOrderView());
        }
        initRecycleView();
        loadMore(0);
    }

    private void initRecycleView() {
        historyOrderContainer.setLayoutManager(linearLayoutManager);
        historyOrderContainer.setAdapter(recycleViewAdapter = new RecycleViewAdapterOrders());
        historyOrderContainer.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        historyOrderContainer.addOnScrollListener(endlessScrollListener);
    }

    private void loadMore(int offset) {
        List<OrderModel> tempList = new ArrayList<>();
        reference = database.getReference(ORDERS_REF_KEY).child(UserModel.User.getUserModel().getIdUser());
        Query query = reference.startAt(null, String.valueOf(10 * offset)).limitToFirst(10);
        query.addValueEventListener(new CustomValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println(dataSnapshot);
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    tempList.add(snapshot.getValue(OrderModel.class));
                }
                recycleViewAdapter.addList(tempList);
            }
        });
    }
}
