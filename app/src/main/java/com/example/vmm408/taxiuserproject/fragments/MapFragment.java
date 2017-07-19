package com.example.vmm408.taxiuserproject.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.vmm408.taxiuserproject.CustomValueEventListener;
import com.example.vmm408.taxiuserproject.MainActivity;
import com.example.vmm408.taxiuserproject.R;
import com.example.vmm408.taxiuserproject.models.OrderModel;
import com.example.vmm408.taxiuserproject.models.UserModel;
import com.example.vmm408.taxiuserproject.utils.Utils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.vmm408.taxiuserproject.FirebaseDataBaseKeys.ORDERS_REF_KEY;
import static com.example.vmm408.taxiuserproject.FirebaseDataBaseKeys.USERS_REF_KEY;

public class MapFragment extends BaseFragment {
    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @BindView(R.id.search_view_app_bar)
    SearchView searchViewAppBar;
    @BindView(R.id.im_btn_clear_search)
    ImageView imBtnClearSearch;
    @BindView(R.id.im_btn_profile)
    CircleImageView imBtnProfile;
    @BindView(R.id.search_view_container)
    LinearLayout searchViewContainer;
    @BindView(R.id.fab_new_order)
    FloatingActionButton fabNewOrder;
    private FusedLocationProviderClient client;
    private Location location;
    private GoogleMap map;
    private SearchView.SearchAutoComplete searchAutoComplete;
    private List<String> searchAddressList = new ArrayList<>();
    private ValueEventListener findUserInBase = new CustomValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            UserModel.User.setUserModel(dataSnapshot.getValue(UserModel.class));
        }
    };
    private ValueEventListener findCurrentOrderInBase = new CustomValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            OrderModel.Order.setOrderModel(dataSnapshot.getValue(OrderModel.class));
        }
    };
    private SearchView.OnQueryTextListener onQueryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            searchViewAppBar.clearFocus();
            initMarkerFromName(query);
            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            imBtnClearSearch.setVisibility(newText.length() > 0 ? View.VISIBLE : View.GONE);
            if (newText.length() > 2) {
                // rx method
//                    map.clear();
//                    try {
//                        List<Address> addresses = new Geocoder(getContext(), Locale.getDefault())
//                                .getFromLocationName(newText, 10);
//                        for (int i = 0; i < addresses.size(); i++) {
//                            searchAddressList.add(addresses.get(i).getAddressLine(0));
//                            System.out.println(addresses.get(i));
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
            }
//                searchAutoComplete.setAdapter(new ArrayAdapter<>(getActivity(),
//                        android.R.layout.simple_list_item_1, searchAddressList));
//                searchAutoComplete.showDropDown();
            return true;
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDataFromBase();
        initAvatar();
        initMapFragment();
        client = LocationServices.getFusedLocationProviderClient(getContext());
        if (!checkSelfPermission()) {
            requestPermissions();
        } else {
            getLastLocation();
        }
    }

    private void getDataFromBase() {
        if (UserModel.User.getUserModel().getIdUser() == null) {
            reference = database.getReference(USERS_REF_KEY).child(new Utils().userSigned(getContext()));
            reference.addListenerForSingleValueEvent(findUserInBase);
        }
        if (UserModel.User.getUserModel().getIdCurrentOrder() != null) {
            reference = database.getReference(ORDERS_REF_KEY).child(UserModel.User.getUserModel().getIdCurrentOrder());
            reference.addListenerForSingleValueEvent(findCurrentOrderInBase);
        }
    }

    private void initAvatar() {
        String avatar = UserModel.User.getUserModel().getAvatarUser();
        if (avatar == null) {
            imBtnProfile.setImageResource(R.mipmap.ic_launcher);
        } else if (avatar.startsWith("https://lh3.googleusercontent.com/")) {
            downloadPhotoUri(avatar);
        } else {
            byte[] imageBytes = Base64.decode(UserModel.User.getUserModel().getAvatarUser(), Base64.DEFAULT);
            imBtnProfile.setImageBitmap(BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length));
        }
    }

    private void downloadPhotoUri(String uri) {
        Glide.with(getContext()).load(uri).into(imBtnProfile);
    }

    private void initMapFragment() {
        SupportMapFragment mapFrag = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(googleMap -> {
            this.map = googleMap;
            this.map.setOnMapClickListener((latLng -> {
                this.map.clear();
                searchViewAppBar.clearFocus();
            }));
            this.map.setOnMapLongClickListener(this::initMarkerFromLocation);
            this.map.setOnMarkerClickListener(marker -> false);
        });
    }

    @SuppressWarnings("MissingPermission")
    private void getLastLocation() {
        client.getLastLocation().addOnCompleteListener(getActivity(), task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                location = task.getResult();
            }
        });
    }

    private boolean checkSelfPermission() {
        return ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions((Activity) getContext(),
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION}, 123);
    }

    @OnClick(R.id.toolbar)
    void toolbar() {
        searchViewContainer.setVisibility(View.VISIBLE);
        initSearchView();
    }

    private void initSearchView() {
        searchViewAppBar.setIconified(false);
        searchViewAppBar.setQueryHint(getString(R.string.search_view_hint));
        searchViewAppBar.setOnQueryTextListener(onQueryTextListener);
    }

    private void initAutoComplete() {
        searchAutoComplete = (SearchView.SearchAutoComplete) searchViewAppBar.findViewById(R.id.search_src_text);
        searchAutoComplete.setOnItemClickListener((parent, view, position, id) -> {
            searchViewAppBar.setQuery(searchAddressList.get(position), true);
            initMarkerFromName(searchAddressList.get(position));
        });
    }

    public void initMarkerFromName(String name) {
        map.clear();
        try {
            List<Address> addresses = new Geocoder(getContext(), Locale.getDefault())
                    .getFromLocationName(name, 50);
            for (int i = 0; i < addresses.size(); i++) {
                map.addMarker(new MarkerOptions().position(new LatLng(addresses.get(i).getLatitude(),
                        addresses.get(i).getLongitude())).title(addresses.get(i).getAddressLine(0)));
                map.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(
                        new LatLng(addresses.get(i).getLatitude(), addresses.get(i).getLongitude()), 12f, 0f, 0f)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.image_view_btn_back)
    void imBtnBack() {
        searchViewContainer.setVisibility(View.GONE);
    }

    @OnClick(R.id.im_btn_clear_search)
    void imBtnClearSearch() {
        imBtnClearSearch.setVisibility(View.GONE);
        searchViewAppBar.setQuery("", false);
    }

    @OnClick(R.id.im_btn_profile)
    void imBtnProfile() {
        new AlertDialog.Builder(getContext()).setItems(R.array.menu_profile, (dialog, which) -> {
            if (which == 0) {
                ((MainActivity) getContext()).changeFragment(initFragment());
            } else if (which == 1) {
                ((MainActivity) getContext()).changeFragment(RatingFragment.newInstance());
            } else if (which == 2) {
                ((MainActivity) getContext()).changeFragment(OrdersHistoryFragment.newInstance());
            } else if (which == 3) {
                ((MainActivity) getContext()).changeFragment(SettingsFragment.newInstance());
            }
        }).create().show();
    }

    @OnClick(R.id.fab_new_order)
    void fabNewOrder() {
        if (UserModel.User.getUserModel().getIdCurrentOrder() == null) {
            ((MainActivity) getActivity()).changeFragment(CreateOrderFragment.newInstance());
        } else {
            new AlertDialog.Builder(getContext())
                    .setView(super.initCurrentOrderView())
                    .setPositiveButton(getString(R.string.positive_btn_edit), (dialog, which) -> makeToast("edit"))
                    .setNegativeButton(getString(R.string.negative_btn_delete), (dialog, which) -> makeToast("delete"))
                    .setNeutralButton(getString(R.string.neutral_btn_back), (dialog, which) -> dialog.dismiss())
                    .create().show();
        }
    }

    @OnClick(R.id.fab_my_location)
    void fabMyLocation() {
        getLastLocation();
        try {
            initMarkerFromLocation(new LatLng(location.getLatitude(), location.getLongitude()));
        } catch (Exception e) {
            Toast.makeText(getContext(), getString(R.string.toast_turn_location), Toast.LENGTH_SHORT).show();
        }
    }

    private void initMarkerFromLocation(LatLng latLng) {
        map.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(latLng, 12f, 0f, 0f)));
        map.clear();
        try {
            List<Address> addresses = new Geocoder(getContext(), Locale.getDefault())
                    .getFromLocation(latLng.latitude, latLng.longitude, 1);
            for (int i = 0; i < addresses.size(); i++) {
                map.addMarker(new MarkerOptions().position(latLng).title(addresses.get(i).getAddressLine(0)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Fragment initFragment() {
        Fragment fragment = SaveProfileFragment.newInstance();
        fragment.setArguments(initBundle());
        return fragment;
    }

    private Bundle initBundle() {
        Bundle bundle = new Bundle();
        bundle.putBoolean(EDIT_MODE_KEY, true);
        return bundle;
    }
}
