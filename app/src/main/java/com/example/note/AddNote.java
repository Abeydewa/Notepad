package com.example.note;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddNote extends AppCompatActivity {
    private EditText title, description;
    private Button add;
    private DbHandler dbHandler;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        context = this;
        title = findViewById(R.id.editTextTitle1);
        description = findViewById(R.id.editTextDescription1);
        add = findViewById(R.id.buttonAdd);
        dbHandler = new DbHandler(context);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userTitle = title.getText().toString();
                String userDescription = description.getText().toString();

                NoteModel noteModel = new NoteModel(userTitle, userDescription);
                dbHandler.addNote(noteModel);

                startActivity(new Intent(context, Note.class));
            }
        });
        Button button = findViewById(R.id.button5);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AddNote.this, Note.class);
                startActivity(intent);
            }

        });
    }
}
