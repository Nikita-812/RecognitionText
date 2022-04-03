package com.example.recognitiontext;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private ImageView image;
    private TextView descript;
    private Uri imageUri;
    private final TextRecognizer textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
    static final int REQUEST_IMAGE_CAPTURE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton addButton = findViewById(R.id.addButton);
        image = findViewById(R.id.image);
        descript = findViewById(R.id.description);
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
    protected void onDestroy() {
        super.onDestroy();
        textRecognizer.close();
    }

    private void processImage() {
        image.setImageURI(imageUri);
        try {
            Task<Text> recognizeTask = textRecognizer.process(InputImage.fromFilePath(this,imageUri));
            recognizeTask.
                    addOnSuccessListener(text -> {
                       descript.setText(text.getText());
                    });
        } catch (IOException e) {
            Toast.makeText(this,"Error",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }


    }


    private void getImageUri() {
        File file = new File(getFilesDir(), "pictureFromCamera");
        imageUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", file);
    }
}