package com.ruthuja.krushikmitr;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

        // Link UI elements (Fixed IDs)
        userInput = findViewById(R.id.editTextUserInput);
        sendButton = findViewById(R.id.buttonSend);
        responseView = findViewById(R.id.textViewResponse);

        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://generativelanguage.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(GeminiApiService.class);

        sendButton.setOnClickListener(v -> sendMessage());
    }

    private void sendMessage() {
        String message = userInput.getText().toString().trim();
        if (message.isEmpty()) {
            Toast.makeText(this, R.string.enter_message, Toast.LENGTH_SHORT).show();
            return;
        }

        GeminiRequest request = new GeminiRequest(message);
        apiService.generateResponse(request).enqueue(new Callback<GeminiResponse>() {
            @Override
            public void onResponse(Call<GeminiResponse> call, Response<GeminiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    responseView.setText(response.body().getContent());
                } else {
                    responseView.setText(getString(R.string.error_chatbot_response));
                }
            }

            @Override
            public void onFailure(Call<GeminiResponse> call, Throwable t) {
                responseView.setText(getString(R.string.api_call_failed));
                Toast.makeText(ChatbotActivity.this, R.string.api_call_failed, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
