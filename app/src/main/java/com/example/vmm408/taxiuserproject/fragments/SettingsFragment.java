package com.example.vmm408.taxiuserproject.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vmm408.taxiuserproject.AuthenticationActivity;
import com.example.vmm408.taxiuserproject.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import butterknife.OnClick;

public class SettingsFragment extends BaseFragment {
    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick(R.id.btn_sign_out)
    void btnSignOut() {
        new AlertDialog.Builder(getActivity())
                .setTitle("Confirm signOut?")
                .setPositiveButton("CONFIRM", (dialog, which) -> signOut())
                .setNegativeButton("CANCEL", (dialog, which) -> dialog.dismiss())
                .create().show();
    }

    private void signOut() {
        (getContext().getSharedPreferences("UserProfile", Context.MODE_APPEND)
                .edit()).remove("userId").apply();
        startActivity(new Intent(getActivity(), AuthenticationActivity.class));
    }
}
