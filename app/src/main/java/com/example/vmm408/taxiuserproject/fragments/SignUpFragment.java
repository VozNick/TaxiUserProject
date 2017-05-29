package com.example.vmm408.taxiuserproject.fragments;

import android.app.ProgressDialog;
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

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class SignUpFragment extends BaseFragment {
    public static SignUpFragment newInstance() {
        return new SignUpFragment();
    }

    private static final String TAG = "SignUpFragment";
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
//        if (super.validate(etLogin) && super.validate(etPassword)) {
//            initProgressDialog();
//            new LoginFragment.CheckUserInBase().execute();
//        }
    }
//
//    private void initProgressDialog() {
//        progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setMessage("Authenticating...");
//    }
//
//    private class checkUserInBase extends AsyncTask<Void, Void, Boolean> {
//        @Override
//        protected void onPreExecute() {
//            etLogin.setEnabled(false);
//            progressDialog.show();
//        }
//
//        @Override
//        protected Boolean doInBackground(Void... params) {
//            return findUserInBase(etLogin);
//        }
//
//        @Override
//        protected void onPostExecute(Boolean noUserInBase) {
//            progressDialog.dismiss();
//            etLogin.setEnabled(true);
//            if (noUserInBase) {
//                saveToBase(newUser());
//                startActivity(new Intent(getActivity(), SecondActivity.class));
//            } else {
//                etLogin.setError("user already exists");
//            }
//        }
//
//        private UserModel newUser() {
//            return new UserModel(Realm.getDefaultInstance()
//                    .where(UserModel.class).findAll().size() + 1,
//                    etLogin.getText().toString(),
//                    etPassword.getText().toString(),
//                    etName.getText().toString(),
//                    etLastName.getText().toString());
//        }
//    }

}
