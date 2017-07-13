package com.example.vmm408.taxiuserproject.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vmm408.taxiuserproject.AuthenticationActivity;
import com.example.vmm408.taxiuserproject.MainActivity;
import com.example.vmm408.taxiuserproject.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.OnClick;

public class SignInFragment extends BaseFragment
        implements GoogleApiClient.OnConnectionFailedListener, ValueEventListener {
    public static SignInFragment newInstance() {
        return new SignInFragment();
    }

    @BindView(R.id.sign_in_button)
    SignInButton signInButton;
    private static final int SIGN_IN_KEY = 9001;
    private GoogleSignInAccount signInAccount;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_google_sign_in, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        super.googleApiClient = initUserSingIn();
    }

    private GoogleApiClient initUserSingIn() {
        return new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity(), this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,
                        new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build())
                .build();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @OnClick(R.id.sign_in_button)
    void signIn() {
        initProgressDialog().show();
        startActivityForResult(Auth.GoogleSignInApi.getSignInIntent(googleApiClient), SIGN_IN_KEY);
    }

    private ProgressDialog initProgressDialog() {
        (progressDialog = new ProgressDialog(getActivity())).setMessage("Authenticating...");
        return progressDialog;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SIGN_IN_KEY)
            handleSignInResult(Auth.GoogleSignInApi.getSignInResultFromIntent(data));
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            signInAccount = result.getSignInAccount();
            (mReference = mDatabase.getReference("users")).addListenerForSingleValueEvent(this);
        }
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            if (signInAccount.getId().equals(snapshot.getKey())) {
                super.saveToShared(snapshot.getKey());
                progressDialog.dismiss();
                startActivity(new Intent(getActivity(), MainActivity.class));
                return;
            }
        }
        progressDialog.dismiss();
        ((AuthenticationActivity) getActivity()).changeFragment(initFragment());
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
    }

    private Fragment initFragment() {
        Fragment fragment = SaveProfileFragment.newInstance();
        fragment.setArguments(initBundle());
        return fragment;
    }

    private Bundle initBundle() {
        Bundle bundle = new Bundle();
        bundle.putString("userId", signInAccount.getId());
        bundle.putString("photo", String.valueOf(signInAccount.getPhotoUrl()));
        bundle.putString("fullName", signInAccount.getGivenName() + " " + signInAccount.getFamilyName());
        return bundle;
    }
}
