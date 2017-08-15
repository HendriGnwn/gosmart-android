package com.atc.gosmartlesmagistra.api;

import com.atc.gosmartlesmagistra.model.request.OrderRequest;
import com.atc.gosmartlesmagistra.model.request.RegisterStudentRequest;
import com.atc.gosmartlesmagistra.model.request.RegisterTeacherRequest;
import com.atc.gosmartlesmagistra.model.response.LoginSuccess;
import com.atc.gosmartlesmagistra.model.response.LogoutSuccess;
import com.atc.gosmartlesmagistra.model.response.OrderSuccess;
import com.atc.gosmartlesmagistra.model.response.ResponseSuccess;
import com.atc.gosmartlesmagistra.model.response.TeacherTermConditionSuccess;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by hendrigunawan on 07/07/17.
 */

public interface OrderApi {

    @GET("order/show/{uniqueNumber}")
    @Headers({
            "Content-Type: application/json"
    })
    Call<OrderSuccess> orderShow(@Path("uniqueNumber") String uniqueNumber);

    @POST("order/create/{uniqueNumber}")
    @Headers({
            "Content-Type: application/json"
    })
    Call<OrderSuccess> orderCreate(@Path("uniqueNumber") String uniqueNumber, @Body OrderRequest body);

    @DELETE("order/delete/{uniqueNumber}/{orderId}")
    @Headers({
            "Content-Type: application/json"
    })
    Call<ResponseSuccess> orderDelete(@Path("uniqueNumber") String uniqueNumber, @Path("orderId") Integer orderId);
}
