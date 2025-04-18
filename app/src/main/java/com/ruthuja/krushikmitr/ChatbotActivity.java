package com.ruthuja.krushikmitr;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatbotActivity extends AppCompatActivity {

    private EditText userInput;
    private ImageView sendButton;
    private TextView responseView;
    private GeminiApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);

        // Link UI elements
        userInput = findViewById(R.id.editTextUserInput);
        sendButton = findViewById(R.id.buttonSend);
        responseView = findViewById(R.id.textViewResponse);

        // Initialize Retrofit with timeout settings
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://generativelanguage.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        apiService = retrofit.create(GeminiApiService.class);

        // Set click listener
        sendButton.setOnClickListener(v -> sendMessage());
    }

    private void sendMessage() {
        String message = userInput.getText().toString().trim();
        if (message.isEmpty()) {
            Toast.makeText(this, "Please enter a message", Toast.LENGTH_SHORT).show();
            return;
        }

        hideKeyboard(); // Hide keyboard when sending a message
        Log.d("Chatbot", "Sending message: " + message);

        String instruction = "Answer if the query is about agriculture. Answer in minimum of 5 lines and maximum of 15 lines. ";
        GeminiRequest request = new GeminiRequest(instruction + message);

        apiService.generateResponse(request).enqueue(new Callback<GeminiResponse>() {
            @Override
            public void onResponse(Call<GeminiResponse> call, Response<GeminiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String chatbotResponse = response.body().getContent();

                    chatbotResponse = chatbotResponse.replaceAll("\\*\\*(.*?)\\*\\*", "<b>$1</b>");

                    responseView.setText(HtmlCompat.fromHtml(chatbotResponse, HtmlCompat.FROM_HTML_MODE_LEGACY));

                    Log.d("Chatbot", "Response: " + chatbotResponse);
                } else {
                    try {
                        String errorResponse = response.errorBody() != null ? response.errorBody().string() : "Unknown error";
                        responseView.setText("Error: Unable to get response");
                        Log.e("Chatbot", "API Error: " + errorResponse);
                    } catch (IOException e) {
                        Log.e("Chatbot", "Error reading API error body", e);
                    }
                }
            }

            @Override
            public void onFailure(Call<GeminiResponse> call, Throwable t) {
                responseView.setText("API Call Failed");
                Toast.makeText(ChatbotActivity.this, "Failed to connect to API", Toast.LENGTH_SHORT).show();
                Log.e("Chatbot", "API Call Failed: " + t.getMessage());
            }
        });
    }

    // Properly defined hideKeyboard() method outside sendMessage()
    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if (view != null && imm.isAcceptingText()) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
