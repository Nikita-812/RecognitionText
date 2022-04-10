package com.example.recognitiontext;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private final ArrayList<ItemNote> adapter = new ArrayList<>();
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(adapter.get(position));
    }

    @Override
    public int getItemCount() {
        return adapter.size();
    }
    public void setAdapter(List<ItemNote> adapter){
        this.adapter.clear();
        this.adapter.addAll(adapter);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView description;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        void bind(ItemNote item){
            description.findViewById(R.id.description);
            description.setText(item.getName());
        }
    }
}
