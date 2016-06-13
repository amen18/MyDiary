package com.DAO.tables;

/**
 * Created by AMEN on 26.04.2016.
 */
public class Image_Table {

    public  static final String TABLE_NAME = "image";
    public  static final String COLUMN_ID = "id";
    public  static final String COLUMN_IMAGE_DATA = "imageData";
    public  static final String COLUMN_NOTEID = "noteId";

    public static final String CREAT_TABLE = "create table "+
            TABLE_NAME + "("+
            COLUMN_ID + " Integer primary key autoincrement, "+
            COLUMN_IMAGE_DATA + " String not null, " +
            COLUMN_NOTEID + " Integer not null);";

    public static final String DROP_TABLE = "DROP TABLE " + TABLE_NAME;
}