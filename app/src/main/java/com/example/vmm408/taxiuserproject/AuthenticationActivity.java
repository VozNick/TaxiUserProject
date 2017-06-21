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
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_authentication);
        ButterKnife.bind(this);
//        if (savedInstanceState != null) {
//            List<Fragment> fragments = (List<Fragment>) savedInstanceState.getSerializable("bob");
//            for (int i = 0; i < fragments.size(); i++) {
//                System.out.println(fragments.get(i));
//            }
//            changeFragment(fragments.get(fragments.size() - 1));
//            return;
//        }
        changeFragment(LoginFragment.newInstance());
    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        List<Fragment> fragments = getSupportFragmentManager().getFragments();
//        for (int i = 0; i < fragments.size(); i++) {
//            System.out.println(fragments.get(i));
//        }
//        outState.putSerializable("bob", (Serializable) getSupportFragmentManager().getFragments());
//        super.onSaveInstanceState(outState);
//    }

    @Override
    public void onBackPressed() {
        if (fragment instanceof SignUpFragment) {
            changeFragment(LoginFragment.newInstance());
        } else {
            super.onBackPressed();
        }
    }

    public void changeFragment(Fragment fragment) {
        this.fragment = fragment;
        getSupportFragmentManager().beginTransaction()
                .replace(activityAuthContainer.getId(), fragment).commit();
    }
}
