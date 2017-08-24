package com.example.vmm408.taxiuserproject.activities;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.example.vmm408.taxiuserproject.R;
import com.example.vmm408.taxiuserproject.fragments.MapFragment;
import com.example.vmm408.taxiuserproject.fragments.SaveProfileFragment;
import com.example.vmm408.taxiuserproject.fragments.SignInFragment;
import com.example.vmm408.taxiuserproject.utils.UserSharedUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.activity_main_container)
    LinearLayout activityMainContainer;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TAG", "activity create");
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (UserSharedUtils.userSignedInApp(this) != null) {
            changeFragment(MapFragment.newInstance());
        } else {
            changeFragment(SignInFragment.newInstance());
        }
    }

    @Override
    public void onBackPressed() {
        if (fragment instanceof SignInFragment || fragment instanceof MapFragment) {
            super.onBackPressed();
        } else if (fragment instanceof SaveProfileFragment) {
            if (!((SaveProfileFragment) fragment).isEditMode()) {
                super.onBackPressed();
            } else {
                changeFragment(MapFragment.newInstance());
            }
        } else {
            changeFragment(MapFragment.newInstance());
        }
    }

    public void changeFragment(Fragment fragment) {
        this.fragment = fragment;
        getSupportFragmentManager().beginTransaction()
                .replace(activityMainContainer.getId(), fragment).commit();
    }

    @Override
    protected void onStart() {
        Log.d("TAG", "activity start");
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.d("TAG", "activity stop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("TAG", "activity destroy");
    }
}
