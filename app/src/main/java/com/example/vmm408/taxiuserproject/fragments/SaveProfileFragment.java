package com.example.vmm408.taxiuserproject.fragments;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.vmm408.taxiuserproject.MainActivity;
import com.example.vmm408.taxiuserproject.R;
import com.example.vmm408.taxiuserproject.models.UserModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

public class SaveProfileFragment extends BaseFragment {
    public static SaveProfileFragment newInstance() {
        return new SaveProfileFragment();
    }

    private static final int READ_STORAGE_KEY = 111;
    private static final int IMAGE_CAPTURE_KEY = 112;
    private static final int PICK_PHOTO_KEY = 113;
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
    private String currentPhotoPath;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_save_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fillDataToWidgets();
        if (!selfPermissionGranted()) requestPermissions();
    }

    private void fillDataToWidgets() {
        Bundle bundle = getArguments();
        if (!bundle.isEmpty() && bundle.getBoolean("editMode")) {
            userId = UserModel.User.getUserModel().getIdUser();
            etFullName.setText(UserModel.User.getUserModel().getFullNameUser());
            etPhone.setText(UserModel.User.getUserModel().getPhoneUser());
            tAge.setText(UserModel.User.getUserModel().getAgeUser());
            btnCancelProfile.setVisibility(View.VISIBLE);
            return;
        }
        if (!bundle.isEmpty()) {
            userId = bundle.getString("userId");
            currentPhotoPath = bundle.getString("photo");
            Glide.with(getActivity()).load(bundle.getString("photo")).into(imageUserAvatar);
            etFullName.setText(bundle.getString("fullName"));
        }
    }

    private boolean selfPermissionGranted() {
        return ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                READ_STORAGE_KEY);
    }

    @OnClick(R.id.image_user_avatar)
    public void avatarUser() {
        new AlertDialog.Builder(getActivity()).setItems(R.array.menu_new_avatar, (dialog, which) -> {
            if (which == 0) {
                initActivityResult(IMAGE_CAPTURE_KEY);
            } else if (which == 1) {
                initActivityResult(PICK_PHOTO_KEY);
            } else if (which == 2) {
                initActivityResult(0);
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
        if (key == IMAGE_CAPTURE_KEY) {
            startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null), key);
        } else if (key == PICK_PHOTO_KEY) {
            startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI), key);
        } else {
            imageUserAvatar.setImageResource(R.mipmap.ic_launcher);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_CAPTURE_KEY) {
            imageUserAvatar.setImageBitmap((Bitmap) data.getExtras().get("data"));
        } else if (requestCode == PICK_PHOTO_KEY) {
            try {
                currentPhotoPath = data.getData().getPath();
                imageUserAvatar.setImageBitmap(MediaStore.Images.Media.getBitmap(
                        getActivity().getContentResolver(), data.getData()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String fromFileToString() {
        Bitmap bitmap = new Compressor(getActivity()).compressToBitmap(new File(currentPhotoPath));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return imageString;
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        new Compressor(getActivity()).compressToBitmap(new File(currentPhotoPath))
//                .compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
//        return Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor = getActivity().getContentResolver().openFileDescriptor(uri, "r");
        parcelFileDescriptor.close();
        return BitmapFactory.decodeFileDescriptor(parcelFileDescriptor.getFileDescriptor());
    }

    @OnClick(R.id.btn_save_profile)
    void btnSaveProfile() {
        if (super.validate(etFullName) && super.validate(etPhone)) {
            (mReference = mDatabase.getReference("users")).child(userId).setValue(initUserData());
            super.saveToShared(userId);
            startActivity(new Intent(getActivity(), MainActivity.class));
        }
    }

    private UserModel initUserData() {
        UserModel.User.getUserModel().setIdUser(userId);
//        UserModel.User.getUserModel().setAvatarUser(fromFileToString());
        UserModel.User.getUserModel().setFullNameUser(etFullName.getText().toString());
        UserModel.User.getUserModel().setPhoneUser(etPhone.getText().toString());
        UserModel.User.getUserModel().setGenderUser(spGender.getSelectedItem().toString());
        UserModel.User.getUserModel().setAgeUser(tAge.getText().toString());
        return UserModel.User.getUserModel();
    }

    @OnClick(R.id.btn_cancel_profile)
    void btnCancelProfile() {
        btnCancelProfile.setVisibility(View.GONE);
        startActivity(new Intent(getActivity(), MainActivity.class));
    }
}
