package com.atc.gosmartlesmagistra.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.atc.gosmartlesmagistra.App;
import com.atc.gosmartlesmagistra.R;
import com.atc.gosmartlesmagistra.api.UserApi;
import com.atc.gosmartlesmagistra.model.TeacherProfile;
import com.atc.gosmartlesmagistra.model.User;
import com.atc.gosmartlesmagistra.model.request.ChangePasswordRequest;
import com.atc.gosmartlesmagistra.model.request.UpdateTeacherProfileRequest;
import com.atc.gosmartlesmagistra.model.response.ChangePasswordResponse;
import com.atc.gosmartlesmagistra.model.response.LoginSuccess;
import com.atc.gosmartlesmagistra.model.response.RegisterStudentResponse;
import com.atc.gosmartlesmagistra.model.response.UpdateTeacherProfileResponse;
import com.atc.gosmartlesmagistra.util.DatabaseHelper;
import com.atc.gosmartlesmagistra.util.SessionManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pkmmte.view.CircularImageView;
import com.squareup.picasso.Picasso;
import com.sw926.imagefileselector.ErrorResult;
import com.sw926.imagefileselector.ImageFileSelector;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hendrigunawan on 7/4/17.
 */

public class EditProfileTeacherActivity extends AppCompatActivity {

    @BindView(R.id.action_left) ImageButton actionLeft;
    @BindView(R.id.change_password_button) AppCompatButton changePasswordButton;
    @BindView(R.id.first_name) AutoCompleteTextView mFirstNameView;
    @BindView(R.id.last_name) AutoCompleteTextView mLastNameView;
    @BindView(R.id.mobile_phone) AutoCompleteTextView mPhoneNumberView;
    @BindView(R.id.address) AutoCompleteTextView mAddressView;
    @BindView(R.id.email) AutoCompleteTextView mEmailView;
    @BindView(R.id.izajah_number) AutoCompleteTextView mIzajahNumberView;
    @BindView(R.id.graduated) AutoCompleteTextView mGraduatedView;
    @BindView(R.id.bio) AutoCompleteTextView mBioView;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.title) Spinner titleSpinner;
    @BindView(R.id.image) CircularImageView imageView;
    @BindView(R.id.photoText) EditText photoText;

    public Integer titleSelected;
    SessionManager sessionManager;
    DatabaseHelper databaseHelper;
    User user;
    ImageFileSelector mImageFileSelector;

    private int PICK_IMAGE_REQUEST = 1;
    String pathPhoto = "";

    @OnClick(R.id.image)
    protected void doImageClicked() {
        capturePhoto();
    }

    @OnClick(R.id.submit_button)
    protected void doSumbitClicked() {
        attemptSubmit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_teacher);
        ButterKnife.bind(this);
        setActionLeftIcon();
        sessionManager = new SessionManager(this);
        databaseHelper = new DatabaseHelper(this);

        if (!sessionManager.isLoggedIn()) {
            Toast.makeText(this, "You must login", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        user = databaseHelper.getUser(sessionManager.getUserCode());
        mFirstNameView.setText(user.getFirstName());
        mLastNameView.setText(user.getLastName());
        mPhoneNumberView.setText(user.getPhoneNumber());
        mAddressView.setText(user.getAddress());
        mEmailView.setText(user.getEmail());
        if (!user.getTeacherProfile().getTitle().equals(null)) {
            titleSpinner.setSelection(user.getTeacherProfile().getTitle() - 1);
        }
        mIzajahNumberView.setText(user.getTeacherProfile().getIzajahNumber());
        mGraduatedView.setText(user.getTeacherProfile().getGraduated());
        mBioView.setText(user.getTeacherProfile().getBio());
        Picasso.with(this).load(App.URL + "/files/users/" + user.getPhoto()).error(R.drawable.user).into(imageView);

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChangePasswordActivity.class);
                startActivity(intent);
            }
        });

        titleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                titleSelected = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                titleSelected = 1;
            }
        });

        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1201); // define this constant yourself
        }
        mImageFileSelector = new ImageFileSelector(this);
        mImageFileSelector.setCallback(new ImageFileSelector.Callback() {
            @Override
            public void onError(@NotNull ErrorResult errorResult) {
                switch (errorResult) {
                    case permissionDenied:
                        break;
                    case canceled:
                        break;
                    case error:
                        break;
                }
            }

            @Override
            public void onSuccess(@NotNull String file) {
                Bitmap bm = BitmapFactory.decodeFile(file);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                byte[] b = baos.toByteArray();
                String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

                pathPhoto = encodedImage;
                imageView.setImageBitmap(bm);

                String mensagem = "data:image/jpeg;base64," + pathPhoto.replaceAll("\r*\n*", "");
                photoText.setText(mensagem);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mImageFileSelector.onActivityResult(this, requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mImageFileSelector.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mImageFileSelector.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mImageFileSelector.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    private void capturePhoto() {
        mImageFileSelector.setOutPutImageSize(400, 400);
        mImageFileSelector.setQuality(100);
        mImageFileSelector.selectImage(this, PICK_IMAGE_REQUEST);
    }

    private void setActionLeftIcon() {
        final Drawable iconLeft = ContextCompat.getDrawable(this, R.drawable.zzz_arrow_left);
        iconLeft.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        actionLeft.setImageDrawable(iconLeft);
    }

    private void attemptSubmit() {
        App.hideSoftKeyboard(this);

        String firstName = mFirstNameView.getText().toString();
        String lastName = mLastNameView.getText().toString();
        String phoneNumber = mPhoneNumberView.getText().toString();
        String address = mAddressView.getText().toString();
        String email = mEmailView.getText().toString();
        String izajahNumber = mIzajahNumberView.getText().toString();
        String graduated = mGraduatedView.getText().toString();
        String bio = mBioView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(bio)) {
            mBioView.setError(getString(R.string.error_field_required));
            focusView = mBioView;
            cancel = true;
        }

        if (!isValidEmail(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        }

        if (TextUtils.isEmpty(address)) {
            mAddressView.setError(getString(R.string.error_field_required));
            focusView = mAddressView;
            cancel = true;
        }

        if (TextUtils.isEmpty(phoneNumber)) {
            mPhoneNumberView.setError(getString(R.string.error_field_required));
            focusView = mPhoneNumberView;
            cancel = true;
        }

        if (TextUtils.isEmpty(firstName)) {
            mFirstNameView.setError(getString(R.string.error_field_required));
            focusView = mFirstNameView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            progressBar.setVisibility(View.VISIBLE);

            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request newRequest  = chain.request().newBuilder()
                            .addHeader("Authorization", "Bearer " + sessionManager.getUserToken())
                            .addHeader("Content-Type", "application/json")
                            .build();
                    return chain.proceed(newRequest);
                }
            }).build();
            Retrofit retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(App.API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            UserApi service = retrofit.create(UserApi.class);
            UpdateTeacherProfileRequest request = new UpdateTeacherProfileRequest(firstName, lastName, phoneNumber, "10", "10", address, email, titleSelected, izajahNumber, graduated, bio, photoText.getText().toString(), null);
            Call<LoginSuccess> call = service.updateTeacherProfile(request);
            call.enqueue(new Callback<LoginSuccess>() {
                @Override
                public void onResponse(Call<LoginSuccess> call, Response<LoginSuccess> response) {
                    if (response.raw().isSuccessful()) {

                        databaseHelper.createUser(response.body());

                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), TeacherProfileActivity.class);
                        startActivity(intent);

                    } else if (response.raw().code() == 400) {

                        Gson gson = new GsonBuilder().create();
                        UpdateTeacherProfileResponse mError =new UpdateTeacherProfileResponse();
                        try {
                            mError = gson.fromJson(response.errorBody().string(),UpdateTeacherProfileResponse.class);
                            Toast.makeText(getApplicationContext(), mError.getMessage(), Toast.LENGTH_LONG).show();

                            if (mError.getUpdateTeacherProfileError().getEmail() != null) {
                                mEmailView.setError(mError.getUpdateTeacherProfileError().getEmail());
                                mEmailView.requestFocus();
                            }
                            if (mError.getUpdateTeacherProfileError().getAddress() != null) {
                                mAddressView.setError(mError.getUpdateTeacherProfileError().getAddress());
                                mAddressView.requestFocus();
                            }
                            if (mError.getUpdateTeacherProfileError().getPhoneNumber() != null) {
                                mPhoneNumberView.setError(mError.getUpdateTeacherProfileError().getPhoneNumber());
                                mPhoneNumberView.requestFocus();
                            }
                            if (mError.getUpdateTeacherProfileError().getFirstName() != null) {
                                mFirstNameView.setError(mError.getUpdateTeacherProfileError().getFirstName());
                                mFirstNameView.requestFocus();
                            }

                        } catch (IOException e) {
                            // handle failure to read error
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Update Profile failed", Toast.LENGTH_LONG).show();
                    }

                    progressBar.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onFailure(Call<LoginSuccess> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Update Profile failed, Please try again.", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });

        }
    }

    private boolean isValidEmail(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@") && email.contains(".");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @OnClick(R.id.action_left)
    public void doActionLeft(View view) {
        onBackPressed();
    }
}
