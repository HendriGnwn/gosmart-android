package com.atc.gosmartlesmagistra.api;

import com.atc.gosmartlesmagistra.model.request.RegisterStudentRequest;
import com.atc.gosmartlesmagistra.model.request.RegisterTeacherRequest;
import com.atc.gosmartlesmagistra.model.response.CourseAvailabilitiesSuccess;
import com.atc.gosmartlesmagistra.model.response.CoursesSuccess;
import com.atc.gosmartlesmagistra.model.response.LoginSuccess;
import com.atc.gosmartlesmagistra.model.response.LogoutSuccess;
import com.atc.gosmartlesmagistra.model.response.TeacherTermConditionSuccess;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by hendrigunawan on 07/07/17.
 */

public interface CourseApi {

    @GET("courses")
    @Headers({
            "Content-Type: application/json"
    })
    Call<CoursesSuccess> courses(@Query("name") String name);

    @GET("courses-availability")
    @Headers({
            "Content-Type: application/json"
    })
    Call<CourseAvailabilitiesSuccess> courseAvailabilities(
            @Query("course_id") Integer courseId,
            @Query("name") String name,
            @Query("latitude") String latitude,
            @Query("longitude") String longitude,
            @Query("radius") String radius
    );

}
