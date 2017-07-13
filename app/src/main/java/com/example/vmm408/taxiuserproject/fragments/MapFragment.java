package com.example.vmm408.taxiuserproject.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.vmm408.taxiuserproject.MainActivity;
import com.example.vmm408.taxiuserproject.R;
import com.example.vmm408.taxiuserproject.models.OrderModel;
import com.example.vmm408.taxiuserproject.models.UserModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.LOCATION_SERVICE;

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
    private LocationManager mLocationManager;
    private Location mLocation;
    private GoogleMap mMap;
    private SearchView.SearchAutoComplete searchAutoComplete;
    private ValueEventListener findUserInBase = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            try {
                UserModel.User.getUserModel()
                        .setAvatarUser(dataSnapshot.child("avatarUser").getValue().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            UserModel.User.getUserModel()
                    .setIdUser(dataSnapshot.child("idUser").getValue().toString());
            UserModel.User.getUserModel()
                    .setFullNameUser(dataSnapshot.child("fullNameUser").getValue().toString());
            UserModel.User.getUserModel()
                    .setPhoneUser(dataSnapshot.child("phoneUser").getValue().toString());
            UserModel.User.getUserModel()
                    .setGenderUser(dataSnapshot.child("genderUser").getValue().toString());
            UserModel.User.getUserModel()
                    .setAgeUser(dataSnapshot.child("ageUser").getValue().toString());
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
        }
    };
    private SearchView.OnQueryTextListener mTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            searchViewAppBar.clearFocus();
            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            imBtnClearSearch.setVisibility(newText.length() > 0 ? View.VISIBLE : View.GONE);
            if (newText.length() > 2) {
            }
//            searchAutoComplete.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, temp));
//            searchAutoComplete.showDropDown();
            return false;
        }
    };
    private LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

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
        if (UserModel.User.getUserModel().getIdUser() == null)
            (mReference = mDatabase.getReference("users")).child(super.userSigned())
                    .addListenerForSingleValueEvent(findUserInBase);
        initSearchView();
        initAutoComplete();
        initAvatar();
        findLocation();
        initMapFragment();
    }

    private void initSearchView() {
        searchViewAppBar.setIconified(false);
        searchViewAppBar.setQueryHint("Search here...");
        searchViewAppBar.setOnQueryTextListener(mTextListener);
    }

    private void initAutoComplete() {
//        searchAutoComplete = (SearchView.SearchAutoComplete) searchViewAppBar.findViewById(R.id.search_src_text);
//        searchAutoComplete.setOnItemClickListener((parent, view, position, id) -> searchViewAppBar
//                .setQuery(userModels.get(position).getName(), true));
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
        Glide.with(getActivity()).load(uri).into(imBtnProfile);
    }

    private void findLocation() {
        if (!checkSelfPermission()) requestPermissions();
        try {
            mLocationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);
            mLocation = mLocationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initMapFragment() {
        ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getMapAsync(googleMap -> {
            mMap = googleMap;
            mMap.setOnMapClickListener((latLng -> mMap.clear()));
            mMap.setOnMapLongClickListener(this::initMarkerFromLocation);
            mMap.setOnMarkerClickListener(marker -> false);
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (!checkSelfPermission()) return;
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
    }

    private boolean checkSelfPermission() {
        return ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION}, 123);
    }

    @OnClick(R.id.im_btn_clear_search)
    void imBtnClearSearch() {
        imBtnClearSearch.setVisibility(View.GONE);
        searchViewAppBar.setQuery("", false);
    }

    @OnClick(R.id.im_btn_my_location)
    void imageBtnMyLocation() {
        findLocation();
        try {
            initMarkerFromLocation(new LatLng(mLocation.getLatitude(), mLocation.getLongitude()));
        } catch (Exception e) {
            Toast.makeText(getContext(), "turn on location", Toast.LENGTH_SHORT).show();
        }
    }

    private void initMarkerFromLocation(LatLng latLng) {
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(latLng, 12f, 0f, 0f)));
        mMap.clear();
        try {
            List<Address> addresses = new Geocoder(getContext(), Locale.getDefault())
                    .getFromLocation(latLng.latitude, latLng.longitude, 1);
            for (int i = 0; i < addresses.size(); i++) {
                mMap.addMarker(new MarkerOptions().position(latLng).title(addresses.get(i).getAddressLine(0)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @OnClick(R.id.im_btn_profile)
    void imBtnProfile() {
        new AlertDialog.Builder(getActivity()).setItems(R.array.menu_profile, (dialog, which) -> {
            if (which == 0) {
                ((MainActivity) getActivity()).changeFragment(initFragment());
            } else if (which == 1) {
                ((MainActivity) getActivity()).changeFragment(RatingFragment.newInstance());
            } else if (which == 2) {
                makeToast("Orders history");
            } else if (which == 3) {
                ((MainActivity) getActivity()).changeFragment(SettingsFragment.newInstance());
            }
        }).create().show();
    }

    private Fragment initFragment() {
        Fragment fragment = SaveProfileFragment.newInstance();
        fragment.setArguments(initBundle());
        return fragment;
    }

    private Bundle initBundle() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("editMode", true);
        return bundle;
    }

    @OnClick(R.id.im_btn_new_order)
    void imBtnNewOrder() {
        if (UserModel.User.getUserModel().getIdCurrentOrder() == null) {
            ((MainActivity) getActivity()).changeFragment(CreateOrderFragment.newInstance());
        } else {
            new AlertDialog.Builder(getActivity())
                    .setView(initCurrentOrderView())
                    .setPositiveButton("EDIT", (dialog, which) -> makeToast("edit"))
                    .setNegativeButton("DELETE", (dialog, which) -> makeToast("delete"))
                    .setNeutralButton("BACK", (dialog, which) -> makeToast("back"))
                    .create().show();
        }
    }

    private View initCurrentOrderView() {
        View view = getActivity().getLayoutInflater().inflate(R.layout.item_current_order, null);
        ((TextView) ButterKnife.findById(view, R.id.text_from_order))
                .setText(OrderModel.Order.getOrderModel().getFromOrder());
        ((TextView) ButterKnife.findById(view, R.id.text_destination_order))
                .setText(OrderModel.Order.getOrderModel().getDestinationOrder());
        ((TextView) ButterKnife.findById(view, R.id.text_price_order))
                .setText(OrderModel.Order.getOrderModel().getPriceOrder());
        ((TextView) ButterKnife.findById(view, R.id.text_comment_order))
                .setText(OrderModel.Order.getOrderModel().getCommentOrder());
        return view;
    }
}
