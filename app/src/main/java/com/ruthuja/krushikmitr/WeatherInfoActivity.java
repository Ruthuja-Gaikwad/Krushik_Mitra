package com.ruthuja.krushikmitr;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.*;

import org.json.JSONException;
import org.json.JSONObject;

public class WeatherInfoActivity extends AppCompatActivity {
    private static final int LOCATION_PERMISSION_REQUEST = 100;
    private static final String API_KEY = "YOUR_API_KEY_HERE"; // Replace with your OpenWeather API Key

    private TextView tvTemperature, tvWeatherDescription, tvLocation, tvWindSpeed, tvHumidity, tvPressure;
    private ImageView ivWeatherIcon;
    private FusedLocationProviderClient fusedLocationClient;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_info);

        // Initialize UI components
        ivWeatherIcon = findViewById(R.id.iv_weather_icon);
        tvTemperature = findViewById(R.id.tv_temperature);
        tvWeatherDescription = findViewById(R.id.tv_weather_description);
        tvLocation = findViewById(R.id.tv_location);
        tvWindSpeed = findViewById(R.id.tv_wind_speed);
        tvHumidity = findViewById(R.id.tv_humidity);
        tvPressure = findViewById(R.id.tv_pressure);

        // Initialize services
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        requestQueue = Volley.newRequestQueue(this);

        // Request location permission
        checkLocationPermission();
    }

    private void checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_PERMISSION_REQUEST);
        } else {
            // Permission already granted
            requestLocation();
        }
    }

    private void requestLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
                if (location != null) {
                    fetchWeatherData(location.getLatitude(), location.getLongitude());
                } else {
                    requestNewLocation();
                }
            }).addOnFailureListener(e -> Toast.makeText(WeatherInfoActivity.this, "Failed to get location", Toast.LENGTH_SHORT).show());
        }
    }

    private void requestNewLocation() {
        LocationRequest locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(5000)
                .setFastestInterval(2000)
                .setNumUpdates(1);

        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null && locationResult.getLastLocation() != null) {
                    fetchWeatherData(locationResult.getLastLocation().getLatitude(), locationResult.getLastLocation().getLongitude());
                }
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, getMainLooper());
        }
    }

    private void fetchWeatherData(double latitude, double longitude) {
        String url = "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude +
                "&lon=" + longitude + "&appid=" + API_KEY + "&units=metric";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        // Extract Weather Information
                        String cityName = response.getString("name");
                        JSONObject main = response.getJSONObject("main");
                        double temp = main.getDouble("temp");
                        int pressure = main.getInt("pressure");
                        int humidity = main.getInt("humidity");
                        double windSpeed = response.getJSONObject("wind").getDouble("speed");
                        String weatherDesc = response.getJSONArray("weather")
                                .getJSONObject(0).getString("description");
                        String iconCode = response.getJSONArray("weather")
                                .getJSONObject(0).getString("icon");

                        // Update UI
                        tvLocation.setText(cityName);
                        tvTemperature.setText(temp + "°C");
                        tvWeatherDescription.setText(weatherDesc);
                        tvWindSpeed.setText("Wind: " + windSpeed + " km/h");
                        tvHumidity.setText("Humidity: " + humidity + "%");
                        tvPressure.setText("Pressure: " + pressure + " hPa");

                        // Update Weather Icon
                        updateWeatherIcon(iconCode);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(WeatherInfoActivity.this, "Error parsing data", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(WeatherInfoActivity.this, "Failed to get weather data", Toast.LENGTH_SHORT).show());

        requestQueue.add(request);
    }

    private void updateWeatherIcon(String iconCode) {
        int iconResource;
        switch (iconCode) {
            case "01d": case "01n": iconResource = R.drawable.ic_sunny; break;
            case "02d": case "02n": iconResource = R.drawable.ic_partly_cloudy; break;
            case "03d": case "03n": iconResource = R.drawable.ic_cloudy; break;
            case "04d": case "04n": iconResource = R.drawable.ic_broken_clouds; break;
            case "09d": case "09n": iconResource = R.drawable.ic_rainy; break;
            case "10d": case "10n": iconResource = R.drawable.ic_rain; break;
            case "11d": case "11n": iconResource = R.drawable.ic_thunderstorm; break;
            case "13d": case "13n": iconResource = R.drawable.ic_snow; break;
            case "50d": case "50n": iconResource = R.drawable.ic_foggy; break;
            default: iconResource = R.drawable.ic_weather; // Default weather icon
        }
        ivWeatherIcon.setImageResource(iconResource);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requestLocation();
            } else {
                Toast.makeText(this, "Location permission required", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
