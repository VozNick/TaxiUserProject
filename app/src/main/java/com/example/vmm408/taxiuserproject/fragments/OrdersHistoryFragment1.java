//package com.example.vmm408.taxiuserproject.fragments;
//
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v7.widget.DividerItemDecoration;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.example.vmm408.taxiuserproject.CustomValueEventListener;
//import com.example.vmm408.taxiuserproject.R;
//import com.example.vmm408.taxiuserproject.adapters.EndlessScrollListener;
//import com.example.vmm408.taxiuserproject.adapters.RecycleViewAdapterOrders;
//import com.example.vmm408.taxiuserproject.models.OrderModel;
//import com.example.vmm408.taxiuserproject.models.UserModel;
//import com.google.firebase.database.ChildEventListener;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.Query;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import butterknife.BindView;
//
//import static com.example.vmm408.taxiuserproject.FirebaseDataBaseKeys.ORDERS_REF_KEY;
//
//public class OrdersHistoryFragment extends BaseFragment {
//    @BindView(R.id.text_current_order)
//    TextView textCurrentOrder;
//    @BindView(R.id.current_order_container)
//    LinearLayout currentOrderContainer;
//    @BindView(R.id.history_order_container)
//    RecyclerView historyOrderContainer;
//    private RecycleViewAdapterOrders recycleViewAdapter;
//    private LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//    private int size;
//    private int count;
//    String lastItemFromBase;
//    private List<OrderModel> tempList = new ArrayList<>();
//    private EndlessScrollListener endlessScrollListener = new EndlessScrollListener(linearLayoutManager) {
//        @Override
//        public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
//            loadMore(page);
//        }
//    };
////    private ValueEventListener getArchiveOrders = new CustomValueEventListener() {
////        @Override
////        public void onDataChange(DataSnapshot dataSnapshot) {
////            List<OrderModel> tempList = new ArrayList<>();
//////            size = (int) dataSnapshot.getChildrenCount();
////            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//////                if (count < size) {
////                tempList.add(snapshot.getValue(OrderModel.class));
//////                    count++;
//////                }
////            }
////            recycleViewAdapter.addList(tempList);
////        }
////    };
//
//    private ChildEventListener getArchiveOrders = new ChildEventListener() {
//        @Override
//        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//            System.out.println("onChildAdded_snap_:" + dataSnapshot);
//            System.out.println("onChildAdded_string_:" + s);
////            tempList.add(dataSnapshot.getValue(OrderModel.class));
//            recycleViewAdapter.addItem(dataSnapshot.getValue(OrderModel.class));
//            lastItemFromBase = s;
//        }
//
//        @Override
//        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//            System.out.println("onChildChanged_snap_:" + dataSnapshot);
//            System.out.println("onChildChanged_string_:" + s);
//        }
//
//        @Override
//        public void onChildRemoved(DataSnapshot dataSnapshot) {
//            System.out.println("onChildRemoved_snap_:" + dataSnapshot);
//        }
//
//        @Override
//        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//            System.out.println("onChildMoved_snap_:" + dataSnapshot);
//            System.out.println("onChildMoved_string_:" + s);
//        }
//
//        @Override
//        public void onCancelled(DatabaseError databaseError) {
//            System.out.println("onChildCancelled_snap_:" + databaseError);
//        }
//    };
//
//    Query query;
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater,
//                             @Nullable ViewGroup container,
//                             @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_orders_history, container, false);
//    }
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        if (OrderModel.Order.getOrderModel().getFromOrder() != null) {
//            textCurrentOrder.setVisibility(View.VISIBLE);
//            currentOrderContainer.addView(super.initCurrentOrderView());
//        }
//        reference = database.getReference(ORDERS_REF_KEY).child(UserModel.User.getUserModel().getIdUser());
//        initRecycleView();
//        loadMore(0);
//    }
//
//    private void initRecycleView() {
//        historyOrderContainer.setLayoutManager(linearLayoutManager);
//        historyOrderContainer.setAdapter(recycleViewAdapter = new RecycleViewAdapterOrders());
//        historyOrderContainer.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
//        historyOrderContainer.addOnScrollListener(endlessScrollListener);
//    }
//
//    private void loadMore(int offset) {
//
//        System.out.println(lastItemFromBase);
//        tempList.clear();
//        query = reference.startAt(lastItemFromBase).limitToFirst(10);
//        System.out.println(lastItemFromBase);
//        query.addChildEventListener(getArchiveOrders);
//    }
//}
