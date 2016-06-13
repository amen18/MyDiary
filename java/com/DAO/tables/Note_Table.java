package com.DAO.tables;

/**
 * Created by AMEN on 26.04.2016.
 */
public class Note_Table {

    public  static final String TABLE_NAME = "note";
    public  static final String COLUMN_ID = "id";
    public  static final String COLUMN_TITLE = "title";
    public  static final String COLUMN_MEMERYTEXT = "memeryText";
    public  static final String COLUMN_LOCKED = "isLocked";
    public  static final String COLUMN_DATE = "date";
    public  static final String COLUMN_MOOD = "mood";

    public static final String CREAT_TABLE = "create table "+
            TABLE_NAME + "("+
            COLUMN_ID + " Integer primary key autoincrement, "+
            COLUMN_TITLE + " text not null, " +
            COLUMN_MEMERYTEXT + " text not null, " +
            COLUMN_LOCKED + " Integer not null, " +
            COLUMN_DATE + " Integer not null, " +
            COLUMN_MOOD + " Integer not null);";

}

