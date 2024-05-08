package com.example.note;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class NoteAdapter extends ArrayAdapter<NoteModel> {
    private Context context;
    private int resource;
    List<NoteModel> noteModels;

    NoteAdapter(Context context, int resource, List<NoteModel> noteModels){

        super(context, resource, noteModels);
        this.context = context;
        this.resource = resource;
        this.noteModels = noteModels;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(resource,parent,false);

        TextView title = row.findViewById(R.id.title);
        TextView description = row.findViewById(R.id.description);


        NoteModel noteModel = noteModels.get(position);
        title.setText(noteModel.getTitle());
        description.setText(noteModel.getDescription());



        return row;
    }
}
