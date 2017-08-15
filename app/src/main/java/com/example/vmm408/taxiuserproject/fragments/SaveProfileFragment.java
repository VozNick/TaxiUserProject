package com.example.vmm408.taxiuserproject.fragments;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.vmm408.taxiuserproject.MyKeys;
import com.example.vmm408.taxiuserproject.R;
import com.example.vmm408.taxiuserproject.activities.AuthenticationActivity;
import com.example.vmm408.taxiuserproject.models.UserModel;
import com.example.vmm408.taxiuserproject.utils.UserSharedUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

import static com.example.vmm408.taxiuserproject.FirebaseDataBaseKeys.USERS_REF_KEY;

public class SaveProfileFragment extends BaseFragment {
    public static SaveProfileFragment newInstance(String userId,
                                                  String photoUrl,
                                                  String fullName) {
        Bundle bundle = new Bundle();
        bundle.putString(MyKeys.USER_ID_KEY, userId);
        bundle.putString(MyKeys.PHOTO_KEY, photoUrl);
        bundle.putString(MyKeys.FULL_NAME_KEY, fullName);
        SaveProfileFragment fragment = new SaveProfileFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public static SaveProfileFragment newInstance(boolean flag) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(MyKeys.EDIT_MODE_KEY, flag);
        SaveProfileFragment fragment = new SaveProfileFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @BindView(R.id.image_user_avatar)
    CircleImageView imageUserAvatar;
    @BindView(R.id.edit_text_full_name)
    EditText etFullName;
    @BindView(R.id.edit_text_phone)
    EditText etPhone;
    @BindView(R.id.spinner_gender)
    Spinner spGender;
    @BindView(R.id.text_age)
    TextView tAge;
    @BindView(R.id.btn_cancel_profile)
    Button btnCancelProfile;
    private String userId;
    private Bitmap bitmapAvatar;
    private String googlePhotoPath;

    private boolean editMode;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_save_profile, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fillDataToWidgets();
        if (!selfPermissionGranted()) {
            requestPermissions();
        }
    }

    public boolean isEditMode() {
        return editMode;
    }

    private void fillDataToWidgets() {
        Bundle bundle = getArguments();
        if (bundle.getBoolean(MyKeys.EDIT_MODE_KEY)) {
            editMode = bundle.getBoolean(MyKeys.EDIT_MODE_KEY);
            fillWidgetsFromEditMode();
            return;
        }
        userId = bundle.getString(MyKeys.USER_ID_KEY);
        googlePhotoPath = bundle.getString(MyKeys.PHOTO_KEY);
        downloadPhotoUri(bundle.getString(MyKeys.PHOTO_KEY));
        etFullName.setText(bundle.getString(MyKeys.FULL_NAME_KEY));
    }

    private void fillWidgetsFromEditMode() {
        initAvatar();
        userId = UserModel.User.getUserModel().getIdUser();
        etFullName.setText(UserModel.User.getUserModel().getFullNameUser());
        etPhone.setText(UserModel.User.getUserModel().getPhoneUser());
        tAge.setText(UserModel.User.getUserModel().getAgeUser());
        btnCancelProfile.setVisibility(View.VISIBLE);
    }

    private void initAvatar() {
        String avatar = UserModel.User.getUserModel().getAvatarUser();
        if (avatar == null) {
            imageUserAvatar.setImageResource(R.mipmap.ic_launcher);
        } else if (avatar.startsWith("https://lh3.googleusercontent.com/")) {
            googlePhotoPath = avatar;
            downloadPhotoUri(avatar);
        } else {
            byte[] imageBytes = Base64.decode(UserModel.User.getUserModel().getAvatarUser(), Base64.DEFAULT);
            bitmapAvatar = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            imageUserAvatar.setImageBitmap(bitmapAvatar);
        }
    }

    private void downloadPhotoUri(String uri) {
        Glide.with(getActivity()).load(uri).into(imageUserAvatar);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private boolean selfPermissionGranted() {
        return ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void requestPermissions() {
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                MyKeys.READ_STORAGE_KEY);
    }

    @OnClick(R.id.image_user_avatar)
    public void avatarUser() {
        new AlertDialog.Builder(getActivity()).setItems(R.array.menu_new_avatar, (dialog, which) -> {
            if (which == MyKeys.IMAGE_CAPTURE_KEY) {
                initActivityResult(MyKeys.IMAGE_CAPTURE_KEY);
            } else if (which == MyKeys.PICK_PHOTO_KEY) {
                initActivityResult(MyKeys.PICK_PHOTO_KEY);
            } else if (which == MyKeys.DELETE_PHOTO_KEY) {
                initActivityResult(MyKeys.DELETE_PHOTO_KEY);
            }
        }).create().show();
    }

    @OnClick(R.id.text_age)
    void initAgeWidget() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity(),
                android.R.style.Theme_DeviceDefault_Dialog_MinWidth,
                ((view1, year, month, dayOfMonth) -> tAge
                        .setText(dayOfMonth + "." + (month + 1) + "." + year)),
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getWindow();
        datePickerDialog.show();
    }

    private void initActivityResult(int key) {
        if (key == MyKeys.IMAGE_CAPTURE_KEY) {
            startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null), key);
        } else if (key == MyKeys.PICK_PHOTO_KEY) {
            startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI), key);
        } else {
            imageUserAvatar.setImageResource(R.mipmap.ic_launcher);
            bitmapAvatar = null;
            googlePhotoPath = null;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (requestCode == MyKeys.IMAGE_CAPTURE_KEY) {
            bitmapAvatar = (Bitmap) data.getExtras().get("data");
        } else if (requestCode == MyKeys.PICK_PHOTO_KEY) {
            bitmapAvatar = new Compressor(getActivity()).compressToBitmap(new File(getPathFromURI(data.getData())));
        } else if (requestCode == MyKeys.DELETE_PHOTO_KEY) {
            imageUserAvatar.setImageBitmap(bitmapAvatar);
            googlePhotoPath = null;
        }
    }

    public String getPathFromURI(Uri uri) {
        Cursor cursor = null;
        try {
            cursor = getActivity().getContentResolver().query(uri, new String[]{"_data"}, null, null, null);
            if (cursor != null && cursor.moveToFirst())
                return cursor.getString(cursor.getColumnIndexOrThrow("_data"));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        if (cursor != null)
            cursor.close();
        return null;
    }

    @OnClick(R.id.btn_save_profile)
    void btnSaveProfile() {
        if (super.validate(etFullName) && super.validate(etPhone)) {
            reference = database.getReference(USERS_REF_KEY).child(userId);
            reference.setValue(initUserData());
            UserSharedUtils.saveUserToShared(getContext(), userId);
            ((AuthenticationActivity) getContext()).changeFragment(MapFragment.newInstance());
        }
    }

    private UserModel initUserData() {
        UserModel.User.getUserModel().setIdUser(userId);
        UserModel.User.getUserModel().setAvatarUser(googlePhotoPath != null ? googlePhotoPath : fromFileToString());
        UserModel.User.getUserModel().setFullNameUser(etFullName.getText().toString());
        UserModel.User.getUserModel().setPhoneUser(etPhone.getText().toString());
        UserModel.User.getUserModel().setGenderUser(spGender.getSelectedItem().toString());
        UserModel.User.getUserModel().setAgeUser(tAge.getText().toString());
        return UserModel.User.getUserModel();
    }

    private String fromFileToString() {
        if (bitmapAvatar != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmapAvatar.compress(Bitmap.CompressFormat.JPEG, 80, baos);
            return Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        }
        return null;
    }

    @OnClick(R.id.btn_cancel_profile)
    void btnCancelProfile() {
        btnCancelProfile.setVisibility(View.GONE);
        ((AuthenticationActivity) getContext()).changeFragment(MapFragment.newInstance());
    }
}
