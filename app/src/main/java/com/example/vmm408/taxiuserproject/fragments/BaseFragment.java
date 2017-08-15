package com.example.vmm408.taxiuserproject.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vmm408.taxiuserproject.R;
import com.example.vmm408.taxiuserproject.models.OrderModel;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BaseFragment extends Fragment {


    private Unbinder unbinder;
    protected FirebaseDatabase database;
    protected DatabaseReference reference;
    protected Gson gson = new GsonBuilder().create();
    protected ProgressDialog progressDialog;
    protected GoogleApiClient googleApiClient;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        database = FirebaseDatabase.getInstance();
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    protected boolean validate(EditText editText) {
        String data = editText.getText().toString();
        if (data.isEmpty()) {
            editText.setError(getResources().getString(R.string.text_error_empty_fields));
            return false;
        }
        if (editText.getId() == R.id.edit_text_phone && !Patterns.PHONE.matcher(data).matches()) {
            editText.setError(getString(R.string.text_error_phone_format));
            return false;
        }
        return true;
    }

    protected View initCurrentOrderView() {
        OrderModel orderModel = OrderModel.Order.getOrderModel();
        View currentOrderView = getActivity().getLayoutInflater().inflate(R.layout.item_current_order, null);
        TextView from = ButterKnife.findById(currentOrderView, R.id.text_from_order);
        from.setText(orderModel.getFromOrder());
        TextView destination = ButterKnife.findById(currentOrderView, R.id.text_destination_order);
        destination.setText(orderModel.getDestinationOrder());
        TextView price = ButterKnife.findById(currentOrderView, R.id.text_price_order);
        price.setText(orderModel.getPriceOrder());
        TextView comment = ButterKnife.findById(currentOrderView, R.id.text_comment_order);
        comment.setText(orderModel.getCommentOrder());
        return currentOrderView;
    }

    protected void makeToast(String string) {
        Toast.makeText(getContext(), string, Toast.LENGTH_SHORT).show();
    }
}
