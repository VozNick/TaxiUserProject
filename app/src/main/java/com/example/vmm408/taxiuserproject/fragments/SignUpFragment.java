package com.example.vmm408.taxiuserproject.fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.vmm408.taxiuserproject.AuthenticationActivity;
import com.example.vmm408.taxiuserproject.MainActivity;
import com.example.vmm408.taxiuserproject.R;
import com.example.vmm408.taxiuserproject.models.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

public class SignUpFragment extends BaseFragment {
    public static SignUpFragment newInstance() {
        return new SignUpFragment();
    }

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
    private ProgressDialog progressDialog;
    private String mCurrentPhotoPath;

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
        if (checkSelfPermission()) requestPermissions();
        if (savedInstanceState != null) {

            etName.setText(savedInstanceState.getString(EDIT_TEXT_NAME_KEY));
        }
        fillAgeSpinner();
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           @NonNull String[] permissions,
//                                           @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }

    private boolean checkSelfPermission() {
        return ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 111);
    }
    //    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        outState.putString(EDIT_TEXT_NAME_KEY, etName.getText().toString());
//        super.onSaveInstanceState(outState);
//    }

    private void fillAgeSpinner() {
        List<String> age = new ArrayList<>();
        age.add("age");
        for (int i = 0; i < 84; i++) age.add(String.valueOf(16 + i)); //driver from 18
        spinnerAge.setAdapter(new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, age));
    }

    @OnClick(R.id.image_user_avatar)
    public void avatarUser() {
        if (newAvatarContainer.findViewById(R.id.item_menu_new_avatar) == null) {
            newAvatarContainer.addView(getActivity()
                    .getLayoutInflater().inflate(R.layout.item_menu_new_avatar, null));
        }
        ButterKnife.findById(getView(), R.id.text_link_camera)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // get pic from camera
//                        try {
//                            startActivityForResult(new Intent(
//                                    MediaStore.ACTION_IMAGE_CAPTURE).putExtra(
//                                    MediaStore.EXTRA_OUTPUT,
//                                    FileProvider.getUriForFile(
//                                            getActivity(),
//                                            "com.example.vmm408.taxiuserproject",
//                                            createImageFile())), 0);
//                        } catch (IOException ex) {
//                            ex.printStackTrace();
//                        }

                        startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), 0);
                    }
                });
        ButterKnife.findById(getView(), R.id.text_link_file).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get pic from file
                startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI), 1);
            }
        });
        ButterKnife.findById(getView(), R.id.text_link_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // delete selected pic
                imageUserAvatar.setImageResource(R.mipmap.ic_launcher);
            }
        });
    }


    private File createImageFile() throws IOException {
        File image = File.createTempFile("JPEG_" +
                new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date()) +
                "_", ".jpg", getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES));
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            imageUserAvatar.setImageBitmap((Bitmap) data.getExtras().get("data"));
//            imageUserAvatar.setImageURI(Uri.parse(mCurrentPhotoPath));
        } else if (requestCode == 1) {
            try {
                imageUserAvatar.setImageBitmap(MediaStore.Images.Media.getBitmap(this.getActivity().getContentResolver(), data.getData()));
                mCurrentPhotoPath = String.valueOf(data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        newAvatarContainer.removeAllViews();
    }

    private String fromFileToString() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        new Compressor(getContext()).compressToBitmap(new File(mCurrentPhotoPath))
                .compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
//        //from string to file
//        imageBytes = Base64.decode(imageString, Base64.DEFAULT);
//        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
//        setImageBitmap(decodedImage);
    }

    @OnClick(R.id.text_link_login)
    public void tvLinkLogin() {
        ((AuthenticationActivity) getActivity()).changeFragment(LoginFragment.newInstance());
    }

    @OnClick(R.id.btn_create_account)
    public void btnCreateAccount() {
        if (checkValidation()) {
            initProgressDialog();
            findUserInBase();
//            createUser();
//            progressDialog.dismiss();
//            startActivity(new Intent(getActivity(), MainActivity.class));
        } else {
            System.out.println("no");
        }
    }

    private boolean checkValidation() {
        return super.validate(etName) &&
                super.validate(etLastName) &&
                super.validate(spinnerSex.getSelectedItemPosition()) &&
                super.validate(spinnerAge.getSelectedItemPosition()) &&
                super.validate(etPhone) &&
                super.validate(etEmail) &&
                super.validate(etPassword) &&
                etPassword.getText().toString().equals(etConfirmPassword.getText().toString());
    }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Registration...");
        progressDialog.show();
    }

    private void findUserInBase() {
        (mReference = mDatabase.getReference("users"))
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        boolean EmailOrPhoneExistInBase = false;

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            if (userEmailExistInBase(snapshot) || userPhoneExistInBase(snapshot))
                                EmailOrPhoneExistInBase = true;
                        }

                        if (!EmailOrPhoneExistInBase) {
                            createUser();
                            startActivity(new Intent(getActivity(), MainActivity.class));
                        } else {
                            etEmail.setError("user already exists");
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        progressDialog.dismiss();
                    }
                });
    }

    private boolean userEmailExistInBase(DataSnapshot dataSnapshot) {
        return etEmail.getText().toString().equals(gson.fromJson(
                dataSnapshot.getValue().toString(), UserModel.class).getEmailUser());
    }

    private boolean userPhoneExistInBase(DataSnapshot dataSnapshot) {
        return etPhone.getText().toString().equals(gson.fromJson(
                dataSnapshot.getValue().toString(), UserModel.class).getPhoneUser());
    }

    private void createUser() {

        int id = new Random().nextInt() + new Random().nextInt();

        UserModel userModel = new UserModel();

        userModel.setIdUser(id);
        userModel.setAvatarUser(fromFileToString());
        userModel.setNameUser(etName.getText().toString());
        userModel.setLastNameUser(etLastName.getText().toString());
        userModel.setSexUser(spinnerSex.getSelectedItem().toString());
        userModel.setAgeUser(spinnerAge.getSelectedItem().toString());
        userModel.setPhoneUser(etPhone.getText().toString());
        userModel.setEmailUser(etEmail.getText().toString());
        userModel.setPasswordUser(etPassword.getText().toString());
        userModel.setExperienceDriver(0.0);
        userModel.setCarModelDriver("null");
        userModel.setNumPlateCarDriver("null");

        UserModel.User.setUserModel(userModel);

//        mReference.child(String.valueOf(id)).setValue(userModel);

        (mReference = mDatabase.getReference("users")).child(String.valueOf(id)).setValue(userModel);


//        mReference = mDatabase.getReference("users").child(String.valueOf(new Random().nextInt() + new Random().nextInt()));
//        mReference.child("avatarUser").setValue(fromFileToString());
//        mReference.child("nameUser").setValue(etName.getText().toString());
//        mReference.child("lastNameUser").setValue(etLastName.getText().toString());
//        mReference.child("sexUser").setValue(String.valueOf(spinnerSex.getSelectedItem()));
//        mReference.child("ageUser").setValue(String.valueOf(spinnerAge.getSelectedItem()));
//        mReference.child("phoneUser").setValue(etPhone.getText().toString());
//        mReference.child("emailUser").setValue(etEmail.getText().toString());
//        mReference.child("passwordUser").setValue(etPassword.getText().toString());
//        mReference.child("experienceDriver").setValue(String.valueOf(0.0));
//        mReference.child("carModelDriver").setValue(String.valueOf("null"));
//        mReference.child("numPlateCarDriver").setValue(String.valueOf("null"));
    }
}
