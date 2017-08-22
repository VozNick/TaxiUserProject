package com.example.vmm408.taxiuserproject;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.vmm408.taxiuserproject.activities.MainActivity;
import com.example.vmm408.taxiuserproject.models.OrderModel;
import com.example.vmm408.taxiuserproject.models.UserModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import butterknife.ButterKnife;

import static com.example.vmm408.taxiuserproject.FirebaseDataBaseKeys.CURRENT_ORDER_REF_KEY;
import static com.example.vmm408.taxiuserproject.FirebaseDataBaseKeys.ORDERS_REF_KEY;
import static com.example.vmm408.taxiuserproject.models.UserModel.SignedUser;
import static com.example.vmm408.taxiuserproject.models.OrderModel.CurrentOrder;

public class CustomAlertDialog {
    private EditText etOrderFrom;
    private EditText etOrderDestination;
    private EditText etOrderPrice;
    private EditText etOrderComment;
    private Context context;
    private FirebaseDatabase database;
    private DatabaseReference reference;


    private DialogInterface.OnShowListener showListener = new DialogInterface.OnShowListener() {
        @Override
        public void onShow(DialogInterface dialog) {
            ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
                findWidgetsInView((AlertDialog) dialog);
                if (validate(etOrderFrom) &&
                        validate(etOrderDestination) &&
                        validate(etOrderPrice)) {
                    saveNewOrder((AlertDialog) dialog);
                }
            });
        }
    };

    public CustomAlertDialog(Context context) {
        this.context = context;
    }

    public CustomAlertDialog(Context context, OrderModel currentOrder) {
        this.context = context;
        showCurrentOrderDialog(currentOrder).show();
    }

    public CustomAlertDialog(Context context, FirebaseDatabase database, DatabaseReference reference) {
        this.context = context;
        this.database = database;
        this.reference = reference;
        createNewOrderDialog().show();
    }


    private AlertDialog createNewOrderDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle("New Current Order")
                .setView(((MainActivity) context).getLayoutInflater().inflate(R.layout.item_create_order, null))
                .setCancelable(false)
                .setPositiveButton(context.getResources().getString(R.string.btn_create_order), null)
                .setNeutralButton(context.getResources().getString(R.string.btn_cancel_order), (dialog1, which) -> dialog1.dismiss())
                .create();
        alertDialog.setOnShowListener(showListener);
        return alertDialog;
    }

    private void findWidgetsInView(AlertDialog dialog) {
        etOrderFrom = ButterKnife.findById(dialog, R.id.edit_text_order_from);
        etOrderDestination = ButterKnife.findById(dialog, R.id.edit_text_order_destination);
        etOrderPrice = ButterKnife.findById(dialog, R.id.edit_text_order_price);
        etOrderComment = ButterKnife.findById(dialog, R.id.edit_text_order_comment);
    }

    private void saveNewOrder(AlertDialog dialog) {
        OrderModel model = createOrderModel();
        reference = database.getReference(CURRENT_ORDER_REF_KEY);
        reference.child(SignedUser.getUserModel().getIdUser()).setValue(model);
        dialog.dismiss();
    }

    private OrderModel createOrderModel() {
        OrderModel model = new OrderModel();
        model.setIdUserOrder(UserModel.SignedUser.getUserModel().getIdUser());
        model.setFromOrder(etOrderFrom.getText().toString());
        model.setDestinationOrder(etOrderDestination.getText().toString());
        model.setPriceOrder(etOrderPrice.getText().toString());
        model.setCommentOrder(etOrderComment.getText().toString());
        model.setTimeOrder(String.valueOf(Calendar.getInstance().getTimeInMillis() / 1000));
        model.setOrderAccepted(false);
        CurrentOrder.setOrderModel(model);
        return model;
    }

    public AlertDialog showCurrentOrderDialog(OrderModel model) {
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setView(initOrderView(model))
                .setPositiveButton(context.getResources().getString(R.string.positive_btn_edit), (dialog, which) ->
                        createNewOrderDialog().show())
                .setNegativeButton(context.getResources().getString(R.string.negative_btn_delete), (dialog, which) -> moveToArchive())
                .setNeutralButton(context.getResources().getString(R.string.neutral_btn_back), (dialog, which) -> dialog.dismiss())
                .create();
        return alertDialog;
    }

    public View initOrderView(OrderModel model) {
        View currentOrderView = ((MainActivity) context).getLayoutInflater().inflate(R.layout.item_current_order, null);
        TextView from = ButterKnife.findById(currentOrderView, R.id.text_from_order);
        from.setText(model.getFromOrder());
        TextView destination = ButterKnife.findById(currentOrderView, R.id.text_destination_order);
        destination.setText(model.getDestinationOrder());
        TextView price = ButterKnife.findById(currentOrderView, R.id.text_price_order);
        price.setText(model.getPriceOrder());
        TextView comment = ButterKnife.findById(currentOrderView, R.id.text_comment_order);
        comment.setText(model.getCommentOrder());
        return currentOrderView;
    }

    //temp for moving to history
    protected void moveToArchive() {
        reference = database.getReference(ORDERS_REF_KEY)
                .child(UserModel.SignedUser.getUserModel().getIdUser())
                .child(CurrentOrder.getOrderModel().getTimeOrder());
        reference.setValue(CurrentOrder.getOrderModel());
        reference = database.getReference(CURRENT_ORDER_REF_KEY);
        reference.removeValue();
        CurrentOrder.setOrderModel(null);
    }

    private boolean validate(EditText editText) {
        if (editText.getText().toString().isEmpty()) {
            editText.setError(context.getResources().getString(R.string.text_error_empty_fields));
            return false;
        }
        return true;
    }
}
