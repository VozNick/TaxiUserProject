package com.example.vmm408.taxiuserproject.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vmm408.taxiuserproject.activities.AuthenticationActivity;
import com.example.vmm408.taxiuserproject.CustomValueEventListener;
import com.example.vmm408.taxiuserproject.activities.MainActivity;
import com.example.vmm408.taxiuserproject.R;
import com.example.vmm408.taxiuserproject.utils.Utils;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.OnClick;

import static com.example.vmm408.taxiuserproject.FirebaseDataBaseKeys.USERS_REF_KEY;

public class SignInFragment extends BaseFragment {
    private static final int SIGN_IN_KEY = 9001;
    @BindView(R.id.sign_in_button)
    SignInButton signInButton;
    private GoogleSignInAccount signInAccount;
    private GoogleApiClient.OnConnectionFailedListener failedListener =
            connectionResult -> makeToast("connection failed");
    private ValueEventListener getUserFromBase = new CustomValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.hasChildren()) {
                Utils.saveUserToShared(getContext(), dataSnapshot.getKey());
                progressDialog.dismiss();
                startActivity(new Intent(getContext(), MainActivity.class));
                return;
            }
            progressDialog.dismiss();
            ((AuthenticationActivity) getContext()).changeFragment(SaveProfileFragment.newInstance(
                    signInAccount.getId(),
                    String.valueOf(signInAccount.getPhotoUrl()),
                    signInAccount.getGivenName() + " " + signInAccount.getFamilyName())
            );
        }
    };

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
        return new GoogleApiClient.Builder(getContext())
                .enableAutoManage(getActivity(), failedListener)
                .addApi(Auth.GOOGLE_SIGN_IN_API,
                        new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build())
                .build();
    }

    @OnClick(R.id.sign_in_button)
    void signIn() {
        initProgressDialog().show();
        startActivityForResult(Auth.GoogleSignInApi.getSignInIntent(googleApiClient), SIGN_IN_KEY);
    }

    private ProgressDialog initProgressDialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getResources().getString(R.string.progress_dialog_msg));
        return progressDialog;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SIGN_IN_KEY) {
            handleSignInResult(Auth.GoogleSignInApi.getSignInResultFromIntent(data));
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            signInAccount = result.getSignInAccount();
            reference = database.getReference(USERS_REF_KEY).child(signInAccount.getId());
            reference.addListenerForSingleValueEvent(getUserFromBase);
        }
    }
}
