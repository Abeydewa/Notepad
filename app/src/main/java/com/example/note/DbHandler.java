package com.example.note;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

public class DbHandler extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DB_NAME = "note";
    private static final String TABLE_NAME = "note";

    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";

    public DbHandler(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String TABLE_CREATE_QUERY = "CREATE TABLE "+TABLE_NAME+" " +
                "("
                +ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +TITLE + " TEXT,"
                +DESCRIPTION + " TEXT" + " );";

        db.execSQL(TABLE_CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS "+ TABLE_NAME;

        db.execSQL(DROP_TABLE_QUERY);

        onCreate(db);
    }
    public void addNote(NoteModel noteModel){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(TITLE,noteModel.getTitle());
        contentValues.put(DESCRIPTION,noteModel.getDescription());

        sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        sqLiteDatabase.close();

    }
   /* public int countPeople(){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM "+ TABLE_NAME;

        Cursor cursor = db.rawQuery(query,null);
        return cursor.getCount();
    } */

    public List<NoteModel> getAllNoteModels(){

        List<NoteModel> noteModels = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM "+TABLE_NAME;

        Cursor cursor = db.rawQuery(query,null);

        if(cursor.moveToFirst()){
            do {

                NoteModel noteModel = new NoteModel();

                noteModel.setId(cursor.getInt(0));
                noteModel.setTitle(cursor.getString(1));
                noteModel.setDescription(cursor.getString(2));



                noteModels.add(noteModel);
            }while (cursor.moveToNext());
        }
        return noteModels;
    }

    public void deleteNoteModel(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, ID + "=?", new String[]{String.valueOf(id)});
        db.close();

    }
    public NoteModel getSingleNoteModel(int id){
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.query(TABLE_NAME,new String[]{ID,TITLE,DESCRIPTION},
                ID + "= ?",new String[]{String.valueOf(id)}
                ,null,null,null);

        NoteModel noteModel;
        if(cursor != null){
            cursor.moveToFirst();
            noteModel = new NoteModel(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2)
            );
            return noteModel;
        }
        return null;
    }

    public int updatesingleNoteModel(NoteModel noteModel){


        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(TITLE,noteModel.getTitle());
        contentValues.put(DESCRIPTION,noteModel.getDescription());

        int status = db.update(TABLE_NAME, contentValues, ID + "=?",
                new String[]{String.valueOf(noteModel.getId())});

        db.close();;
        return status;
    }




}

