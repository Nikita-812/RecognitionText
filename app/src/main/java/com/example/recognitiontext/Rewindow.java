package com.example.recognitiontext;

import static com.example.recognitiontext.NotePreview.DEL_TAG;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class Rewindow extends Fragment {
    private final NoteAdapter adapter = new NoteAdapter(this::onClickNote);

    private TextDao dao;
    private Uri imageUri;
    private final TextRecognizer textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.rewindow, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dao = App.getAppDatabaseInstance().textDao(); //create database
        FloatingActionButton addButton = view.findViewById(R.id.addButton);
        Log.d("MyLog", "create dao");
        recyclerView = view.findViewById(R.id.recycler);//create recycler
        initRecycler();
        addResultListener();
        getImageUri();
        ActivityResultLauncher<Uri> takePhoto = registerForActivityResult(//do photo
                new ActivityResultContracts.TakePicture(),
                isSuccess -> {
                    Log.d("MyLog", "did photo");
                    if (isSuccess) processImage();
                }
        );
        addButton.setOnClickListener(v -> {
            takePhoto.launch(imageUri);
        });
    }

    private void initRecycler() {
        Log.d("MyLog", "init recycler");
        recyclerView.setAdapter(adapter);
        dao.getAllToItemNote().observe(requireActivity(), itemNotes -> {
            Log.d("MyLog", "data complete: " + itemNotes.size());
            adapter.setItems(itemNotes);
        });


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        textRecognizer.close();
    }

    private void processImage() {
        try {
            Task<Text> recognizeTask = textRecognizer.process(InputImage.fromFilePath(requireActivity(), imageUri));
            recognizeTask.
                    addOnSuccessListener(text -> {
                        if (text != null) {
                            Log.d("MyLog", "recognition correct");
                            new Thread(() -> dao.insert(new TextDb(text.getText()))).start();
                            adapter.addItem(new TextDb(text.getText()));
                        } else {
                            Log.d("Mylog", "recognititon uncorrect");
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
    private void addResultListener() {
        getParentFragmentManager().setFragmentResultListener(
                NotePreview.RESULT,
                this,
                (requestKey, result) -> {
                    TextDb note =(TextDb) result.getSerializable(NotePreview.ARG_ITEM);
                    if(result.getBoolean(DEL_TAG)){
                        Log.d("MYLog","delete");
                        new Thread(() -> dao.delete(note)).start();
                        adapter.deleteItem(note);

                        Log.d("MYLog","unsubscribe");
                    }
                    else{
                        Log.d("MYLog","save");

                        new Thread(() -> dao.update(note)).start();
                    }
                }
        );
    }
    private void onClickNote(@NonNull TextDb note) {
        dao.getStringForNote(note.id).observe(requireActivity(), itemNotes -> {
            Log.d("MYLog","2x");
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, NotePreview.newInstance(itemNotes))
                    .addToBackStack(null)
                    .commit();
        });




    }
    private void getImageUri() {
        File file = new File(requireActivity().getFilesDir(), "pictureFromCamera");
        imageUri = FileProvider.getUriForFile(requireActivity(), requireActivity().getPackageName() + ".provider", file);
    }
}
