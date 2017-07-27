package com.atc.gosmartlesmagistra.api;

import com.atc.gosmartlesmagistra.model.request.ChangePasswordRequest;
import com.atc.gosmartlesmagistra.model.request.ForgotPasswordRequest;
import com.atc.gosmartlesmagistra.model.request.LoginRequest;
import com.atc.gosmartlesmagistra.model.request.RegisterStudentRequest;
import com.atc.gosmartlesmagistra.model.request.RegisterTeacherRequest;
import com.atc.gosmartlesmagistra.model.response.ForgotPasswordSuccess;
import com.atc.gosmartlesmagistra.model.response.LoginSuccess;
import com.atc.gosmartlesmagistra.model.response.LogoutSuccess;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by hendrigunawan on 07/07/17.
 */

public interface UserApi {

    @POST("auth/login")
    @Headers({
            "Content-Type: application/json"
    })
    Call<LoginSuccess> login(@Body LoginRequest body);

    @POST("auth/forgot-password")
    @Headers({
            "Content-Type: application/json"
    })
    Call<ForgotPasswordSuccess> forgotPassword(@Body ForgotPasswordRequest body);

    @POST("auth/register-student")
    @Headers({
            "Content-Type: application/json"
    })
    Call<LoginSuccess> registerStudent(@Body RegisterStudentRequest body);

    @POST("auth/change-password/{uniqueNumber}")
    @Headers({
            "Content-Type: application/json"
    })
    Call<LoginSuccess> changePassword(@Path("uniqueNumber") String uniqueNumber, @Body ChangePasswordRequest body);

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

    @GET("user/get-by-unique/{uniqueNumber}")
    @Headers({
            "Content-Type: application/json"
    })
    Call<LoginSuccess> getUserByUniqueNumber(@Path("uniqueNumber") String uniqueNumber);
}
