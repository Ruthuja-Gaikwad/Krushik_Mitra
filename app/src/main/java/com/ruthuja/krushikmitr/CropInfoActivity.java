package com.ruthuja.krushikmitr;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.image.ImageProcessor;
import org.tensorflow.lite.support.image.ops.ResizeOp;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CropInfoActivity extends AppCompatActivity {
    private static final int REQUEST_CAMERA_PERMISSION = 100;

    private Button btnUploadImage, btnUploadGallery, btnDetectDisease;
    private ImageView ivCropPreview;
    private TextView tvDiseaseResult, tvSuggestion;
    private ProgressBar progressBar;
    private Bitmap cropImage;
    private Interpreter tflite;
    private List<String> labels = new ArrayList<>();
    private ExecutorService executorService;

    private final ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Bundle extras = result.getData().getExtras();
                    if (extras != null) {
                        cropImage = (Bitmap) extras.get("data");
                        ivCropPreview.setImageBitmap(cropImage);
                    }
                }
            }
    );

    private final ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Uri selectedImage = result.getData().getData();
                    try {
                        cropImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                        ivCropPreview.setImageBitmap(cropImage);
                    } catch (IOException e) {
                        Log.e("ERROR", "Failed to load image", e);
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_info);

        initializeUI();
        executorService = Executors.newSingleThreadExecutor();
        executorService.execute(this::loadModelFile);
    }

    private void initializeUI() {
        btnUploadImage = findViewById(R.id.btn_upload_image);
        btnUploadGallery = findViewById(R.id.btn_upload_gallery);
        btnDetectDisease = findViewById(R.id.btn_detect_disease);
        ivCropPreview = findViewById(R.id.iv_crop_preview);
        tvDiseaseResult = findViewById(R.id.tv_disease_result);
        tvSuggestion = findViewById(R.id.tv_suggestion);
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);

        btnUploadImage.setOnClickListener(v -> checkCameraPermission());
        btnUploadGallery.setOnClickListener(v -> galleryLauncher.launch(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)));
        btnDetectDisease.setOnClickListener(v -> detectDisease());
    }

    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        } else {
            cameraLauncher.launch(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
        }
    }

    private void detectDisease() {
        if (cropImage == null || tflite == null || labels.isEmpty()) {
            showToast("Please upload an image and ensure model is loaded!");
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        executorService.execute(() -> {
            TensorImage inputImage = preprocessImage(cropImage);
            TensorBuffer outputBuffer = TensorBuffer.createFixedSize(new int[]{1, labels.size()}, tflite.getOutputTensor(0).dataType());
            tflite.run(inputImage.getBuffer(), outputBuffer.getBuffer());

            int maxIndex = getMaxIndex(outputBuffer.getFloatArray());
            String detectedDisease = labels.get(maxIndex);
            String recommendation = getRecommendation(detectedDisease);

            runOnUiThread(() -> {
                tvDiseaseResult.setText("Detected: " + detectedDisease);
                tvSuggestion.setText("Recommendation: " + recommendation);
                progressBar.setVisibility(View.GONE);
            });
        });
    }

    private String getRecommendation(String disease) {
        switch (disease) {
            case "Blight":
                return "Use copper-based fungicides and remove infected leaves.";
            case "Rust":
                return "Apply sulfur or neem oil sprays.";
            case "Mosaic Virus":
                return "Remove affected plants and control aphids.";
            default:
                return "Keep monitoring your plant.";
        }
    }

    private int getMaxIndex(float[] outputs) {
        int maxIndex = 0;
        float maxConfidence = outputs[0];
        for (int i = 1; i < outputs.length; i++) {
            if (outputs[i] > maxConfidence) {
                maxConfidence = outputs[i];
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    private TensorImage preprocessImage(Bitmap bitmap) {
        ImageProcessor imageProcessor = new ImageProcessor.Builder()
                .add(new ResizeOp(224, 224, ResizeOp.ResizeMethod.BILINEAR))
                .build();

        TensorImage tensorImage = new TensorImage(tflite.getInputTensor(0).dataType());
        tensorImage.load(bitmap);
        return imageProcessor.process(tensorImage);
    }

    private void loadModelFile() {
        try {
            FileInputStream fis = new FileInputStream(getFilesDir() + "/plant_disease_model_with_metadata.tflite");
            FileChannel fileChannel = fis.getChannel();
            MappedByteBuffer modelBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size());
            tflite = new Interpreter(modelBuffer);
            labels = loadLabels("labels.txt");
        } catch (IOException e) {
            Log.e("ERROR", "Failed to load model", e);
        }
    }

    private List<String> loadLabels(String filePath) throws IOException {
        List<String> labels = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open(filePath)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                labels.add(line);
            }
        }
        return labels;
    }

    private void showToast(String message) {
        runOnUiThread(() -> Toast.makeText(this, message, Toast.LENGTH_SHORT).show());
    }
}
