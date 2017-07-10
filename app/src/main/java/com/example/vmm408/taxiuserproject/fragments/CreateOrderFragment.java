package com.example.vmm408.taxiuserproject.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.vmm408.taxiuserproject.MainActivity;
import com.example.vmm408.taxiuserproject.R;
import com.example.vmm408.taxiuserproject.models.OrderModel;
import com.example.vmm408.taxiuserproject.models.UserModel;

import butterknife.BindView;
import butterknife.OnClick;

public class CreateOrderFragment extends BaseFragment {
    public static CreateOrderFragment newInstance() {
        return new CreateOrderFragment();
    }

    @BindView(R.id.edit_text_order_from)
    EditText etOrderFrom;
    @BindView(R.id.edit_text_order_destination)
    EditText etOrderDestination;
    @BindView(R.id.edit_text_order_price)
    EditText etOrderPrice;
    @BindView(R.id.edit_text_order_comment)
    EditText etOrderComment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_order, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick(R.id.im_btn_my_location)
    void imBtnMyLocation() {

    }

    @OnClick(R.id.btn_create_order)
    void btnCreateOrder() {
        if (super.validate(etOrderFrom) &&
                super.validate(etOrderDestination) &&
                super.validate(etOrderPrice)) {

//            OrderModel.Order.getOrderModel().setIdUserOrder(UserModel.User.getUserModel().getIdUser());
//
//            OrderModel.Order.getOrderModel().setFromOrder(etOrderFrom.getText().toString());
//            OrderModel.Order.getOrderModel().setDestinationOrder(etOrderDestination.getText().toString());
//            OrderModel.Order.getOrderModel().setPriceOrder(Double.parseDouble(etOrderPrice.getText().toString()));
//            OrderModel.Order.getOrderModel().setCommentOrder(etOrderComment.getText().toString());
//
//            OrderModel.Order.getOrderModel().getOrderStatusModel()
//                    .setIdUserOrderStatus(UserModel.User.getUserModel().getIdUser());
//            OrderModel.Order.getOrderModel().getOrderStatusModel().setOrderAcceptedStatus(false);
//
//            (mReference = mDatabase.getReference("orders"))
//                    .child((UserModel.User.getUserModel().getIdUser()))
//                    .setValue(OrderModel.Order.getOrderModel());
//
//            ((MainActivity) getActivity()).changeFragment(MapFragment.newInstance());
        }
    }
}
