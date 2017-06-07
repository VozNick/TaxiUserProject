package com.example.vmm408.taxiuserproject.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.vmm408.taxiuserproject.AuthenticationActivity;
import com.example.vmm408.taxiuserproject.MainActivity;
import com.example.vmm408.taxiuserproject.R;
import com.example.vmm408.taxiuserproject.models.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginFragment extends BaseFragment {
    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @BindView(R.id.edit_text_login)
    EditText etLogin;
    @BindView(R.id.edit_text_password)
    EditText etPassword;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick(R.id.text_link_forget_password)
    public void tvLinkForgetPassword() {
        // write code here
    }

    @OnClick(R.id.text_link_sign_up)
    public void tvLinkSignUp() {
        ((AuthenticationActivity) getActivity()).changeFragment(SignUpFragment.newInstance());
    }

    @OnClick(R.id.btn_login)
    public void btnLogin() {
        if (super.validate(etLogin)
//                && super.validate(etPassword)
                ) {
            initProgressDialog();
            checkUserInBase();
        }
    }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Authenticating...");
        etLogin.setEnabled(false);
        progressDialog.show();
    }

    private void checkUserInBase() {
        mReference = mDatabase.getReference("users");
        mReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildren().iterator().equals(etLogin.getText().toString())) {
                    System.out.println("good");
                }
                try {
                    UserModel.User.setUserModel(dataSnapshot.getValue(UserModel.class));
                    checkPassword();
                } catch (NullPointerException e) {
                    progressDialog.dismiss();
                    etLogin.setEnabled(true);
                    makeToast("invalid email / password");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void checkPassword() {
        if (UserModel.User.getUserModel().getPasswordUser()
                .equals(etPassword.getText().toString())) {
            progressDialog.dismiss();
            etLogin.setEnabled(true);
            startActivity(new Intent(getActivity(), MainActivity.class));
        }
        makeToast("invalid email / password");
    }
}
