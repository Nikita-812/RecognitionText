package com.example.recognitiontext;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NotePreview extends Fragment {
    public final static String TAG = "NotePreview";
    public final static String RESULT = "NotePreview_Result";
    public final static String ARG_ITEM = "Result_ItemNote";
    public final static String ARG_ID = "arg_id";
    private FloatingActionButton delButton;
    private EditText editText;
    private FloatingActionButton saveButton;

    public static Fragment newInstance(String s){
        Fragment fragment = new NotePreview();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_ID,s);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.note, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        saveButton = view.findViewById(R.id.save);
        editText = view.findViewById(R.id.description);
        delButton = view.findViewById(R.id.delButton);

        Bundle arguments = getArguments();

        assert arguments != null;
        String lineFromDB = getArguments().getString(ARG_ID,"No text");
        editText.setText(lineFromDB);


        saveButton.setOnClickListener(v->{
            String s = editText.toString();
            Bundle bundle = new Bundle();
            bundle.putSerializable(ARG_ITEM,new ItemNote(s));
            getParentFragmentManager().setFragmentResult(RESULT, bundle);
        });


        delButton.setOnClickListener(v -> {

        });


    }
}
