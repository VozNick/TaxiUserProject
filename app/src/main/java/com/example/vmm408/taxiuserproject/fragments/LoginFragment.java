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

public class LoginFragment extends BaseFragment implements ValueEventListener {
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
        if (super.validate(etLogin) && super.validate(etPassword)) {
//            initProgressDialog();
//            new CheckUserInBase().execute();
            checkUserInBase();
        }
    }
//
//    private void initProgressDialog() {
//        progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setMessage("Authenticating...");
//    }

//    private class CheckUserInBase extends AsyncTask<Void, Void, Void>{
//        @Override
//        protected void onPreExecute() {
//            etLogin.setEnabled(false);
//            progressDialog.show();
//        }
//
//        @Override
//        protected void doInBackground(Void... params) {
//            checkUserInBase();
//        }
//
//        @Override
//        protected void onPostExecute(Boolean userExists) {
//            progressDialog.dismiss();
//            etLogin.setEnabled(true);
//            if (userExists) {
//                startActivity(new Intent(getActivity(), MainActivity.class));
//            } else {
//                etLogin.setError("user doesn't exists");
//            }
//        }

    private void checkUserInBase() {
        mReference = (DatabaseReference) mDatabase.getReference("users").child("login").equalTo(etLogin.getText().toString());
        mReference.addListenerForSingleValueEvent(this);
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        try {
            UserModel.User.setUserModel(gson.fromJson(
                    String.valueOf(dataSnapshot.getValue().toString()), UserModel.class));
            checkPassword();
        } catch (NullPointerException e) {
            makeToast("invalid email / password");
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

    private void checkPassword() {
        if (UserModel.User.getUserModel().getPasswordUser()
                .equals(etPassword.getText().toString())) {
            startActivity(new Intent(getActivity(), MainActivity.class));
        }
        makeToast("invalid email / password");
    }
}
