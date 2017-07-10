package com.example.vmm408.taxiuserproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.example.vmm408.taxiuserproject.fragments.MapFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    //    @BindView(R.id.toolbar)
//    Toolbar toolbar;
    @BindView(R.id.activity_main_container)
    LinearLayout activityMainContainer;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        setSupportActionBar(toolbar);
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
