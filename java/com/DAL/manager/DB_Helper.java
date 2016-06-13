package com.DAL.manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.DAO.tables.Image_Table;
import com.DAO.tables.Media_Table;
import com.DAO.tables.Note_Table;

/**
 * Created by AMEN on 26.04.2016.
 */
public class DB_Helper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME ="Note.db";
    public static final int DATABASE_VERSION =1;


    public DB_Helper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Note_Table.CREAT_TABLE);
        db.execSQL(Image_Table.CREAT_TABLE);
        db.execSQL(Media_Table.CREAT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table is exsists " + Note_Table.TABLE_NAME);
        db.execSQL("drop table is exsists " + Image_Table.TABLE_NAME);
        db.execSQL("drop table is exsists " + Media_Table.TABLE_NAME);
        onCreate(db);
    }
}
