package com.example.recognitiontext;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;



import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Rewindow extends Fragment {
    private ImageView image;
    private TextView descript;
    private Uri imageUri;
    private final TextRecognizer textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.rewindow, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FloatingActionButton addButton = view.findViewById(R.id.addButton);
        image = view.findViewById(R.id.image);
        descript = view.findViewById(R.id.description);
        getImageUri();
        ActivityResultLauncher<Uri> takePhoto = registerForActivityResult(
                new ActivityResultContracts.TakePicture(),
                isSuccess -> {
                    if (isSuccess) processImage();
                }
        );
        addButton.setOnClickListener(v -> {
            takePhoto.launch(imageUri);
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        textRecognizer.close();
    }

    private void processImage() {
        image.setImageURI(imageUri);
        try {
            Task<Text> recognizeTask = textRecognizer.process(InputImage.fromFilePath(getContext(),imageUri));
            recognizeTask.
                    addOnSuccessListener(text -> {
                        descript.setText(text.getText());
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    private void getImageUri() {
        File file = new File(getContext().getFilesDir(), "pictureFromCamera");
        imageUri = FileProvider.getUriForFile(getContext(), getContext().getApplicationContext().getPackageName() + ".provider", file);
    }


    }