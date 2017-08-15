package com.atc.gosmartlesmagistra.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.atc.gosmartlesmagistra.App;
import com.atc.gosmartlesmagistra.R;
import com.atc.gosmartlesmagistra.api.OrderApi;
import com.atc.gosmartlesmagistra.model.Order;
import com.atc.gosmartlesmagistra.model.TeacherCourse;
import com.atc.gosmartlesmagistra.model.response.OrderResponse;
import com.atc.gosmartlesmagistra.model.response.OrderSuccess;
import com.atc.gosmartlesmagistra.model.response.ResponseSuccess;
import com.atc.gosmartlesmagistra.util.DatabaseHelper;
import com.atc.gosmartlesmagistra.util.SessionManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

public class PrivateOrderActivity extends AppCompatActivity {

    @BindView(R.id.action_left) ImageButton actionLeft;
    @BindView(R.id.teacher) TextView teacherView;
    @BindView(R.id.invoice_number) TextView invoiceNumberView;
    @BindView(R.id.course_name) TextView courseNameView;
    @BindView(R.id.course_section) TextView sectionView;
    @BindView(R.id.final_amount) TextView finalAmountView;
    @BindView(R.id.linear) LinearLayout linearView;

    DatabaseHelper databaseHelper;
    SessionManager sessionManager;
    Order order;
    Retrofit retrofit;
    boolean isSubmitSuccess;

    @OnClick(R.id.delete_button)
    protected void deleteButton(View v) {
        new AlertDialog.Builder(this)
                .setTitle("Delete This Order?")
                .setMessage("Do you really want to delete this order?")
                .setIcon(android.R.drawable.ic_delete)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        deleting();
                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }

    @OnClick(R.id.edit_button)
    protected void editButton(View v) {
        Intent intent = new Intent(this, FillOrderActivity.class);
        intent.putExtra("isEdit", true);
        intent.putExtra("teacherCourse", order.getOrderDetails().get(0).getTeacherCourse());
        intent.putExtra("order", order);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_order);
        ButterKnife.bind(this);
        setActionLeftIcon();

        sessionManager = new SessionManager(this);
        databaseHelper = new DatabaseHelper(this);

        linearView.setVisibility(View.INVISIBLE);
        isSubmitSuccess = getIntent().getBooleanExtra("submitSuccess", false);

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
        if (isSubmitSuccess) {
            loadOrder();
        } else {
            getOrders();
        }
    }

    private void deleting() {
        OrderApi service = retrofit.create(OrderApi.class);
        Call<ResponseSuccess> call = service.orderDelete(sessionManager.getUserCode(), order.getId());
        call.enqueue(new Callback<ResponseSuccess>() {
            @Override
            public void onResponse(Call<ResponseSuccess> call, Response<ResponseSuccess> response) {
                if (response.raw().isSuccessful()) {
                    sessionManager.setKeyHaveAnOrder(false);
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Unable to delete, please try again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseSuccess> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Unable to delete, please try again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getOrders() {
        OrderApi service = retrofit.create(OrderApi.class);
        Call<OrderSuccess> call = service.orderShow(sessionManager.getUserCode());
        call.enqueue(new Callback<OrderSuccess>() {
            @Override
            public void onResponse(Call<OrderSuccess> call, Response<OrderSuccess> response) {
                if (response.raw().isSuccessful()) {
                    linearView.setVisibility(View.VISIBLE);
                    databaseHelper.createOrder(sessionManager.getUserCode(), response.body());
                    order = response.body().getOrder();
                    sessionManager.setKeyHaveAnOrder(true);
                    teacherView.setText(order.getUser().getFullName());
                    finalAmountView.setText(order.getFormattedFinalAmount());
                    invoiceNumberView.setText(order.getCode());
                    courseNameView.setText(order.getOrderDetails().get(0).getTeacherCourse().getCourse().getName());
                    sectionView.setText(order.getOrderDetails().get(0).getTeacherCourse().getCourse().getSection() + " " + getString(R.string.section));
                    return;
                } else if (response.raw().code() == 404) {
                    Toast.makeText(getApplicationContext(), getString(R.string.there_is_no_private_order), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getApplicationContext(), "unable to load, please try again", Toast.LENGTH_LONG).show();
                }
                linearView.setVisibility(View.INVISIBLE);
                sessionManager.setKeyHaveAnOrder(false);
            }

            @Override
            public void onFailure(Call<OrderSuccess> call, Throwable t) {
                loadOrder();
            }
        });
    }

    private void loadOrder() {
        if (!sessionManager.getHaveAnOrder()) {
            linearView.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(), getString(R.string.there_is_no_private_order), Toast.LENGTH_SHORT).show();
            return;
        }
        linearView.setVisibility(View.VISIBLE);
        order = databaseHelper.getOrder(sessionManager.getUserCode());
        teacherView.setText(order.getUser().getFullName());
        finalAmountView.setText(order.getFormattedFinalAmount());
        invoiceNumberView.setText(order.getCode());
        courseNameView.setText(order.getOrderDetails().get(0).getTeacherCourse().getCourse().getName());
        sectionView.setText(order.getOrderDetails().get(0).getTeacherCourse().getCourse().getSection() + " " + getString(R.string.section));
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
}
