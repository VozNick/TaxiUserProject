package com.example.vmm408.taxiuserproject.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vmm408.taxiuserproject.AuthenticationActivity;
import com.example.vmm408.taxiuserproject.CustomValueEventListener;
import com.example.vmm408.taxiuserproject.MainActivity;
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
    public static SignInFragment newInstance() {
        return new SignInFragment();
    }

    private static final int SIGN_IN_KEY = 9001;
    @BindView(R.id.sign_in_button)
    SignInButton signInButton;
    private GoogleSignInAccount signInAccount;
    private GoogleApiClient.OnConnectionFailedListener failedListener = connectionResult -> {
    };
    private ValueEventListener getUserFromBase = new CustomValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.hasChildren()) {
                new Utils().saveToShared(getContext(), dataSnapshot.getKey());
                progressDialog.dismiss();
                startActivity(new Intent(getActivity(), MainActivity.class));
                return;
            }
            progressDialog.dismiss();
            ((AuthenticationActivity) getActivity()).changeFragment(initFragment());
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
        GoogleSignInOptions signInOptions = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build();
        return new GoogleApiClient.Builder(getContext()).enableAutoManage(getActivity(), failedListener)
                .addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions).build();
    }

    @OnClick(R.id.sign_in_button)
    void signIn() {
        initProgressDialog().show();
        startActivityForResult(Auth.GoogleSignInApi.getSignInIntent(googleApiClient), SIGN_IN_KEY);
    }

    private ProgressDialog initProgressDialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getString(R.string.progress_dialog_msg));
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

    private Fragment initFragment() {
        Fragment fragment = SaveProfileFragment.newInstance();
        fragment.setArguments(initBundle());
        return fragment;
    }

    private Bundle initBundle() {
        Bundle bundle = new Bundle();
        bundle.putString(USER_ID_KEY, signInAccount.getId());
        bundle.putString(PHOTO_KEY, String.valueOf(signInAccount.getPhotoUrl()));
        bundle.putString(FULL_NAME_KEY, signInAccount.getGivenName() + " " + signInAccount.getFamilyName());
        return bundle;
    }
}
