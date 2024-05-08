package com.example.note;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class Note extends AppCompatActivity {

    private Button add;
    private Button back;
    private ListView listView;

    Context context;
    private DbHandler dbHandler;
    private List <NoteModel> noteModels;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        context = this;
        dbHandler = new DbHandler(context);


        add = findViewById(R.id.add);
        back = findViewById(R.id.back);
        listView = findViewById(R.id.notelist );

        noteModels = new ArrayList<>();

        noteModels = dbHandler.getAllNoteModels();
        NoteAdapter adapter = new NoteAdapter(context, R.layout.single_note, noteModels);
        listView.setAdapter(adapter);





        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, AddNote.class));
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, MainActivity.class));
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NoteModel noteModel = noteModels.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(noteModel.getTitle());
                builder.setMessage(noteModel.getDescription());
                builder.show();
                builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(context,Note.class));
                    }

                });

                builder.setNegativeButton("Delete", new DialogInterface.OnClickListener()    {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHandler.deleteNoteModel (noteModel.getId());
                        startActivity(new Intent(context,Note.class));
                    }
                });

                builder.setNeutralButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(context, EditNote.class );
                        intent.putExtra("id", String.valueOf(noteModel.getId()));
                        startActivity(intent);
                    }
                });
                builder.show();

            }

        });

    }
}
