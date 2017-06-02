package com.example.vmm408.taxiuserproject.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.vmm408.taxiuserproject.AuthenticationActivity;
import com.example.vmm408.taxiuserproject.R;
import com.example.vmm408.taxiuserproject.models.UserModel;

import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class SignUpFragment extends BaseFragment {
    public static SignUpFragment newInstance() {
        return new SignUpFragment();
    }

    @BindView(R.id.image_user_avatar)
    CircleImageView imageUserAvatar;
    @BindView(R.id.edit_text_name)
    EditText etName;
    @BindView(R.id.edit_text_last_name)
    EditText etLastName;
    @BindView(R.id.spinner_sex)
    Spinner spinnerSex;
    @BindView(R.id.spinner_age)
    Spinner spinnerAge;
    @BindView(R.id.edit_text_phone)
    EditText etPhone;
    @BindView(R.id.edit_text_email)
    EditText etEmail;
    @BindView(R.id.edit_text_password)
    EditText etPassword;
    @BindView(R.id.edit_text_confirm_password)
    EditText etConfirmPassword;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick(R.id.text_link_login)
    public void tvLinkLogin() {
        ((AuthenticationActivity) getActivity()).changeFragment(LoginFragment.newInstance());
    }

    @OnClick(R.id.btn_create_account)
    public void btnCreateAccount() {
        if (super.validate(etName) &&
                super.validate(etLastName) &&
                super.validate(spinnerSex.getSelectedItemPosition()) &&
                super.validate(spinnerSex.getSelectedItemPosition()) &&
                super.validate(etPassword) &&
                etPassword.getText().toString().equals(etConfirmPassword.getText().toString())) {
            initProgressDialog();
            new CheckUserInBase().execute();
        }
    }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Registration...");
    }

    private class CheckUserInBase extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected void onPreExecute() {
//            etLogin.setEnabled(false);
            progressDialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            if (checkInBase(etEmail, etPhone)) {
                etEmail.setError("User already exists");
                return false;
            } else {
                // reg user
                return true;
            }
        }

        @Override
        protected void onPostExecute(Boolean noUserInBase) {
            progressDialog.dismiss();
            etLogin.setEnabled(true);
            if (noUserInBase) {
                saveToBase(newUser());
                startActivity(new Intent(getActivity(), SecondActivity.class));
            } else {
                etLogin.setError("user already exists");
            }
        }

        private UserModel newUser() {
            return new UserModel(new Random().nextInt(),
                    R.mipmap.ic_launcher,
                    etName.getText().toString(),
                    etLastName.getText().toString(),
                    sex(),
                    Integer.parseInt(spinnerAge.getSelectedItem().toString()),
                    Integer.parseInt(etPhone.getText().toString()),
                    etEmail.getText().toString(),
                    etPassword.getText().toString(),
                    "",
                    0,
                    0.0,
                    "",
                    "");
        }

        private boolean sex() {
            return spinnerSex.getSelectedItemPosition() == 1;
        }
    }

}
