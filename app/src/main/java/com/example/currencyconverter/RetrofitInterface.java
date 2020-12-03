package com.example.currencyconverter;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitInterface {
    @GET("v6/40f804bfbef9457bb6ac7f88/latest/{currency}")
    Call<JsonObject> getExchangeCurrency(@Path("currency") String currency);
}
