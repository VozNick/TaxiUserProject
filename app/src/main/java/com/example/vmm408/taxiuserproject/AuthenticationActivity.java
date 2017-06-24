package com.example.vmm408.taxiuserproject;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.vmm408.taxiuserproject.fragments.LoginFragment;
import com.example.vmm408.taxiuserproject.fragments.SignUpFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AuthenticationActivity extends AppCompatActivity {
    @BindView(R.id.activity_authentication_container)
    LinearLayout activityAuthContainer;
    private Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        ButterKnife.bind(this);
        changeFragment(LoginFragment.newInstance());
    }

    @Override
    public void onBackPressed() {
        if (mFragment instanceof SignUpFragment) {
            changeFragment(LoginFragment.newInstance());
        } else {
            super.onBackPressed();
        }
    }

    public void changeFragment(Fragment fragment) {
        this.mFragment = fragment;
        getSupportFragmentManager().beginTransaction()
                .replace(activityAuthContainer.getId(), fragment).commit();
    }
}
