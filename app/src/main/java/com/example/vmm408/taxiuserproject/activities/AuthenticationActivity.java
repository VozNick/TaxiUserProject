package com.example.vmm408.taxiuserproject.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.vmm408.taxiuserproject.R;
import com.example.vmm408.taxiuserproject.fragments.SignInFragment;
import com.example.vmm408.taxiuserproject.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AuthenticationActivity extends AppCompatActivity {
    @BindView(R.id.activity_authentication_container)
    LinearLayout activityAuthContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        ButterKnife.bind(this);
        if (Utils.userSigned(this) != null) {
            startActivity(new Intent(this, MapActivity.class));
        }
        changeFragment(new SignInFragment());
    }

    public void changeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(activityAuthContainer.getId(), fragment).commit();
    }
}
