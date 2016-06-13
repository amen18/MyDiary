package com.DAO.executors;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.DAO.executors.functionality.Image_Functionality;
import com.DAL.manager.DB_Helper;
import com.DAO.model.NoteImage;
import com.DAO.tables.Image_Table;
import com.DAO.tables.Note_Table;

import java.sql.SQLException;


/**
 * Created by AMEN on 26.04.2016.
 */
public class Image_Queries implements Image_Functionality {
    private String[] allColumns = {Image_Table.COLUMN_ID,
            Image_Table.COLUMN_IMAGE_DATA,
            Image_Table.COLUMN_NOTEID
    };

    private SQLiteDatabase database;
    private DB_Helper dbHelper;

    public  Image_Queries (Context context){
        dbHelper = new DB_Helper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }


    @Override
    public NoteImage addImage(NoteImage image) {
        ContentValues values = new ContentValues();
        values.put(Image_Table.COLUMN_IMAGE_DATA,image.getImagePath());
        values.put(Image_Table.COLUMN_NOTEID,image.getNoteID());
        long insertId = database.insert(Image_Table.TABLE_NAME, null, values);
        Cursor cursor = database.query(Image_Table.TABLE_NAME , allColumns , Image_Table.COLUMN_ID + " = " + insertId,
                null,null,null,null);
        cursor.moveToFirst();
        NoteImage newNoteImage = null;
        try {
            newNoteImage = cursorToNoteImage(cursor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        cursor.close();
        return  newNoteImage;
    }

    private NoteImage cursorToNoteImage(Cursor cursor) {
        NoteImage n = new NoteImage();
        n.setId(cursor.getInt(0));
        n.setImagePath(cursor.getString(1));
        n.setNoteID(cursor.getInt(2));
        return  n;
    }

    @Override
    public void deleteImage(int imageId) {
        database.delete(Image_Table.TABLE_NAME, Image_Table.COLUMN_ID + " = " + imageId, null);

    }

    @Override
    public NoteImage getImageById(int imageId) {
        Cursor cursor = database.rawQuery("Select ?,?,? from ? where ? = " + imageId,new
                String[]{Image_Table.COLUMN_ID,Image_Table.COLUMN_IMAGE_DATA,Image_Table.COLUMN_NOTEID,
                Image_Table.TABLE_NAME, Note_Table.COLUMN_ID});
        cursor.moveToFirst();
        NoteImage newNoteImage = null;
        try {
            newNoteImage = cursorToNoteImage(cursor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        cursor.close();
        return  newNoteImage;
    }

    @Override
    public void deleteImageByNoteId(int noteId) {
        database.delete(Image_Table.TABLE_NAME, Image_Table.COLUMN_NOTEID + " = " + noteId, null);
    }

    @Override
    public void deleteAllImages() {
        database.execSQL("delete from "+ Image_Table.TABLE_NAME);
    }
}
