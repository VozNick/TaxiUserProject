//
//package com.google.android.gms.location.sample.locationupdates;
//
//import android.Manifest;
//import android.app.Activity;
//import android.content.Intent;
//import android.content.IntentSender;
//import android.content.pm.PackageManager;
//import android.location.Location;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Looper;
//import android.provider.Settings;
//import android.support.annotation.NonNull;
//import android.support.design.widget.Snackbar;
//import android.support.v4.app.ActivityCompat;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.View;
//import android.widget.Toast;
//
//import com.example.vmm408.taxiuserproject.BuildConfig;
//import com.example.vmm408.taxiuserproject.activities.MapActivity;
//import com.example.vmm408.taxiuserproject.R;
//import com.google.android.gms.common.api.ApiException;
//import com.google.android.gms.common.api.ResolvableApiException;
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationCallback;
//import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationResult;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.location.LocationSettingsRequest;
//import com.google.android.gms.location.LocationSettingsStatusCodes;
//import com.google.android.gms.location.SettingsClient;
//
//import java.text.DateFormat;
//import java.util.Date;
//
//public class Main extends AppCompatActivity {
//    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
//    private static final int REQUEST_CHECK_SETTINGS = 0x1;
//    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
//    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2;
//    private final static String KEY_REQUESTING_LOCATION_UPDATES = "requesting-location-updates";
//    private final static String KEY_LOCATION = "location";
//    private final static String KEY_LAST_UPDATED_TIME_STRING = "last-updated-time-string";
//    private FusedLocationProviderClient fusedLocationClient;
//    private SettingsClient settingsClient;
//    private LocationRequest locationRequest;
//    private LocationSettingsRequest locationSettingsRequest;
//    private LocationCallback locationCallback;
//    private Location currentLocation;
//    private Boolean requestingLocationUpdates;
//    private String lastUpdateTime;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_map);
//        requestingLocationUpdates = false;
//        lastUpdateTime = "";
//        updateValuesFromBundle(savedInstanceState);
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
//        settingsClient = LocationServices.getSettingsClient(this);
//        createLocationCallback();
//        createLocationRequest();
//        buildLocationSettingsRequest();
//    }
//
//    private void updateValuesFromBundle(Bundle savedInstanceState) {
//        if (savedInstanceState != null) {
//            if (savedInstanceState.keySet().contains(KEY_REQUESTING_LOCATION_UPDATES)) {
//                requestingLocationUpdates = savedInstanceState.getBoolean(KEY_REQUESTING_LOCATION_UPDATES);
//            }
//            if (savedInstanceState.keySet().contains(KEY_LOCATION)) {
//                currentLocation = savedInstanceState.getParcelable(KEY_LOCATION);
//            }
//            if (savedInstanceState.keySet().contains(KEY_LAST_UPDATED_TIME_STRING)) {
//                lastUpdateTime = savedInstanceState.getString(KEY_LAST_UPDATED_TIME_STRING);
//            }
//            updateUI();
//        }
//    }
//
//    private void createLocationRequest() {
//        locationRequest = new LocationRequest();
//        locationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
//        locationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
//        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//    }
//
//    private void createLocationCallback() {
//        locationCallback = new LocationCallback() {
//            @Override
//            public void onLocationResult(LocationResult locationResult) {
//                super.onLocationResult(locationResult);
//                currentLocation = locationResult.getLastLocation();
//                lastUpdateTime = DateFormat.getTimeInstance().format(new Date());
//                updateLocationUI();
//            }
//        };
//    }
//
//    private void buildLocationSettingsRequest() {
//        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
//        builder.addLocationRequest(locationRequest);
//        locationSettingsRequest = builder.build();
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_CHECK_SETTINGS) {
//            if (resultCode == Activity.RESULT_OK) {
//
//            }
//        }
//    }
//
//    public void startUpdatesButtonHandler(View view) {
//        if (!requestingLocationUpdates) {
//            requestingLocationUpdates = true;
//            setButtonsEnabledState();
//            startLocationUpdates();
//        }
//    }
//
//    public void stopUpdatesButtonHandler(View view) {
//        // It is a good practice to remove location requests when the activity is in a paused or
//        // stopped state. Doing so helps battery performance and is especially
//        // recommended in applications that request frequent location updates.
//        stopLocationUpdates();
//    }
//
//    @SuppressWarnings("MissingPermission")
//    private void startLocationUpdates() {
//        settingsClient.checkLocationSettings(locationSettingsRequest)
//                .addOnSuccessListener(this, locationSettingsResponse -> {
//                    System.out.println("All location settings are satisfied.");
//                    fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
//                    updateUI();
//                })
//                .addOnFailureListener(this, e -> {
//                    int statusCode = ((ApiException) e).getStatusCode();
//                    if (statusCode == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
//                        System.out.println("Location settings are not satisfied. Attempting to upgrade location settings ");
//                        try {
//                            ResolvableApiException rae = (ResolvableApiException) e;
//                            rae.startResolutionForResult(Main.this, REQUEST_CHECK_SETTINGS);
//                        } catch (IntentSender.SendIntentException sie) {
//                            System.out.println("PendingIntent unable to execute request.");
//                        }
//                    } else if (statusCode == LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE) {
//                        String errorMessage = "Location settings are inadequate, and cannot be fixed here. Fix in Settings.";
//                        System.out.println(errorMessage);
//                        Toast.makeText(Main.this, errorMessage, Toast.LENGTH_LONG).show();
//                        requestingLocationUpdates = false;
//                    }
//                    updateUI();
//                });
//    }
//
//    private void stopLocationUpdates() {
//        if (!requestingLocationUpdates) {
//            System.out.println("stopLocationUpdates: updates never requested, no-op.");
//            return;
//        }
//        fusedLocationClient.removeLocationUpdates(locationCallback)
//                .addOnCompleteListener(this, task -> requestingLocationUpdates = false);
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        if (requestingLocationUpdates && checkPermissions()) {
//            startLocationUpdates();
//        } else if (!checkPermissions()) {
//            requestPermissions();
//        }
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        stopLocationUpdates();
//    }
//
//    public void onSaveInstanceState(Bundle savedInstanceState) {
//        savedInstanceState.putBoolean(KEY_REQUESTING_LOCATION_UPDATES, requestingLocationUpdates);
//        savedInstanceState.putParcelable(KEY_LOCATION, currentLocation);
//        savedInstanceState.putString(KEY_LAST_UPDATED_TIME_STRING, lastUpdateTime);
//        super.onSaveInstanceState(savedInstanceState);
//    }
//
//    private void showSnackbar(final int mainTextStringId, final int actionStringId, View.OnClickListener listener) {
//        Snackbar.make(
//                findViewById(android.R.id.content),
//                getString(mainTextStringId),
//                Snackbar.LENGTH_INDEFINITE)
//                .setAction(getString(actionStringId), listener).show();
//    }
//
//    private boolean checkPermissions() {
//        int permissionState = ActivityCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_FINE_LOCATION);
//        return permissionState == PackageManager.PERMISSION_GRANTED;
//    }
//
//    private void requestPermissions() {
//        boolean shouldProvideRationale =
//                ActivityCompat.shouldShowRequestPermissionRationale(this,
//                        Manifest.permission.ACCESS_FINE_LOCATION);
//
//        // Provide an additional rationale to the user. This would happen if the user denied the
//        // request previously, but didn't check the "Don't ask again" checkbox.
//        if (shouldProvideRationale) {
//            Log.i(TAG, "Displaying permission rationale to provide additional context.");
//            showSnackbar(R.string.permission_rationale,
//                    android.R.string.ok, new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            // Request permission
//                            ActivityCompat.requestPermissions(MapActivity.this,
//                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                                    REQUEST_PERMISSIONS_REQUEST_CODE);
//                        }
//                    });
//        } else {
//            Log.i(TAG, "Requesting permission");
//            // Request permission. It's possible this can be auto answered if device policy
//            // sets the permission in a given state or the user denied the permission
//            // previously and checked "Never ask again".
//            ActivityCompat.requestPermissions(MapActivity.this,
//                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                    REQUEST_PERMISSIONS_REQUEST_CODE);
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        System.out.println("onRequestPermissionResult");
//        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
//            if (grantResults.length <= 0) {
//                System.out.println("User interaction was cancelled.");
//            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                if (requestingLocationUpdates) {
//                    System.out.println("Permission granted, updates requested, starting location updates");
//                    startLocationUpdates();
//                }
//            } else {
//                showSnackbar(R.string.permission_denied_explanation,
//                        R.string.settings, (View.OnClickListener) view -> {
//                            // Build intent that displays the App settings screen.
//                            Intent intent = new Intent();
//                            intent.setAction(
//                                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                            Uri uri = Uri.fromParts("package",
//                                    BuildConfig.APPLICATION_ID, null);
//                            intent.setData(uri);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            startActivity(intent);
//                        });
//            }
//        }
//    }
//}