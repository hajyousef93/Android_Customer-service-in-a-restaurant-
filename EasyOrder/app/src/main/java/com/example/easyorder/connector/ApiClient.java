package com.example.easyorder.connector;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static String url = "http://192.168.43.73:8080/easy order/";
    private static Retrofit retrofit;


    public static Retrofit getApiClient() {
        if(retrofit == null)
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        return retrofit;
    }
}
