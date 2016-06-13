package com.DAO.executors;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.DAO.executors.functionality.Note_Functionality;
import com.DAL.manager.DB_Helper;
import com.DAO.model.Note;
import com.DAO.model.NoteImage;
import com.DAO.model.NoteListViewModel;
import com.DAO.model.NoteMedia;
import com.DAO.tables.Image_Table;
import com.DAO.tables.Media_Table;
import com.DAO.tables.Note_Table;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AMEN on 26.04.2016.
 */
public class Note_Queries implements Note_Functionality {
    private String[] allColumns = {Note_Table.COLUMN_ID,
            Note_Table.COLUMN_TITLE,
            Note_Table.COLUMN_MEMERYTEXT,
            Note_Table.COLUMN_LOCKED,
            Note_Table.COLUMN_DATE,
            Note_Table.COLUMN_MOOD
    };

    private SQLiteDatabase database;
    private DB_Helper dbHelper;

    public  Note_Queries (Context context){
        dbHelper = new DB_Helper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    @Override
    public Note addNote(Note note) {
        ContentValues values = new ContentValues();
        values.put(Note_Table.COLUMN_TITLE,note.getTitle());
        values.put(Note_Table.COLUMN_MEMERYTEXT,note.getMemeryText());
        values.put(Note_Table.COLUMN_LOCKED,(note.isLocked() == true) ? 1 : 0);
        values.put(Note_Table.COLUMN_DATE,note.getDate());
        values.put(Note_Table.COLUMN_MOOD,note.getMood());
        long insertId = database.insert(Note_Table.TABLE_NAME, null, values);

        Cursor cursor = database.query(Note_Table.TABLE_NAME , allColumns , Note_Table.COLUMN_ID + " = " + insertId,
                null,null,null,null);
        cursor.moveToFirst();
        Note newNote = null;
        try {
            newNote = cursorToNote(cursor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        cursor.close();
        return  newNote;

    }

    private Note cursorToNote(Cursor cursor)throws Exception{
        Note n = new Note();
        n.setId(cursor.getInt(0));
        n.setTitle(cursor.getString(1));
        n.setMemeryText(cursor.getString(2));
        n.setIsLocked((cursor.getInt(3) == 1) ? true : false);
        n.setDate(cursor.getLong(4));
        n.setMood(cursor.getInt(5));
        return  n;
    }

    @Override
    public void deleteNote(int noteId) {
        database.delete(Note_Table.TABLE_NAME, Note_Table.COLUMN_ID + " = " + noteId, null);
    }

    @Override
    public Note getNoteById(int noteId) {
        Cursor cursor = database.rawQuery("Select ?,?,?,?,?,? from ? where ? = " + noteId,new
                String[]{Note_Table.COLUMN_ID,Note_Table.COLUMN_TITLE,Note_Table.COLUMN_MEMERYTEXT,
                Note_Table.COLUMN_LOCKED,Note_Table.COLUMN_DATE,Note_Table.COLUMN_MOOD,Note_Table.TABLE_NAME,Note_Table.COLUMN_ID});
        cursor.moveToFirst();
        Note newNote = null;
        try {
            newNote = cursorToNote(cursor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        cursor.close();
        return  newNote;
    }

    @Override
    public List<NoteListViewModel> getAllNotes() {
        List<NoteListViewModel> notes = new ArrayList<NoteListViewModel>();
        Cursor cursor = database.query(Note_Table.TABLE_NAME,allColumns,null, null,null,null,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            NoteListViewModel note = null;
            try {
                note = cursorToNoteListViewModel(cursor);
            } catch (Exception e) {
                e.printStackTrace();
            }
            notes.add(note);
            cursor.moveToNext();
        }
        cursor.close();
        return  notes;
    }

    private NoteListViewModel cursorToNoteListViewModel(Cursor cursor)throws Exception{
        NoteListViewModel n = new NoteListViewModel();
        n.setId(cursor.getInt(0));
        n.setTitle(cursor.getString(1));
        n.setIsLocked((cursor.getInt(3) == 1) ? true : false);
        n.setDate(cursor.getLong(4));
        n.setMood(cursor.getInt(5));
        return  n;
    }

    @Override
    public List<NoteImage> getAllImagesByNoteId(int noteId) {
        List<NoteImage> noteImages = new ArrayList<>();
        String query = "select * from " + Image_Table.TABLE_NAME + " where " + Image_Table.COLUMN_NOTEID + " = " + noteId;

        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            NoteImage ni = new NoteImage();
            ni.setId(cursor.getInt(0));
            ni.setImagePath(cursor.getString(1));
            ni.setNoteID(cursor.getInt(2));
            noteImages.add(ni);
            cursor.moveToNext();
        }
        cursor.close();
        return noteImages;
    }

    @Override
    public List<NoteMedia> getAllMediaByNoteId(int noteId) {
        List<NoteMedia> noteMedias = new ArrayList<>();
        String query = "select * from " + Media_Table.TABLE_NAME + " where " + Media_Table.COLUMN_NOTEID + " = " + noteId;

        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            NoteMedia nm = new NoteMedia();
            nm.setId(cursor.getInt(0));
            nm.setMediaContent(cursor.getBlob(1));
            nm.setNoteID(cursor.getInt(2));
            noteMedias.add(nm);
            cursor.moveToNext();
        }
        cursor.close();
        return noteMedias;
    }

    @Override
    public String getMemoryTextByNoteId(int noteId) {
        String query = "select " + Note_Table.COLUMN_MEMERYTEXT + " from " + Note_Table.TABLE_NAME + " where " + Note_Table.COLUMN_ID + " = " + noteId;
        String memory = "";
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            memory = cursor.getString(0);
            cursor.moveToNext();
        }
        cursor.close();
        return memory;
    }

    @Override
    public List<NoteListViewModel> getNotes(int locked) {

        List<NoteListViewModel> notes = new ArrayList<NoteListViewModel>();
        String selection = Note_Table.COLUMN_LOCKED + "=";
        String[] selectionArgs = new String[] { locked+"" };
        Cursor cursor = database.query(Note_Table.TABLE_NAME,allColumns,selection+locked, null,null,null,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            NoteListViewModel note = null;
            try {
                note = cursorToNoteListViewModel(cursor);
            } catch (Exception e) {
                e.printStackTrace();
            }
            notes.add(note);
            cursor.moveToNext();
        }
        cursor.close();
        return  notes;

    }

    @Override
    public List<NoteListViewModel> getFilteredNotes(long date1, long date2) {
        List<NoteListViewModel> notes = new ArrayList<>();
        String query = "SELECT * FROM " + Note_Table.TABLE_NAME + " WHERE (" +
                Note_Table.COLUMN_DATE + " >= " + date1 + " AND " +
                Note_Table.COLUMN_DATE + " <= " + date2 + ")" ;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            NoteListViewModel note = null;
            try {
                note = cursorToNoteListViewModel(cursor);
            } catch (Exception e) {
                e.printStackTrace();
            }
            notes.add(note);
            cursor.moveToNext();
        }
        cursor.close();
        return  notes;
    }

    @Override
    public List<NoteListViewModel> getDateFilteredNotes(long date1, long date2,int locked) {
        List<NoteListViewModel> notes = new ArrayList<NoteListViewModel>();
        String query = "SELECT * FROM " + Note_Table.TABLE_NAME + " WHERE (" +
                Note_Table.COLUMN_DATE + " >= " + date1 + " AND " +
                Note_Table.COLUMN_DATE + " <= " + date2 + ")" +" AND " +
                Note_Table.COLUMN_LOCKED + " = " + locked;

     //   Cursor cursor = database.rawQuery("SELECT * FROM " + Note_Table.TABLE_NAME + " WHERE " + Note_Table.COLUMN_LOCKED + " = " + locked +  " AND " + Note_Table.COLUMN_DATE + " BETWEEN "+date1+" AND "+date2+"",null);
      Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            NoteListViewModel note = null;
            try {
                note = cursorToNoteListViewModel(cursor);
            } catch (Exception e) {
                e.printStackTrace();
            }
            notes.add(note);
            cursor.moveToNext();
        }
        cursor.close();
        return  notes;
    }

    @Override
    public void deleteAllNotes() {
        database.execSQL("delete from "+ Note_Table.TABLE_NAME);
    }
}
