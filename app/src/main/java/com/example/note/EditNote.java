package com.example.note;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EditNote extends AppCompatActivity {

    private EditText title, description;
    private Button edit;
    private DbHandler dbHandler;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        context = this;
        dbHandler = new DbHandler(context);
        title = findViewById(R.id.editTextTitle);
        description = findViewById(R.id.editTextDescription);
        edit = findViewById(R.id.buttonEdit);
        final String id = getIntent().getStringExtra("id");
        NoteModel noteModel = dbHandler.getSingleNoteModel(Integer.parseInt(id));
        title.setText(noteModel.getTitle());
        description.setText(noteModel.getDescription());


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titletext = title.getText().toString();
                String descriptiontext = description.getText().toString();

                NoteModel noteModel = new NoteModel(Integer.parseInt(id), titletext, descriptiontext);
                int state = dbHandler.updatesingleNoteModel(noteModel);
                startActivity(new Intent(context, Note.class));
            }
        });

    }
}
