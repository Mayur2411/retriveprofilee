package com.example.retriveprofile;

import com.google.gson.JsonObject;
import com.squareup.okhttp.ResponseBody;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIService {
    @FormUrlEncoded
    @POST("createUser")
    Call<JsonObject> getStringScalar(@Field("fullname") String fullname, @Field("email")
            String email, @Field("password") String password, @Field("phonenumber") String phonenumber, @Field("address") String address);

    @FormUrlEncoded
    @POST("authenticateUser")
    Call<JsonObject> getAuthUser(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("userFetch")
    Call<JsonObject> fetchUser(@Field("email") String email);

    @FormUrlEncoded
    @POST("updateUser")
    Call<JsonObject> updateUsers(@Field("fullname") String fullname, @Field("email")
            String email, @Field("phonenumber") String phonenumber, @Field("address") String address);

}

