package com.atc.gosmartlesmagistra.api;

import com.atc.gosmartlesmagistra.model.request.LoginRequest;
import com.atc.gosmartlesmagistra.model.request.RegisterStudentRequest;
import com.atc.gosmartlesmagistra.model.request.RegisterTeacherRequest;
import com.atc.gosmartlesmagistra.model.response.LoginSuccess;
import com.atc.gosmartlesmagistra.model.response.LogoutSuccess;
import com.atc.gosmartlesmagistra.model.response.TeacherTermConditionSuccess;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by hendrigunawan on 07/07/17.
 */

public interface RequestApi {

    @GET("teacher-term-condition")
    @Headers({
            "Content-Type: application/json"
    })
    Call<TeacherTermConditionSuccess> teacherTermCondition();

    @POST("auth/register-student")
    @Headers({
            "Content-Type: application/json"
    })
    Call<LoginSuccess> registerStudent(@Body RegisterStudentRequest body);

    @POST("auth/register-teacher")
    @Headers({
            "Content-Type: application/json"
    })
    Call<LoginSuccess> registerTeacher(@Body RegisterTeacherRequest body);
    @POST("auth/logout")
    @Headers({
            "Content-Type: application/json"
    })
    Call<LogoutSuccess> logout();
}
