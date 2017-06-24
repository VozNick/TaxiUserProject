package com.example.vmm408.taxiuserproject.fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.vmm408.taxiuserproject.AuthenticationActivity;
import com.example.vmm408.taxiuserproject.MainActivity;
import com.example.vmm408.taxiuserproject.R;
import com.example.vmm408.taxiuserproject.models.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class SignUpFragment extends BaseFragment implements ValueEventListener {
    public static SignUpFragment newInstance() {
        return new SignUpFragment();
    }

    private static final int READ_STORAGE_PERMISSION_REQUEST_CODE = 111;
    private static final int ACTION_IMAGE_CAPTURE_REQUEST_CODE = 112;
    private static final int ACTION_PICK_PHOTO_REQUEST_CODE = 113;
    private static final String EDIT_TEXT_AVATAR_KEY = "editTextAvatarKey";
    private static final String EDIT_TEXT_NAME_KEY = "editTextNameKey";
    private static final String EDIT_TEXT_LAST_NAME_KEY = "editTextLastNameKey";
    private static final String SPINNER_SEX_KEY = "spinnerSexKey";
    private static final String SPINNER_AGE_KEY = "spinnerAgeKey";
    private static final String EDIT_TEXT_PHONE_KEY = "editTextPhoneKey";
    private static final String EDIT_TEXT_EMAIL_KEY = "editTextEmailKey";
    @BindView(R.id.image_user_avatar)
    CircleImageView imageUserAvatar;
    @BindView(R.id.new_avatar_container)
    LinearLayout newAvatarContainer;
    @BindView(R.id.edit_text_name)
    EditText etName;
    @BindView(R.id.edit_text_last_name)
    EditText etLastName;
    @BindView(R.id.spinner_sex)
    Spinner spinnerSex;
    @BindView(R.id.spinner_age)
    Spinner spinnerAge;
    @BindView(R.id.edit_text_phone)
    EditText etPhone;
    @BindView(R.id.edit_text_email)
    EditText etEmail;
    @BindView(R.id.edit_text_password)
    EditText etPassword;
    @BindView(R.id.edit_text_confirm_password)
    EditText etConfirmPassword;
    private ProgressDialog mProgressDialog;
    private View.OnClickListener viewAvatarMenu = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.text_link_camera) {
                startActivityForResult(getPhotoFromIntent(
                        MediaStore.ACTION_IMAGE_CAPTURE, null),
                        ACTION_IMAGE_CAPTURE_REQUEST_CODE);
            } else if (v.getId() == R.id.text_link_file) {
                startActivityForResult(getPhotoFromIntent(
                        Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI),
                        ACTION_PICK_PHOTO_REQUEST_CODE);
            } else if (v.getId() == R.id.text_link_delete) {
                imageUserAvatar.setImageResource(R.mipmap.ic_launcher);
            }
        }
    };

    private Intent getPhotoFromIntent(String actionPick, Uri internalContentUri) {
        return new Intent(actionPick, internalContentUri);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spinnerAge.setAdapter(spinnerAgeList());
        if (savedInstanceState != null) initFieldsAfterResume(savedInstanceState);
        if (!selfPermissionGranted()) requestPermissions();
    }

    private void initFieldsAfterResume(Bundle savedInstanceState) {
        etName.setText(savedInstanceState.getString(EDIT_TEXT_NAME_KEY));
        etLastName.setText(savedInstanceState.getString(EDIT_TEXT_LAST_NAME_KEY));
        spinnerSex.getItemAtPosition(savedInstanceState.getInt(SPINNER_SEX_KEY));
        spinnerAge.getItemAtPosition(savedInstanceState.getInt(SPINNER_AGE_KEY));
        etPhone.setText(savedInstanceState.getString(EDIT_TEXT_PHONE_KEY));
        etEmail.setText(savedInstanceState.getString(EDIT_TEXT_EMAIL_KEY));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(EDIT_TEXT_NAME_KEY, etName.getText().toString());
        outState.putString(EDIT_TEXT_LAST_NAME_KEY, etLastName.getText().toString());
        outState.putInt(SPINNER_SEX_KEY, spinnerSex.getSelectedItemPosition());
        outState.putInt(SPINNER_AGE_KEY, spinnerAge.getSelectedItemPosition());
        outState.putString(EDIT_TEXT_PHONE_KEY, etPhone.getText().toString());
        outState.putString(EDIT_TEXT_EMAIL_KEY, etEmail.getText().toString());
        super.onSaveInstanceState(outState);
    }

    private ArrayAdapter spinnerAgeList() {
        List<String> age = new ArrayList<>();
        age.add("age");
        for (int i = 0; i < 84; i++) age.add(String.valueOf(16 + i));
        return new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, age);
    }

    private boolean selfPermissionGranted() {
        return ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                READ_STORAGE_PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @OnClick(R.id.image_user_avatar)
    public void avatarUser() {
        if (newAvatarContainer.findViewById(R.id.item_menu_new_avatar) == null)
            newAvatarContainer.addView(initViewForCameraMenu());
    }

    private View initViewForCameraMenu() {
        View viewMenu = getActivity().getLayoutInflater().inflate(R.layout.item_menu_new_avatar, null);
        ButterKnife.findById(viewMenu, R.id.text_link_camera).setOnClickListener(viewAvatarMenu);
        ButterKnife.findById(viewMenu, R.id.text_link_file).setOnClickListener(viewAvatarMenu);
        ButterKnife.findById(viewMenu, R.id.text_link_delete).setOnClickListener(viewAvatarMenu);
        return viewMenu;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageUserAvatar.setImageBitmap(newAvatar(requestCode, data));
        newAvatarContainer.removeAllViews();
    }

    private Bitmap newAvatar(int requestCode, Intent data) {
        if (requestCode == ACTION_IMAGE_CAPTURE_REQUEST_CODE && data != null) {
            return (Bitmap) data.getExtras().get("data");
        } else if (requestCode == ACTION_PICK_PHOTO_REQUEST_CODE) {
            return photoFromFile(data);
        }
        return null;
    }

    private Bitmap photoFromFile(Intent data) {
        try {
            return MediaStore.Images.Media.getBitmap(this.getActivity().getContentResolver(), data.getData());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @OnClick(R.id.text_link_login)
    public void tvLinkLogin() {
        ((AuthenticationActivity) getActivity()).changeFragment(LoginFragment.newInstance());
    }

    @OnClick(R.id.btn_create_account)
    public void btnCreateAccount() {
        if (checkValidationNames() && checkValidationSexAge() &&
                checkValidationPhoneEmail()) { //pass
            initProgressDialog().show();
            (mReference = mDatabase.getReference("users")).addValueEventListener(this);
        }
    }

    private boolean checkValidationNames() {
        return super.validate(etName) && super.validate(etLastName);
    }

    private boolean checkValidationSexAge() {
        return super.validate(spinnerSex.getSelectedItemPosition()) &&
                super.validate(spinnerAge.getSelectedItemPosition());
    }

    private boolean checkValidationPhoneEmail() {
        return super.validate(etPhone) && super.validate(etEmail);
    }

    private boolean checkValidationPassword() {
        return super.validate(etPassword) &&
                etPassword.getText().toString().equals(etConfirmPassword.getText().toString());
    }

    private ProgressDialog initProgressDialog() {
        (mProgressDialog = new ProgressDialog(getActivity())).setMessage("Registration...");
        return mProgressDialog;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            if (etEmail.getText().toString().equals(userGsonFromBase(snapshot).getEmailUser())) {
                initError(etEmail, "email already exists, choose another one");
                return;
            }
            if (etPhone.getText().toString().equals(userGsonFromBase(snapshot).getPhoneUser())) {
                initError(etPhone, "phone already exists, choose another one");
                return;
            }
        }
        createUser();
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        mProgressDialog.dismiss();
    }

    private void initError(TextView textView, String text) {
        textView.setError(text);
        mProgressDialog.dismiss();
    }

    private UserModel userGsonFromBase(DataSnapshot dataSnapshot) {
        return gson.fromJson(dataSnapshot.getValue().toString(), UserModel.class);
    }

    private void createUser() {
        mReference.child(String.valueOf(new Random().nextInt() + new Random().nextInt()))
                .setValue(initUserData());
        mProgressDialog.dismiss();
        startActivity(new Intent(getActivity(), MainActivity.class));
        mReference.onDisconnect();

    }

    private UserModel initUserData() {
//        UserModel.User.getUserModel().setAvatarUser(fromFileToString());
        UserModel.User.getUserModel().setNameUser(etName.getText().toString());
        UserModel.User.getUserModel().setLastNameUser(etLastName.getText().toString());
        UserModel.User.getUserModel().setSexUser(spinnerSex.getSelectedItem().toString());
        UserModel.User.getUserModel().setAgeUser(spinnerAge.getSelectedItem().toString());
        UserModel.User.getUserModel().setPhoneUser(etPhone.getText().toString());
        UserModel.User.getUserModel().setEmailUser(etEmail.getText().toString());
        UserModel.User.getUserModel().setPasswordUser(etPassword.getText().toString());
        return UserModel.User.getUserModel();
    }

//    private String fromFileToString() {
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        new Compressor(getContext()).compressToBitmap()
//                .compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
//        return Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
//    }
}
