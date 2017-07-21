package com.atc.gosmartlesmagistra.activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.atc.gosmartlesmagistra.App;
import com.atc.gosmartlesmagistra.R;
import com.atc.gosmartlesmagistra.api.RequestApi;
import com.atc.gosmartlesmagistra.model.response.TeacherTermConditionSuccess;
import com.atc.gosmartlesmagistra.util.DatabaseHelper;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hendrigunawan on 6/12/17.
 */

public class TeacherTermConditionActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.agree_img) ImageView agreeImg;
    @BindView(R.id.agree_btn) LinearLayout agreeBtn;
    @BindView(R.id.term_condition) TextView termCondition;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindColor(R.color.colorBlackSecondary) int dark;

    private String tempTerm = "";

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_term_condition);
        ButterKnife.bind(this);

        databaseHelper = new DatabaseHelper(this);

        Drawable agreeDrawable = ContextCompat.getDrawable(this, R.drawable.zzz_arrow_right);
        agreeDrawable.setColorFilter(dark, PorterDuff.Mode.SRC_ATOP);
        agreeImg.setImageDrawable(agreeDrawable);

        loadTermAndConditions();

        agreeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpTeacherActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadTermAndConditions() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(App.API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestApi requestApi = retrofit.create(RequestApi.class);

        Call<TeacherTermConditionSuccess> call = requestApi.teacherTermCondition();
        call.enqueue(new Callback<TeacherTermConditionSuccess>() {
            @Override
            public void onResponse(Call<TeacherTermConditionSuccess> call, Response<TeacherTermConditionSuccess> response) {
                if (response.raw().isSuccessful()) {
                    databaseHelper.createTeacherTermCondition(response.body());
                }
                tempTerm += response.body().getTeacherTermCondition().getDescription();
                termCondition.setText(tempTerm);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<TeacherTermConditionSuccess> call, Throwable t) {
                tempTerm += databaseHelper.getTeacherTermCondition().getDescription();
                termCondition.setText(tempTerm);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

}
