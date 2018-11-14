package com.linearbd.automation.api;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface DeviceClient {
    //http://localhost:5000/api/locations/getlocationsbyimeidate

    @GET("/")
    Call<String> test();

    @GET
    Call<String> setState(@Url String url);

}
