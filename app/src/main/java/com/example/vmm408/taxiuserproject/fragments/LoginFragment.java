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
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginFragment extends BaseFragment implements ValueEventListener {
    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    private static final String EDIT_TEXT_LOGIN_KEY = "editTextLoginKey";
    @BindView(R.id.edit_text_login)
    EditText etLogin;
    @BindView(R.id.edit_text_password)
    EditText etPassword;
    private ProgressDialog mProgressDialog;

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
        if (savedInstanceState != null)
            etLogin.setText(savedInstanceState.getString(EDIT_TEXT_LOGIN_KEY));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(EDIT_TEXT_LOGIN_KEY, etLogin.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @OnClick(R.id.text_link_forget_password)
    public void tvLinkForgetPassword() {
    }

    @OnClick(R.id.text_link_sign_up)
    public void tvLinkSignUp() {
        ((AuthenticationActivity) getActivity()).changeFragment(SignUpFragment.newInstance());
    }

    @OnClick(R.id.btn_login)
    public void btnLogin() {
        if (super.validate(etLogin) && super.validate(etPassword)) {
        initProgressDialog().show();
        (mReference = mDatabase.getReference("users")).addValueEventListener(this);
        }
    }

    private ProgressDialog initProgressDialog() {
        (mProgressDialog = new ProgressDialog(getActivity())).setMessage("Authenticating...");
        return mProgressDialog;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        for (DataSnapshot snapshot : dataSnapshot.getChildren())
            if (findUserInBase(snapshot)) {
                loginUser(snapshot);
                mReference.onDisconnect();
                return;
            }
        mProgressDialog.dismiss();
        makeToast("invalid email / password");
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        mProgressDialog.dismiss();
    }

    private void loginUser(DataSnapshot dataSnapshot) {
        UserModel.User.setUserModel(gson.fromJson(dataSnapshot.getValue().toString(), UserModel.class));
        UserModel.User.getUserModel().setIdUser(Integer.parseInt(dataSnapshot.getKey()));
        mProgressDialog.dismiss();
        startActivity(new Intent(getActivity(), MainActivity.class));
    }

    private boolean findUserInBase(DataSnapshot dataSnapshot) {
        return (etLogin.getText().toString().equals(userGsonFromBase(dataSnapshot).getEmailUser()) ||
                etLogin.getText().toString().equals(userGsonFromBase(dataSnapshot).getPhoneUser())) &&
                etPassword.getText().toString().equals(userGsonFromBase(dataSnapshot).getPasswordUser());
    }

    private UserModel userGsonFromBase(DataSnapshot dataSnapshot) {
        return gson.fromJson(dataSnapshot.getValue().toString(), UserModel.class);
    }
}
