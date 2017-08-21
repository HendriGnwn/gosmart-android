package com.atc.gosmartlesmagistra.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IntegerRes;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.atc.gosmartlesmagistra.App;
import com.atc.gosmartlesmagistra.R;
import com.atc.gosmartlesmagistra.api.OrderApi;
import com.atc.gosmartlesmagistra.model.Order;
import com.atc.gosmartlesmagistra.model.TeacherCourse;
import com.atc.gosmartlesmagistra.model.request.OrderRequest;
import com.atc.gosmartlesmagistra.model.request.UpdateOrderRequest;
import com.atc.gosmartlesmagistra.model.response.LoginResponse;
import com.atc.gosmartlesmagistra.model.response.OrderResponse;
import com.atc.gosmartlesmagistra.model.response.OrderSuccess;
import com.atc.gosmartlesmagistra.util.DatabaseHelper;
import com.atc.gosmartlesmagistra.util.SessionManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

public class FillOrderActivity extends AppCompatActivity {

    @BindView(R.id.action_left) ImageButton actionLeft;
    @BindView(R.id.title_bar) TextView titleBar;
    @BindView(R.id.course_name) AutoCompleteTextView mCourseNameView;
    @BindView(R.id.course_description) AutoCompleteTextView mCourseDescriptionView;
    @BindView(R.id.course_section) AutoCompleteTextView mCourseSectionView;
    @BindView(R.id.teacher_name) AutoCompleteTextView mTeacherNameView;
    @BindView(R.id.teacher_phone) AutoCompleteTextView mTeacherPhoneNumberView;
    @BindView(R.id.teacher_address) AutoCompleteTextView mTeacherAddressView;
    @BindView(R.id.teacher_bio) AutoCompleteTextView mTeacherBioView;
    @BindView(R.id.final_amount) TextView finalAmount;
    @BindView(R.id.checkout_button) AppCompatButton checkoutButton;
    @BindView(R.id.linear_choose_detail) LinearLayout lEnterText;
    @BindView(R.id.progress_bar) ProgressBar progressBar;

    TeacherCourse teacherCourse;
    SessionManager sessionManager;
    DatabaseHelper databaseHelper;
    Retrofit retrofit;
    Order order;
    boolean isEdit = false;

    int _intLineCount;
    private List<AutoCompleteTextView> autoCompleteTextViewList = new ArrayList<AutoCompleteTextView>();
    private List<LinearLayout> linearLayoutList = new ArrayList<LinearLayout>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_order);
        ButterKnife.bind(this);
        setActionLeftIcon();

        sessionManager = new SessionManager(this);
        databaseHelper = new DatabaseHelper(this);
        teacherCourse = (TeacherCourse) getIntent().getSerializableExtra("teacherCourse");
        mCourseNameView.setText(teacherCourse.getCourse().getName());
        mCourseSectionView.setText(teacherCourse.getCourse().getSection() + ", " + teacherCourse.getCourse().getSectionTime() + " " + getString(R.string.time));
        mCourseDescriptionView.setText(teacherCourse.getCourse().getDescription());
        finalAmount.setText(teacherCourse.getFormattedFinalCost());

        isEdit = getIntent().getBooleanExtra("isEdit", false);
        if (isEdit) {
            order = (Order) getIntent().getSerializableExtra("order");
            mTeacherNameView.setText(order.getUser().getFullName());
            mTeacherPhoneNumberView.setText(order.getUser().getPhoneNumber());
            mTeacherAddressView.setText(order.getUser().getAddress());
            mTeacherBioView.setText(order.getUser().getTeacherProfile().getBio());
            checkoutButton.setText(getString(R.string.action_edit));
        } else {
            mTeacherNameView.setText(teacherCourse.getUser().getFullName());
            mTeacherPhoneNumberView.setText(teacherCourse.getUser().getPhoneNumber());
            mTeacherAddressView.setText(teacherCourse.getUser().getAddress());
            mTeacherBioView.setText(teacherCourse.getUser().getTeacherProfile().getBio());
            checkoutButton.setText(getString(R.string.action_submit));
        }

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
        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(App.API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        dynamicEditText();
    }

    private void dynamicEditText() {
        for (int i=1; i<=teacherCourse.getCourse().getSection(); i++) {
            _intLineCount = i;
            lEnterText.addView(linearlayout(_intLineCount));
            _intLineCount++;
        }

        if (isEdit) {
            Integer i = 0;
            List<String> onDetails = order.getOrderDetails().get(0).getOnDetails();
            for(AutoCompleteTextView autoCompleteTextView : autoCompleteTextViewList) {
                Date date = null;
                try {
                    date = new SimpleDateFormat("yyyy-MM-dd H:m:s", new Locale("id", "ID")).parse(onDetails.get(i));
                    SimpleDateFormat formatted = new SimpleDateFormat("EEEE, dd MMM yyyy H:m:s", new Locale("id", "ID"));
                    autoCompleteTextView.setText(formatted.format(date));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                i++;
            }
        }

        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEdit) {
                    progressBar.setVisibility(View.VISIBLE);
                    Boolean cancel = false;
                    String onAt = null;
                    for(final AutoCompleteTextView autoCompleteTextView : autoCompleteTextViewList) {
                        Log.i("hendrigunawan", autoCompleteTextView.getText().toString());
                        String choose = autoCompleteTextView.getText().toString();
                        Date date = null;
                        try {
                            date = new SimpleDateFormat("EEEE, dd MMM yyyy H:m:s", new Locale("id", "ID")).parse(choose);
                            SimpleDateFormat formatted = new SimpleDateFormat("yyyy-MM-dd H:m:s", new Locale("id", "ID"));
                            choose = formatted.format(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        autoCompleteTextView.setError(null);
                        if (TextUtils.isEmpty(choose)) {
                            cancel = true;
                            autoCompleteTextView.setError(getResources().getString(R.string.error_field_required));
                        } else {
                            if (TextUtils.isEmpty(onAt)) {
                                onAt = choose;
                            } else {
                                onAt += "," + choose;
                            }
                        }
                    }

                    if (cancel) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_field_required), Toast.LENGTH_SHORT).show();
                    } else {
                        OrderApi service = retrofit.create(OrderApi.class);
                        UpdateOrderRequest request = new UpdateOrderRequest(teacherCourse.getId(), onAt);
                        Call<OrderSuccess> call = service.orderUpdate(sessionManager.getUserCode(), order.getId(), request);
                        call.enqueue(new Callback<OrderSuccess>() {
                            @Override
                            public void onResponse(Call<OrderSuccess> call, Response<OrderSuccess> response) {
                                if (response.raw().isSuccessful()) {
                                    sessionManager.setKeyHaveAnOrder(true);
                                    databaseHelper.createOrder(sessionManager.getUserCode(), response.body());
                                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    intent.putExtra("submitSuccess", true);
                                    startActivity(intent);
                                } else if (response.raw().code() == 400) {
                                    Gson gson = new GsonBuilder().create();
                                    OrderResponse mError =new OrderResponse();
                                    try {
                                        mError = gson.fromJson(response.errorBody().string(),OrderResponse.class);
                                        Toast.makeText(getApplicationContext(), mError.getMessage(), Toast.LENGTH_LONG).show();
                                    } catch (IOException e) {
                                        // handle failure to read error
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "Order failed, please try again", Toast.LENGTH_LONG).show();
                                }
                                progressBar.setVisibility(View.INVISIBLE);
                            }

                            @Override
                            public void onFailure(Call<OrderSuccess> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "Order failed, please try again", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        });
                    }
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    Boolean cancel = false;
                    String onAt = null;
                    for(final AutoCompleteTextView autoCompleteTextView : autoCompleteTextViewList) {
                        Log.i("hendrigunawan", autoCompleteTextView.getText().toString());
                        String choose = autoCompleteTextView.getText().toString();
                        Date date = null;
                        try {
                            date = new SimpleDateFormat("EEEE, dd MMM yyyy H:m:s", new Locale("id", "ID")).parse(choose);
                            SimpleDateFormat formatted = new SimpleDateFormat("yyyy-MM-dd H:m:s", new Locale("id", "ID"));
                            choose = formatted.format(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        autoCompleteTextView.setError(null);
                        if (TextUtils.isEmpty(choose)) {
                            cancel = true;
                            autoCompleteTextView.setError(getResources().getString(R.string.error_field_required));
                        } else {
                            if (TextUtils.isEmpty(onAt)) {
                                onAt = choose;
                            } else {
                                onAt += "," + choose;
                            }
                        }
                    }

                    if (cancel) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_field_required), Toast.LENGTH_SHORT).show();
                    } else {
                        OrderApi service = retrofit.create(OrderApi.class);
                        OrderRequest request = new OrderRequest(teacherCourse.getUser().getUniqueNumber(), teacherCourse.getId(), onAt);
                        Call<OrderSuccess> call = service.orderCreate(sessionManager.getUserCode(), request);
                        call.enqueue(new Callback<OrderSuccess>() {
                            @Override
                            public void onResponse(Call<OrderSuccess> call, Response<OrderSuccess> response) {
                                if (response.raw().isSuccessful()) {
                                    sessionManager.setKeyHaveAnOrder(true);
                                    databaseHelper.createOrder(sessionManager.getUserCode(), response.body());
                                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    intent.putExtra("submitSuccess", true);
                                    startActivity(intent);
                                } else if (response.raw().code() == 400) {
                                    Gson gson = new GsonBuilder().create();
                                    OrderResponse mError =new OrderResponse();
                                    try {
                                        mError = gson.fromJson(response.errorBody().string(),OrderResponse.class);
                                        Toast.makeText(getApplicationContext(), mError.getMessage(), Toast.LENGTH_LONG).show();
                                    } catch (IOException e) {
                                        // handle failure to read error
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "Order failed, please try again", Toast.LENGTH_LONG).show();
                                }
                                progressBar.setVisibility(View.INVISIBLE);
                            }

                            @Override
                            public void onFailure(Call<OrderSuccess> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "Order failed, please try again", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        });
                    }
                }
            }
        });

        for(final AutoCompleteTextView autoCompleteTextView : autoCompleteTextViewList) {
            autoCompleteTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            final Calendar calendar = Calendar.getInstance();
                            int mYear = calendar.get(Calendar.YEAR);
                            int mMonth = calendar.get(Calendar.MONTH);
                            int mDay = calendar.get(Calendar.DAY_OF_MONTH);
                            final int hour = calendar.get(Calendar.HOUR_OF_DAY);
                            final int minute = calendar.get(Calendar.MINUTE);
                            // date picker dialog
                            final DatePickerDialog datePickerDialog = new DatePickerDialog(getApplicationContext(),
                                    new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker view, int year,
                                                              int monthOfYear, int dayOfMonth) {
                                            autoCompleteTextView.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                        }
                                    }, mYear, mMonth, mDay);
                            datePickerDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                            datePickerDialog.show();
                            datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.i("cranium", "test");
                                    if (which == DialogInterface.BUTTON_POSITIVE) {
                                        final DatePicker datePicker = datePickerDialog
                                                .getDatePicker();
                                        TimePickerDialog timePickerDialog = new TimePickerDialog(getApplicationContext(), new TimePickerDialog.OnTimeSetListener() {
                                            @Override
                                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                                String datetime = datePicker.getYear() + "-" + (datePicker.getMonth() + 1) + "-" + datePicker.getDayOfMonth();
                                                Date date = null;
                                                try {
                                                    date = new SimpleDateFormat("yyyy-MM-dd", new Locale("id", "ID")).parse(datetime);
                                                    SimpleDateFormat formatted = new SimpleDateFormat("EEEE, dd MMM yyyy", new Locale("id", "ID"));
                                                    autoCompleteTextView.setText(formatted.format(date) + " " + hourOfDay + ":00:00");
                                                } catch (ParseException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }, hour, minute, DateFormat.is24HourFormat(getApplicationContext()));
                                        timePickerDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                                        timePickerDialog.show();
                                    }
                                }
                            });
                        }
                    }, 200);
                }
            });
        }
    }

    private LinearLayout linearlayout(int _intID) {
        TextInputLayout textInputLayout = new TextInputLayout(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0,0, dpToPx(4), dpToPx(16));
        textInputLayout.setLayoutParams(layoutParams);

        final AutoCompleteTextView autoCompleteTextView = new AutoCompleteTextView(this);
        autoCompleteTextView.setId(_intID);
        autoCompleteTextView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        autoCompleteTextView.setFocusable(false);
        autoCompleteTextView.setTextSize(14);
        autoCompleteTextView.setHint(getResources().getString(R.string.prompt_choose_section) + " " + _intID);
        autoCompleteTextViewList.add(autoCompleteTextView);

        textInputLayout.addView(autoCompleteTextView);

        LinearLayout LLMain=new LinearLayout(this);
        LLMain.setId(_intID);
        LLMain.addView(textInputLayout);
        LLMain.setOrientation(LinearLayout.VERTICAL);
        linearLayoutList.add(LLMain);
        return LLMain;
    }

    private void setActionLeftIcon() {
        final Drawable iconLeft = ContextCompat.getDrawable(this, R.drawable.zzz_arrow_left);
        iconLeft.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        actionLeft.setImageDrawable(iconLeft);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @OnClick(R.id.action_left)
    public void doActionLeft(View view) {
        onBackPressed();
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
