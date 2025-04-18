package com.ruthuja.krushikmitr;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface GeminiApiService {
    @Headers("Content-Type: application/json")
    @POST("v1beta/models/gemini-2.0-flash:generateContent?key=AIzaSyB6D2-DNh6kGqaU5QrtfOjyOqq7U4bWKxw")
    Call<GeminiResponse> generateResponse(@Body GeminiRequest request);
}
