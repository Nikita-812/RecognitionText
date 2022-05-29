package com.example.recognitiontext.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.recognitiontext.R;
import com.example.recognitiontext.db.TextDb;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;

public class NotePreview extends Fragment {

    public final static String DEL_TAG = "DeleteNote";
    public final static String RESULT = "NotePreview_Result";
    public final static String ARG_ITEM = "Result_ItemNote";
    public final static String ARG_ID = "arg_id";
    private EditText editText;

    public static Fragment newInstance(TextDb item){
        Fragment fragment = new NotePreview();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_ID, item);
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


        FloatingActionButton saveButton = view.findViewById(R.id.save);
        editText = view.findViewById(R.id.description);
        FloatingActionButton delButton = view.findViewById(R.id.delButton);

        Bundle arguments = getArguments();

        TextDb lineFromDB = (TextDb) arguments.getSerializable(ARG_ID);
        if(lineFromDB!=null) editText.setText(lineFromDB.text);
        else {
            lineFromDB = new TextDb(1L,"no text");
            editText.setText(lineFromDB.text);
        }


        TextDb finalLineFromDB = lineFromDB;
        saveButton.setOnClickListener(v->{
            Log.d("MYLog","save is send");
            Bundle bundle = new Bundle();
            bundle.putBoolean(DEL_TAG,false);

            bundle.putSerializable(ARG_ITEM,new TextDb(finalLineFromDB.id, editText.getText().toString()));
            getParentFragmentManager().setFragmentResult(RESULT, bundle);

        });


        TextDb finalLineFromDB1 = lineFromDB;
        delButton.setOnClickListener(v -> {
            Log.d("MYLog","del is send");
            Bundle bundle = new Bundle();
            bundle.putBoolean(DEL_TAG,true);
            bundle.putSerializable(ARG_ITEM,new TextDb(finalLineFromDB1.id,editText.getText().toString()));
            getParentFragmentManager().setFragmentResult(RESULT,bundle);
            requireActivity().getSupportFragmentManager().popBackStack();

        });


    }
}
