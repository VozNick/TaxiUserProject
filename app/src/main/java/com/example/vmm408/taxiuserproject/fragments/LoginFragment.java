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

import com.example.vmm408.taxiuserproject.MainActivity;
import com.example.vmm408.taxiuserproject.R;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginFragment extends BaseFragment {
    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @BindView(R.id.et_login)
    EditText etLogin;
    @BindView(R.id.et_password)
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

    @OnClick(R.id.tv_link_forget_password)
    public void tvLinkForgetPassword() {
        System.out.println("forget");
    }

    @OnClick(R.id.tv_link_sign_up)
    public void tvLinkSignUp() {
        System.out.println("good");
    }

    @OnClick(R.id.btn_login)
    public void btnLogin() {
        if (super.validate(etLogin) && super.validate(etPassword)) {
            initProgressDialog();
            new CheckUserInBase().execute();
        }
    }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Authenticating...");
    }

    private class CheckUserInBase extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            etLogin.setEnabled(false);
            progressDialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            return findUserInBase(etLogin, etPassword);
        }

        @Override
        protected void onPostExecute(Boolean userExists) {
            progressDialog.dismiss();
            etLogin.setEnabled(true);
            if (userExists) {
                CategoryModel.Category.setCategoryModelList(initList());
                startActivity(new Intent(getActivity(), MainActivity.class));
            } else {
                etLogin.setError("user doesn't exists");
            }
        }

        private RealmList<CategoryModel> initList() {
            RealmList<CategoryModel> categoryModelRealmList = new RealmList<>();
            categoryModelRealmList.addAll(findCategoryInBase());
            return categoryModelRealmList;
        }
    }
}
