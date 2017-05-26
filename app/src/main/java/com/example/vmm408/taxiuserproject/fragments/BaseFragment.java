package com.example.vmm408.taxiuserproject.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.EditText;

import com.example.vmm408.taxiuserproject.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BaseFragment extends Fragment {
    private Unbinder unbinder;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    protected boolean validate(EditText editText) {
        if (editText.getId() == R.id.et_login) {
            String login = editText.getText().toString();
            if (login.isEmpty() || login.length() < 4) {
                editText.setError("login must be more than 3 chars");
                return false;
            }
        } else if (editText.getId() == R.id.et_password) {
            String password = editText.getText().toString();
            if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
                editText.setError("password must be between 4 and 10 chars");
                return false;
            }
        } else if (editText.getText().toString().isEmpty()) {
            editText.setError("can't be empty fields");
            return false;
        }
        return true;
    }
}
