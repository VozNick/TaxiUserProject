package com.example.vmm408.taxiuserproject.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vmm408.taxiuserproject.R;
import com.example.vmm408.taxiuserproject.models.OrderModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecycleViewAdapterOrders extends
        RecyclerView.Adapter<RecycleViewAdapterOrders.CustomViewHolder> {
    private List<OrderModel> orderModelList = new ArrayList<>();

    public void addList(List<OrderModel> orderModelList) {
        this.orderModelList.addAll(orderModelList);
        notifyDataSetChanged();
    }

//    public void addItem(OrderModel orderModel) {
//        this.orderModelList.add(orderModel);
//        notifyDataSetChanged();
//    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_order, parent, false));
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.textFromOrder.setText(orderModelList.get(position).getFromOrder());
        holder.textDestinationOrder.setText(orderModelList.get(position).getDestinationOrder());
        holder.itemTextTime.setText(orderModelList.get(position).getTimeOrder());
        holder.itemBtnMore.setOnClickListener(v -> System.out.println("open more fragment"));
    }

    @Override
    public int getItemCount() {
        return orderModelList.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_from_order)
        TextView textFromOrder;
        @BindView(R.id.text_destination_order)
        TextView textDestinationOrder;
        @BindView(R.id.item_text_time)
        TextView itemTextTime;
        @BindView(R.id.item_btn_more)
        TextView itemBtnMore;

        CustomViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
