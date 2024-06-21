package com.jds.eduTech.services;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface EduTechServices {

  @Headers({"Content-Type: application/json"})
  @POST("login")
  Call<JsonObject> getLogin(@Body JsonObject data);

  @Headers({"Content-Type: application/json"})
  @POST("createOrder")
  Call<JsonObject> createOrder(@Body JsonObject data);

  @Headers({"Content-Type: application/json"})
  @GET("categories")
  Call<JsonArray> getCategories();

  @Headers({"Content-Type: application/json"})
  @POST("orders")
  Call<JsonObject> getOrders(@Body JsonObject data);

  @Headers({"Content-Type: application/json"})
  @GET("getproducts/{code}")
  Call<JsonArray> getProducts(@Path("code") String code);

  @Headers({"Content-Type: application/json"})
  @GET("vieworder/{code}")
  Call<JsonObject> getOrderFullDetails(@Path("code") String code);

  @Headers({"Content-Type: application/json"})
  @POST("schooldetails")
  Call<JsonObject> getSchoolDetails(@Body JsonObject data);

  @Headers({"Content-Type: application/json"})
  @POST("updateorder")
  Call<JsonObject> updateOrder(@Body JsonObject data);

  @Headers({"Content-Type: application/json"})
  @POST("updateschoolprofile")
  Call<JsonObject> updateschoolprofile(@Body JsonObject data);

}
