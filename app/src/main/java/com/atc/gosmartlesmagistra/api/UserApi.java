package com.atc.gosmartlesmagistra.api;

import com.atc.gosmartlesmagistra.model.request.LoginRequest;
import com.atc.gosmartlesmagistra.model.response.LoginSuccess;
import com.atc.gosmartlesmagistra.model.response.LogoutSuccess;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by hendrigunawan on 07/07/17.
 */

public interface UserApi {

    @POST("auth/login")
    @Headers({
            "Content-Type: application/json"
    })
    Call<LoginSuccess> login(@Body LoginRequest body);

    @POST("auth/logout")
    @Headers({
            "Content-Type: application/json"
    })
    Call<LogoutSuccess> logout();
}
