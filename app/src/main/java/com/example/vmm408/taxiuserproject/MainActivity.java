package com.example.vmm408.taxiuserproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.example.vmm408.taxiuserproject.fragments.MapFragment;
import com.example.vmm408.taxiuserproject.fragments.SignInFragment;
import com.example.vmm408.taxiuserproject.service.TaxiService;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.activity_main_container)
    LinearLayout activityMainContainer;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        startService(new Intent(this, TaxiService.class));
        changeFragment(MapFragment.newInstance());
    }

    @Override
    public void onBackPressed() {
        if (fragment instanceof MapFragment) {
            moveTaskToBack(true);
        } else {
            changeFragment(MapFragment.newInstance());
        }
    }

    public void changeFragment(Fragment fragment) {
        this.fragment = fragment;
        getSupportFragmentManager().beginTransaction()
                .replace(activityMainContainer.getId(), fragment).commit();
    }

}
