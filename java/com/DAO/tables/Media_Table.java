package com.DAO.tables;

/**
 * Created by AMEN on 26.04.2016.
 */
public class Media_Table {

    public  static final String TABLE_NAME = "media";
    public  static final String COLUMN_ID = "id";
    public  static final String COLUMN_MEDIA_CONTENT = "mediaContent";
    public  static final String COLUMN_NOTEID = "noteId";

    public static final String CREAT_TABLE = "create table "+
            TABLE_NAME + "("+
            COLUMN_ID + " Integer primary key autoincrement, "+
            COLUMN_MEDIA_CONTENT + " BLOB not null, " +
            COLUMN_NOTEID + " Integer not null);";
}