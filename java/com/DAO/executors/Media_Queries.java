package com.DAO.executors;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.DAO.executors.functionality.Media_Functionality;
import com.DAL.manager.DB_Helper;
import com.DAO.model.NoteMedia;
import com.DAO.tables.Media_Table;
import com.DAO.tables.Note_Table;

import java.sql.SQLException;

/**
 * Created by AMEN on 26.04.2016.
 */
public class Media_Queries implements Media_Functionality {

    private String[] allColumns = {Media_Table.COLUMN_ID,
            Media_Table.COLUMN_MEDIA_CONTENT,
            Media_Table.COLUMN_NOTEID
    };

    private SQLiteDatabase database;
    private DB_Helper dbHelper;

    public  Media_Queries (Context context){
        dbHelper = new DB_Helper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    @Override
    public NoteMedia addMedia(NoteMedia media) {
        ContentValues values = new ContentValues();
        values.put(Media_Table.COLUMN_MEDIA_CONTENT,media.getMediaContent());
        values.put(Media_Table.COLUMN_NOTEID,media.getNoteID());

        long insertId = database.insert(Media_Table.TABLE_NAME, null, values);

        Cursor cursor = database.query(Media_Table.TABLE_NAME , allColumns , Media_Table.COLUMN_ID + " = " + insertId,
                null,null,null,null);
        cursor.moveToFirst();
        NoteMedia newNoteMedia = null;
        try {
            newNoteMedia = cursorToNoteMedia(cursor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        cursor.close();
        return  newNoteMedia;
    }

    private NoteMedia cursorToNoteMedia(Cursor cursor) {
        NoteMedia n = new NoteMedia();
        n.setId(cursor.getInt(0));
        n.setMediaContent(cursor.getBlob(1));
        n.setNoteID(cursor.getInt(2));
        return  n;
    }

    @Override
    public void deleteMedia(int mediaId) {
        database.delete(Media_Table.TABLE_NAME, Media_Table.COLUMN_ID + " = " + mediaId, null);
    }

    @Override
    public NoteMedia getMediaById(int mediaId) {
        Cursor cursor = database.rawQuery("Select ?,?,? from ? where ? = " + mediaId,new
                String[]{Media_Table.COLUMN_ID,Media_Table.COLUMN_MEDIA_CONTENT,Media_Table.COLUMN_NOTEID,
                Media_Table.TABLE_NAME, Media_Table.COLUMN_ID});
        cursor.moveToFirst();
        NoteMedia newNoteMedia = null;
        try {
            newNoteMedia = cursorToNoteMedia(cursor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        cursor.close();
        return  newNoteMedia;
    }

    @Override
    public void deleteMediaByNoteId(int noteId) {
        database.delete(Media_Table.TABLE_NAME, Media_Table.COLUMN_NOTEID + " = " + noteId, null);
    }

    @Override
    public void deleteAllMedia() {
        database.execSQL("delete from "+ Media_Table.TABLE_NAME);
    }
}
