package com.example.retriveprofile;

public class ApiUtils {
    private ApiUtils() {}

    public static final String BASE_URL = "http://192.168.2.14:8000/";

    public static APIService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
