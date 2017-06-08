package com.example.vmm408.taxiuserproject.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.vmm408.taxiuserproject.AuthenticationActivity;
import com.example.vmm408.taxiuserproject.MainActivity;
import com.example.vmm408.taxiuserproject.R;
import com.example.vmm408.taxiuserproject.models.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
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
        fillAgeSpinner();
    }

    private void fillAgeSpinner() {
        List<String> age = new ArrayList<>();
        age.add("age");
        for (int i = 0; i < 84; i++) age.add(String.valueOf(16 + i)); //driver from 18
        spinnerAge.setAdapter(new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, age));
    }

    @OnClick(R.id.text_link_login)
    public void tvLinkLogin() {
        ((AuthenticationActivity) getActivity()).changeFragment(LoginFragment.newInstance());
    }

    @OnClick(R.id.btn_create_account)
    public void btnCreateAccount() {
        if (checkValidation()) {
            initProgressDialog();
            createUser();
            startActivity(new Intent(getActivity(), MainActivity.class));
            progressDialog.dismiss();
        }
    }

    private boolean checkValidation() {
        return super.validate(etName) && super.validate(etLastName) &&
                super.validate(spinnerSex.getSelectedItemPosition()) &&
                super.validate(spinnerSex.getSelectedItemPosition()) &&
                super.validate(etPassword) &&
                etPassword.getText().toString().equals(etConfirmPassword.getText().toString());
    }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Registration...");
    }

    private void saveUserToBase() {
        mReference = mDatabase.getReference("users");
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (!UserExistInBase(snapshot)) {
                        createUser();
                        startActivity(new Intent(getActivity(), MainActivity.class));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        })
    }

    private boolean UserExistInBase(DataSnapshot dataSnapshot) {
        return etEmail.getText().toString().equals(gson.fromJson(
                dataSnapshot.getValue().toString(), UserModel.class).getEmailUser()) &&
                etPhone.getText().toString().equals(gson.fromJson(
                        dataSnapshot.getValue().toString(), UserModel.class).getPhoneUser());
    }

    private void createUser() {
        mReference = mDatabase.getReference("users").child(String.valueOf(new Random().nextInt()));
        mReference.child("avatarUser").setValue(String.valueOf(imageUserAvatar));
        mReference.child("nameUser").setValue(String.valueOf(etName.getText().toString()));
        mReference.child("lastNameUser").setValue(String.valueOf(etLastName.getText().toString()));
        mReference.child("sexUser").setValue(String.valueOf(spinnerSex.getSelectedItem()));
        mReference.child("ageUser").setValue(String.valueOf(spinnerAge.getSelectedItem()));
        mReference.child("phoneUser").setValue(String.valueOf(etPhone.getText().toString()));
        mReference.child("emailUser").setValue(String.valueOf(etEmail.getText().toString()));
        mReference.child("passwordUser").setValue(String.valueOf(etPassword.getText().toString()));
        mReference.child("experienceDriver").setValue(String.valueOf(""));
        mReference.child("carModelDriver").setValue(String.valueOf(""));
        mReference.child("numPlateCarDriver").setValue(String.valueOf(""));
    }

    private class CheckUserInBase extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected void onPreExecute() {
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
    }

}
