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
        String errorMessage = findError(editText);
        if (errorMessage == null) {
            return true;
        }
        editText.setError(errorMessage);
        return false;
    }

    private String findError(EditText editText) {
        String data = editText.getText().toString();
        if (data.isEmpty()) return "can't be empty fields";
        if (editText.getId() == R.id.edit_text_login &&
                !Patterns.EMAIL_ADDRESS.matcher(data).matches() &&
                !Patterns.PHONE.matcher(data).matches()) return "wrong email or phone";
        if (editText.getId() == R.id.edit_text_email &&
                !Patterns.EMAIL_ADDRESS.matcher(data).matches()) return "wrong email format";
        if (editText.getId() == R.id.edit_text_phone &&
                !Patterns.PHONE.matcher(data).matches()) return "wrong phone format";
        if (editText.getId() == R.id.edit_text_password &&
                !Pattern.compile("([A-Za-z0-9]){6,15}").matcher(data).matches() &&
                !Pattern.compile("([A-Z])+").matcher(data).find() &&
                !Pattern.compile("([0-9])+").matcher(data).find())
            return "password must be between 6 and 15 chars. " +
                    "Must contain numbers, chars and at least one capital letter";
        return null;
    }

    protected boolean validate(int position) {
        if (position != 0) {
            return true;
        }
        makeToast("fill in spinners");
        return false;
    }

    protected void makeToast(String string) {
        Toast.makeText(getActivity(), string, Toast.LENGTH_SHORT).show();
    }

//    protected boolean validate(EditText editText) {
//        String data = editText.getText().toString();
//        if (data.isEmpty()) {
//            editText.setError("can't be empty fields");
//            return false;
//        }
//        if (editText.getId() == R.id.edit_text_login &&
//                !Patterns.EMAIL_ADDRESS.matcher(data).matches() &&
//                !Patterns.PHONE.matcher(data).matches()) {
//            editText.setError("wrong email or phone");
//            return false;
//        }
//        if (editText.getId() == R.id.edit_text_email &&
//                !Patterns.EMAIL_ADDRESS.matcher(data).matches()) {
//            editText.setError("wrong email format");
//            return false;
//        }
//        if (editText.getId() == R.id.edit_text_phone &&
//                !Patterns.PHONE.matcher(data).matches()) {
//            editText.setError("wrong phone format");
//            return false;
//        }
//        if (editText.getId() == R.id.edit_text_password &&
//                !Pattern.compile("([A-Za-z0-9]){6,15}").matcher(data).matches() &&
//                !Pattern.compile("([A-Z])+").matcher(data).find() &&
//                !Pattern.compile("([0-9])+").matcher(data).find()) {
//            editText.setError("password must be between 6 and 15 chars. " +
//                    "Must contain numbers, chars and at least one capital letter");
//            return false;
//        }
//        return true;
//    }
}
