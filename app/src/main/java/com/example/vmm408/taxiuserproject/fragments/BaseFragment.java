package com.example.vmm408.taxiuserproject.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vmm408.taxiuserproject.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.regex.Pattern;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BaseFragment extends Fragment {
    private Unbinder unbinder;
    protected FirebaseDatabase mDatabase;
    protected DatabaseReference mReference;
    protected Gson gson = new GsonBuilder().create();

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
        if (editText.getId() == R.id.edit_text_login) {
            String login = editText.getText().toString();
            if (login.isEmpty() ||
                    (!Patterns.EMAIL_ADDRESS.matcher(login).matches() &&
                            !Patterns.PHONE.matcher(login).matches())) {
                editText.setError("wrong email");
                return false;
            }
        } else if (editText.getId() == R.id.edit_text_password) {
            String password = editText.getText().toString();
            if (!isValidPassword(password)) {
                editText.setError("password must be between 4 and 10 chars");
                return false;
            }
        } else if (editText.getText().toString().isEmpty()) {
            editText.setError("can't be empty fields");
            return false;
        }
        return true;
    }

    private boolean isValidPassword(String password) {
        if (Pattern.compile("([A-Za-z0-9]){6,15}").matcher(password).matches()) {
            if (Pattern.compile("([A-Z])+").matcher(password).find()
                    && Pattern.compile("([0-9])+").matcher(password).find()) {
                return true;
            }
        }
        return false;
    }

    protected boolean validate(int position) {
        if (position == 0) {
            makeToast("fill in spinners");
            return false;
        }
        return true;
    }

    protected void makeToast(String string) {
        Toast.makeText(getActivity(), string, Toast.LENGTH_SHORT).show();
    }
}
