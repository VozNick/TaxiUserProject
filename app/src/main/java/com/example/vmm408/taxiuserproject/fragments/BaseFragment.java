package com.example.vmm408.taxiuserproject.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vmm408.taxiuserproject.R;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BaseFragment extends Fragment {
    private Unbinder unbinder;
    protected FirebaseDatabase mDatabase;
    protected DatabaseReference mReference;
        protected Gson gson = new GsonBuilder().create();
    protected ProgressDialog progressDialog;
    protected GoogleApiClient googleApiClient;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        mDatabase = FirebaseDatabase.getInstance();
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    protected boolean validate(EditText editText) {
        String data = editText.getText().toString();
        if (data.isEmpty()) {
            editText.setError("can't be empty fields");
            return false;
        }
        if (editText.getId() == R.id.edit_text_phone && !Patterns.PHONE.matcher(data).matches()) {
            editText.setError("wrong phone format");
            return false;
        }
        return true;
    }

    protected void saveToShared(String userId) {
        (getContext().getSharedPreferences("UserProfile", Context.MODE_APPEND)
                .edit()).putString("userId", userId).apply();
    }

    protected String userSigned() {
        return getContext().getSharedPreferences("UserProfile", Context.MODE_APPEND)
                .getString("userId", null);
    }

    protected void makeToast(String string) {
        Toast.makeText(getActivity(), string, Toast.LENGTH_SHORT).show();
    }
}
