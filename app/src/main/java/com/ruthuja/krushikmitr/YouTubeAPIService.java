package com.ruthuja.krushikmitr;import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YouTubeAPIService {
    @GET("search")
    Call<YouTubeResponse> searchVideos(
            @Query("part") String part,
            @Query("q") String query,
            @Query("key") String apiKey,
            @Query("type") String type,
            @Query("maxResults") int maxResults
    );
}
