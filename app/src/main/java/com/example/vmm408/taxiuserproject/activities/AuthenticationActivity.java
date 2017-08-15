package com.example.vmm408.taxiuserproject.activities;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.vmm408.taxiuserproject.R;
import com.example.vmm408.taxiuserproject.fragments.MapFragment;
import com.example.vmm408.taxiuserproject.fragments.SaveProfileFragment;
import com.example.vmm408.taxiuserproject.fragments.SignInFragment;
import com.example.vmm408.taxiuserproject.utils.UserSharedUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AuthenticationActivity extends AppCompatActivity {
    @BindView(R.id.activity_main_container)
    LinearLayout activityAuthContainer;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (UserSharedUtils.userSignedInApp(this) != null) {
            changeFragment(MapFragment.newInstance());
        }
        changeFragment(SignInFragment.newInstance());
    }

    @Override
    public void onBackPressed() {
        if (fragment instanceof SignInFragment || fragment instanceof MapFragment) {
            super.onBackPressed();
        } else if (fragment instanceof SaveProfileFragment && !new SaveProfileFragment().isEditMode()) {
            changeFragment(SignInFragment.newInstance());
        } else {
            changeFragment(MapFragment.newInstance());
        }
    }

    public void changeFragment(Fragment fragment) {
        this.fragment = fragment;
        getSupportFragmentManager().beginTransaction()
                .replace(activityAuthContainer.getId(), fragment).commit();
    }
}
