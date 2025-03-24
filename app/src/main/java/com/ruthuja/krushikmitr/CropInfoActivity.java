package com.ruthuja.krushikmitr;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;

public class CropInfoActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int IMAGE_SIZE = 224; // Model input size

    private ImageView ivCropPreview;
    private TextView tvDiseaseResult, tvSuggestion;
    private Interpreter tflite;
    private Button btnUploadImage, btnDetectDisease;
    private Bitmap cropImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_info);

        ivCropPreview = findViewById(R.id.iv_crop_preview);
        tvDiseaseResult = findViewById(R.id.tv_disease_result);
        tvSuggestion = findViewById(R.id.tv_suggestion);
        btnUploadImage = findViewById(R.id.btn_upload_image);
        btnDetectDisease = findViewById(R.id.btn_detect_disease);

        // Load TensorFlow Lite Model
        try {
            tflite = new Interpreter(loadModelFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Capture Image from Camera
        btnUploadImage.setOnClickListener(v -> {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        });

        // Detect Disease
        btnDetectDisease.setOnClickListener(v -> {
            if (cropImage != null) {
                String disease = detectDisease(cropImage);
                tvDiseaseResult.setText("Detected: " + disease);
                tvDiseaseResult.setVisibility(View.VISIBLE);

                // Fetch & Display Recommendations
                String suggestion = getRecommendation(disease);
                tvSuggestion.setText("Recommendation: " + suggestion);
                tvSuggestion.setVisibility(View.VISIBLE);
            }
        });
    }

    // Process Captured Image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            cropImage = (Bitmap) extras.get("data");
            ivCropPreview.setImageBitmap(cropImage);
        }
    }

    // Load the ML Model
    private MappedByteBuffer loadModelFile() throws IOException {
        AssetFileDescriptor fileDescriptor = getAssets().openFd("plant_disease_model.tflite");
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    // Run Image through ML Model
    private String detectDisease(Bitmap image) {
        Bitmap resizedImage = preprocessImage(image);
        ByteBuffer inputBuffer = convertBitmapToByteBuffer(resizedImage);
        float[][] output = new float[1][1];
        tflite.run(inputBuffer, output);
        return classifyDisease(output[0][0]);
    }

    // Preprocess Image to Match Model Input Requirements
    private Bitmap preprocessImage(Bitmap bitmap) {
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, IMAGE_SIZE, IMAGE_SIZE, true);
        Bitmap newBitmap = Bitmap.createBitmap(IMAGE_SIZE, IMAGE_SIZE, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        canvas.drawBitmap(scaledBitmap, new Matrix(), paint);
        return newBitmap;
    }

    // Convert Bitmap to ByteBuffer
    private ByteBuffer convertBitmapToByteBuffer(Bitmap bitmap) {
        ByteBuffer buffer = ByteBuffer.allocateDirect(IMAGE_SIZE * IMAGE_SIZE * 3 * 4);
        buffer.order(ByteOrder.nativeOrder());
        int[] pixels = new int[IMAGE_SIZE * IMAGE_SIZE];
        bitmap.getPixels(pixels, 0, IMAGE_SIZE, 0, 0, IMAGE_SIZE, IMAGE_SIZE);
        for (int pixel : pixels) {
            buffer.putFloat(((pixel >> 16) & 0xFF) / 255.0f); // Red
            buffer.putFloat(((pixel >> 8) & 0xFF) / 255.0f);  // Green
            buffer.putFloat((pixel & 0xFF) / 255.0f);         // Blue
        }
        return buffer;
    }

    // Classify Disease Based on Model Output
    private String classifyDisease(float index) {
        String[] diseases = {"Healthy", "Leaf Spot", "Blight", "Rust"};
        int classIndex = (int) Math.min(Math.max(index, 0), diseases.length - 1); // Ensure valid index
        return diseases[classIndex];
    }

    // Get Recommendation Based on Disease
    private String getRecommendation(String disease) {
        Map<String, String> recommendations = new HashMap<>();
        recommendations.put("Healthy", "No issues detected. Maintain regular watering and fertilization.");
        recommendations.put("Leaf Spot", "Use copper-based fungicide and remove infected leaves.");
        recommendations.put("Blight", "Apply organic fungicides and avoid overhead watering.");
        recommendations.put("Rust", "Use sulfur-based fungicide and ensure good air circulation.");
        return recommendations.getOrDefault(disease, "No recommendations available.");
    }
}
